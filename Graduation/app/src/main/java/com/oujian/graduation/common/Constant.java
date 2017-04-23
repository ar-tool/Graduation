package com.oujian.graduation.common;

import android.os.Environment;

import java.io.File;
import java.util.Map;

/**
 * Created by yi on 16/11/19.
 */
public class Constant {

    public static final int UIL_DISK_CACHE_SIZE = 50 * 1024 * 1024; //50MB
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE_SIZE_THREE = 3;
    public static final int DEFAULT_TICKET_LIST_PAGE_SIZE = 8;
    public static final int DEFAULT_POINT_CHECK_PAGE_SIZE = 8;
    public static final String QQ_COMMUNICATE = "FiEJYIcHRsR1mPA2yKe9QeGQb4v1Gs1a";
    public static final String QQ_SERVICE = "EM55Jq8NR37DQgnMnAw89c3tKLJGaQL9";

    public static final String TULING_URL = "http://www.tuling123.com/openapi/";
    public static final String TULING_KEY = "e527af87e96c4d898e9b4340af23da06";

    //用来存放倒计时的时间
    public static Map<String, Long> map;
    /**
     * 存储活体检测文件目录
     */
    public static String storageFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "liveness" + File.separator;

    public static final String API_CODE_PIC_IDENTIFY = "ZKWZSB";

    public static final String API_CODE_INFO_VER = "SFZCY";

    public static final String API_CODE_PIC_VER = "RXDB";

    public static final String Base_URL = "http://139.224.17.214/regist_api/api/";


}
