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
    public static final String PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHedLrI6g46XWHhkeVYXJ5GXOxik96tdk9/1LJ+aG1I3LD1/87OAK8wy1Ii5gtCNRL5caaNh6VSN++FY59h6QT3ID3/gesRZlpXE0zQvAsUdgixFRCeTK5uHp6GOWiQZA7uOwI4FSJGuPVuCWc4KhzVx0p4g9GZdN1Dp62uz1C1wIDAQAB";

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
    public static final String THE_URL = "http://rod.oujistore.com/regist_app/";
    public static final String ORDER_URL = "http://rod.oujistore.com/regist_app/order/";
    public static final String USER_URL = "http://rod.oujistore.com/regist_app/user/";


}
