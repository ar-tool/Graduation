package com.oujian.graduation.net.entity;

import android.text.TextUtils;

import com.oujian.graduation.entity.Click;
import com.oujian.graduation.entity.PingLun;

import java.util.List;

/**
 * Created by yi on 2017/4/28.
 */

public class FriendEntity {
    /**
     * 帖子id
     */
    private String _id;
    /**
     * 发帖人Id
     */
    private String userId;
    private String userImg;
    /**
     * 发帖内容
     */
    private String text;
    /**
     * 点赞列表
     */
    private List<Click> zanList;
    /**
     * 评论列表
     */
    private List<PingLun> pingLunList;
    /**
     * 发帖人姓名
     */
    private String userName;
    /**
     * 发帖时间
     */
    private String createTime;

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Click> getZanList() {
        return zanList;
    }

    public void setZanList(List<Click> zanList) {
        this.zanList = zanList;
    }

    public List<PingLun> getPingLunList() {
        return pingLunList;
    }

    public void setPingLunList(List<PingLun> pingLunList) {
        this.pingLunList = pingLunList;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public boolean hasFavort(){
        if(zanList!=null && zanList.size()>0){
            return true;
        }
        return false;
    }

    public boolean hasComment(){
        if(pingLunList!=null && pingLunList.size()>0){
            return true;
        }
        return false;
    }
    //本人有没有点赞过
    public String getCurUserFavortId(String curUserId){
        String favortid = "";
        if(!TextUtils.isEmpty(curUserId) && hasFavort()){
            for(Click item : zanList){
                if(curUserId.equals(item.getFriendId())){
                    //是否应该有个点赞id/// TODO: 2017/4/23 是否应该有点赞id
                    favortid = item.getFriendId();
                    return favortid;
                }
            }
        }
        return favortid;
    }
}
