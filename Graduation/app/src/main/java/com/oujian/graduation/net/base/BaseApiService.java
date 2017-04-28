package com.oujian.graduation.net.base;



import com.oujian.graduation.net.entity.BaseChatRes;
import com.oujian.graduation.net.entity.ChatEntity;
import com.oujian.graduation.net.entity.LoginEntity;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 基础ApiService，可以继承
 * Created by yi on 2017/03/21.
 */
public interface BaseApiService {

    public static final String Base_URL = "http://139.224.17.214/regist_api/";

    /**
     * 接口的统一封装
     * @param type
     * @param req
     * @return
     */

    @POST("api")
    Observable<BaseResult> getData(@Field("type") String type, @Field("reqJson") String req);

    @GET("api")
    Observable<ChatEntity> chat(@Query("key") String key,@Query("info") String info);

}
