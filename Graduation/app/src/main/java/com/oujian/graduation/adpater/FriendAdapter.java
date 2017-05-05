package com.oujian.graduation.adpater;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oujian.graduation.R;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.ActionItem;
import com.oujian.graduation.entity.Click;
import com.oujian.graduation.entity.CommentConfig;
import com.oujian.graduation.entity.PingLun;
import com.oujian.graduation.net.entity.NoteEntity;
import com.oujian.graduation.utils.TimeUtils;
import com.oujian.graduation.view.CommentDialog;
import com.oujian.graduation.view.CommentListView;
import com.oujian.graduation.view.SnsPopupWindow;

import java.util.List;

/**
 * 朋友圈适配器
 * Created by yi on 2017/4/28.
 */

public class FriendAdapter extends ListBaseAdapter<NoteEntity> {
    private LayoutInflater mLayoutInflater;
    private FriendZoneListener mListener = null;
    public void setFriendZoneListener(FriendZoneListener friendZoneListener) {
        this.mListener = friendZoneListener;
    }

    public FriendAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendViewHolder(mLayoutInflater.inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NoteEntity entity = mDataList.get(position);
        FriendViewHolder viewHolder = (FriendViewHolder) holder;

        final String noteId = entity.getId();//id
        String name = entity.getAccount();//昵称
        //String headImg = entity.getUserImg();//头像
        final String content = entity.getContent();//内容
        String createTime = TimeUtils.getLongTime(String.valueOf(entity.getCreateTime()));
        final List<Click> favortDatas = entity.getUpvoteList();
        final List<PingLun> commentsDatas = entity.getCommentList();
        //是否有点赞和评论
        boolean hasFavort = entity.hasFavort();
        boolean hasComment = entity.hasComment();
        //头像，姓名，时间,发布内容
        //GildeUtils.imageLoader((Activity)mContext,viewHolder.headIv,headImg);
        viewHolder.nameTv.setText(name);
        viewHolder.timeTv.setText(createTime);
        if(!TextUtils.isEmpty(content)){
            viewHolder.contentTv.setText(content);
        }
        viewHolder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        //删除按钮的显示和操作，隐藏删帖功能
//        if(MyContext.getInstance().getUserId().equals(entity.getCreateUser())){
//            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.deleteBtn.setVisibility(View.GONE);
//        }
//        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mListener != null){
//                    mListener.onDeleteBbs(position,noteId);
//                }
//            }
//        });

        if(!TextUtils.isEmpty(content)){
            viewHolder.contentTv.setText(content);
        }
        if(hasFavort || hasComment){
            if(hasFavort){//处理点赞列表
                //点赞列表暂时不添加点击事件
                viewHolder.praiseListView.setDatas(favortDatas);
                viewHolder.praiseListView.setVisibility(View.VISIBLE);
            }else{
                viewHolder.praiseListView.setVisibility(View.GONE);
            }

            if(hasComment){//处理评论列表
                viewHolder.commentList.setDatas(commentsDatas);
                viewHolder.commentList.setVisibility(View.VISIBLE);
                viewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int commentPosition) {
                        final PingLun commentItem = commentsDatas.get(commentPosition);
                        //将删除评论以及回复他人评论功能隐藏
//                        if(MyContext.getInstance().getUserId().equals(commentItem.getpUserId())){//复制或者删除自己的评论
//                            CommentDialog dialog = new CommentDialog(noteId,mContext, commentItem, position);
//                            dialog.setCommitClick(new CommentDialog.OnDeleteCommitClick() {
//                                @Override
//                                public void deleteCommit(int postion, String noteId, String pinglunId) {
//                                    mListener.delteComment(noteId,commentItem.getPingLunId());
//                                }
//                            });
//                            dialog.show();
//                        }else{//回复别人的评论
//                            CommentConfig config = new CommentConfig();
//                            config.itemPosition = position;
//                            config.commentPosition = commentPosition;
//                            config.commentType = CommentConfig.Type.REPLY;
//                            config.towho = commentItem.getTowho();
//                            config.towhoName = commentItem.getToWhoName();
//                            if(mListener != null){
//                                mListener.onPublishComment(config);
//                            }
//                        }
                    }
                });

            }else {
                viewHolder.commentList.setVisibility(View.GONE);
            }
            viewHolder.digCommentBody.setVisibility(View.VISIBLE);
        }else{
            viewHolder.digCommentBody.setVisibility(View.GONE);
        }
        final SnsPopupWindow snsPopupWindow = viewHolder.snsPopupWindow;
        //判断本人是否已点赞
        String curUserFavortId = entity.getCurUserFavortId(MyContext.getInstance().getUserId());
        if(!TextUtils.isEmpty(curUserFavortId)){
            snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
        }else{
            snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
        }
        snsPopupWindow.update();
        snsPopupWindow.setmItemClickListener(new PopupItemClickListener(position, entity, curUserFavortId));
        viewHolder.snsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //弹出popupwindow
                snsPopupWindow.showPopupWindow(view);
            }
        });
    }
    // 弹窗子类项选中时的监听
    private SnsPopupWindow.OnItemClickListener mItemClickListener;
    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener{
        private String mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private NoteEntity mCircleItem;

        public PopupItemClickListener(int circlePosition, NoteEntity item, String favorId){
            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
            this.mCircleItem = item;
        }



        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if(System.currentTimeMillis()-mLasttime<700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                        if ("赞".equals(actionitem.mTitle.toString())) {
                            //点赞
                            if(mListener != null){
                                mListener.onAddLike(mCirclePosition);
                            }

                        } else {
                            //取消点赞
                            if(mListener != null){
                                mListener.onDeleteLike(mCirclePosition);
                            }
                    }
                    break;
                case 1://发布评论
                    CommentConfig config = new CommentConfig();
                    config.itemPosition = mCirclePosition;
                    config.commentType = CommentConfig.Type.PUBLIC;
                    if(mListener != null){
                        mListener.onPublishComment(config);
                    }

                    break;
                default:
                    break;
            }
        }
    }

    public interface FriendZoneListener{
        void onAddLike(int postion);
        void onDeleteLike(int position);
        void onPublishComment(CommentConfig commentConfig);
    }
}
