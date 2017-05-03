package com.oujian.graduation.entity;

/**
 * Created by yi on 2016/9/18.
 * 点赞
 */
public class Click {
    /**
     * 点赞用户
     */
    private String account;
    /**
     * 用户id
     */
    private String upvoteUser;
    /**
     * 点赞时间
     */
    private String createTime;
    //点赞id
    private String id;
    //帖子id
    private String postId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUpvoteUser() {
        return upvoteUser;
    }

    public void setUpvoteUser(String upvoteUser) {
        this.upvoteUser = upvoteUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
