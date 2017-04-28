package com.oujian.graduation.entity;

/**
 * Created by yi on 16/3/2.
 * 评论的类型和评论的目标
 */
public class CommentConfig {
    public static enum Type{
        PUBLIC("public"), REPLY("reply");

        private String value;
        private Type(String value){
            this.value = value;
        }

    }

    public int itemPosition;
    public int commentPosition;
    public Type commentType;
    public String towho;
    public String towhoName;

}
