package com.oujian.graduation.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by yi on 2016/9/12.
 * 将utf8编码改成汉字
 */
public class DecodeUtils {
    public static String getString(String s){
        String text = "";
        try {
            text= URLDecoder.decode(s,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}
