package com.oujian.graduation.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.view.CircleImageView;
import com.oujian.graduation.view.CommentListView;
import com.oujian.graduation.view.ExpandTextView;
import com.oujian.graduation.view.PraiseListView;
import com.oujian.graduation.view.SnsPopupWindow;


/**
 * Created by yiw on 2016/8/16.
 */
public class FriendViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView headIv;
    public TextView nameTv;

    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    public FriendViewHolder(View itemView) {
        super(itemView);
        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        //以后可以添加图片以及视频的布局
        // initSubView(viewType, viewStub);

        headIv = (CircleImageView) itemView.findViewById(R.id.friend_img);
        nameTv = (TextView) itemView.findViewById(R.id.friend_name);
        digLine = itemView.findViewById(R.id.lin_dig);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.friend_content);
        timeTv = (TextView) itemView.findViewById(R.id.friend_date);
        deleteBtn = (TextView) itemView.findViewById(R.id.friend_delete);
        snsBtn = (ImageView) itemView.findViewById(R.id.friend_sns);
        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

//    public abstract void initSubView(int viewType, ViewStub viewStub);


}
