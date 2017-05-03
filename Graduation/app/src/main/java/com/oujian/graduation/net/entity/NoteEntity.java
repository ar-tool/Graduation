package com.oujian.graduation.net.entity;

import android.text.TextUtils;

import com.oujian.graduation.entity.Click;
import com.oujian.graduation.entity.PingLun;

import java.util.List;

/**
 * Created by DIY on 2017/5/2.
 */

public class NoteEntity {
    private String account;
    private String content;
    private long createTime;
    private String createUser;
    private int delFlag;
    private String id;
    private List<PingLun> commentList;
    private List<Click> upvoteList;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PingLun> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<PingLun> commentList) {
        this.commentList = commentList;
    }

    public List<Click> getUpvoteList() {
        return upvoteList;
    }

    public void setUpvoteList(List<Click> upvoteList) {
        this.upvoteList = upvoteList;
    }

    public boolean hasFavort(){
        if(upvoteList!=null && upvoteList.size()>0){
            return true;
        }
        return false;
    }

    public boolean hasComment(){
        if(commentList!=null && commentList.size()>0){
            return true;
        }
        return false;
    }

    //本人有没有点赞过
    public String getCurUserFavortId(String curUserId){
        String favortid = "";
        if(!TextUtils.isEmpty(curUserId) && hasFavort()){
            for(Click item : upvoteList){
                if(curUserId.equals(item.getUpvoteUser())){
                    //是否应该有个点赞id/// TODO: 2016/9/23 是否应该有点赞id
                    favortid = item.getUpvoteUser();
                    return favortid;
                }
            }
        }
        return favortid;
    }
}
