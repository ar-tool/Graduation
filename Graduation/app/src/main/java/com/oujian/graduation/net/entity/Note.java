package com.oujian.graduation.net.entity;

import java.util.List;

/**
 * Created by DIY on 2017/5/2.
 */

public class Note {

    /**
     * retBody : [{"account":"15216721172","commentList":[],"content":"把呵呵哈哈哈","createTime":1493704423000,"createUser":"088b465ac81a44719c23cd8ce07dfc77","delFlag":0,"id":"569feb18cc8c4eabb76fe983024eacb9","upvoteList":[]},{"account":"15216721172","commentList":[],"content":"12313","createTime":1493704214000,"createUser":"088b465ac81a44719c23cd8ce07dfc77","delFlag":0,"id":"9155ab36b2d84d37ab886071f72b9523","upvoteList":[]},{"account":"15216721172","commentList":[],"content":"e10adc3949ba59abbe56e057f20f883e","createTime":1493703786000,"createUser":"088b465ac81a44719c23cd8ce07dfc77","delFlag":0,"id":"51e521a41cb545c79532f454f23dcffa","upvoteList":[]}]
     * retCode : 0
     * retMsg : 成功
     */

    private int retCode;
    private String retMsg;
    private List<RetBodyBean> retBody;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<RetBodyBean> getRetBody() {
        return retBody;
    }

    public void setRetBody(List<RetBodyBean> retBody) {
        this.retBody = retBody;
    }

    public static class RetBodyBean {
        /**
         * account : 15216721172
         * commentList : []
         * content : 把呵呵哈哈哈
         * createTime : 1493704423000
         * createUser : 088b465ac81a44719c23cd8ce07dfc77
         * delFlag : 0
         * id : 569feb18cc8c4eabb76fe983024eacb9
         * upvoteList : []
         */

        private String account;
        private String content;
        private long createTime;
        private String createUser;
        private int delFlag;
        private String id;
        private List<?> commentList;
        private List<?> upvoteList;

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

        public List<?> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<?> commentList) {
            this.commentList = commentList;
        }

        public List<?> getUpvoteList() {
            return upvoteList;
        }

        public void setUpvoteList(List<?> upvoteList) {
            this.upvoteList = upvoteList;
        }
    }
}
