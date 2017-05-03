package com.oujian.graduation.net.req;

/**
 * Created by DIY on 2017/5/3.
 */

public class AddLikeReq {
    private String userId;
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
