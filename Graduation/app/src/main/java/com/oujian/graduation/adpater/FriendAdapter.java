package com.oujian.graduation.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oujian.graduation.R;
import com.oujian.graduation.activity.ImagePagerActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.entity.Click;
import com.oujian.graduation.entity.CommentConfig;
import com.oujian.graduation.entity.FriendEntity;
import com.oujian.graduation.entity.PingLun;
import com.oujian.graduation.utils.GildeUtils;
import com.oujian.graduation.utils.TimeUtils;
import com.oujian.graduation.view.CommentDialog;
import com.oujian.graduation.view.CommentListView;
import com.oujian.graduation.view.PraiseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIY on 2017/4/28.
 */

public class FriendAdapter extends ListBaseAdapter<FriendEntity> {
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
        FriendEntity entity = mDataList.get(position);
        FriendViewHolder viewHolder = (FriendViewHolder) holder;

        final String noteId = entity.get_id();//id
        String name = entity.getUserName();//昵称
        String headImg = entity.getUserImg();//头像
        final String content = entity.getText();//内容
        String createTime = TimeUtils.getTime(entity.getCreateTime());
        final List<Click> favortDatas = entity.getZanList();
        final List<PingLun> commentsDatas = entity.getPingLunList();
        //是否有点赞和评论
        boolean hasFavort = entity.hasFavort();
        boolean hasComment = entity.hasComment();
        //头像，姓名，时间,发布内容
        GildeUtils.imageLoader((Activity)mContext,viewHolder.headIv,headImg);
        viewHolder.nameTv.setText(name);
        viewHolder.timeTv.setText(createTime);
        if(!TextUtils.isEmpty(content)){
            viewHolder.contentTv.setText(content);
        }
        viewHolder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
        //删除按钮的显示和操作
        if(MyContext.getInstance().getUserId().equals(entity.getUserId())){
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
        }else{
            viewHolder.deleteBtn.setVisibility(View.GONE);
        }
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onDeleteBbs(position,noteId);
                }
            }
        });

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
                        if(MyContext.getInstance().getUserId().equals(commentItem.getpUserId())){//复制或者删除自己的评论
                            CommentDialog dialog = new CommentDialog(noteId,mContext, commentItem, position);
                            dialog.setCommitClick(new CommentDialog.OnDeleteCommitClick() {
                                @Override
                                public void deleteCommit() {
                                    mListener.delteComment(noteId,commentItem.getPingLunId());
                                }
                            });
                            dialog.show();
                        }else{//回复别人的评论
                            CommentConfig config = new CommentConfig();
                            config.itemPosition = position;
                            config.commentPosition = commentPosition;
                            config.commentType = CommentConfig.Type.REPLY;
                            config.towho = commentItem.getTowho();
                            config.towhoName = commentItem.getToWhoName();
                            if(mListener != null){
                                mListener.onPublishComment(config);
                            }
                        }
                    }
                });

            }else {
                viewHolder.commentList.setVisibility(View.GONE);
            }
            viewHolder.digCommentBody.setVisibility(View.VISIBLE);
        }else{
            viewHolder.digCommentBody.setVisibility(View.GONE);
        }

    }

    /**
     * 打开图片查看器
     *
     * @param position
     * @param urls2
     */
    private void imageBrower(int position, ArrayList<String> urls2) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
    public interface FriendZoneListener{
        void onAddLike(int postion);
        void onDeleteLike(int position);
        void delteComment(String noteId,String commentId);
        void onPublishComment(CommentConfig commentConfig);
        void onClickPhoto();
        void onClickBgImg();
        void onDeleteBbs(int position,String noteId);
    }
}
