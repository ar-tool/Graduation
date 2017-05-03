package com.oujian.graduation.entity;

/**
 * Created by yi on 2016/9/18.
 */
public class PingLun {
    //评论Id
    private String id;

    //评论的用户，
    private String account;
    //帖子id
    private String postId;
    /**
     * 评论人id
     */
    private String createUser;
    //评论时间
    private String createTime;
    //评论内容
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
