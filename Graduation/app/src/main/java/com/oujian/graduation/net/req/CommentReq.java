package com.oujian.graduation.net.req;

/**
 * Created by DIY on 2017/5/3.
 */

public class CommentReq {

    private String postId;
    private String userId;
    private String comment;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
