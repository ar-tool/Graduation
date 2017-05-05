package com.oujian.graduation.net.base;



import com.oujian.graduation.net.entity.ChatEntity;
import com.oujian.graduation.net.entity.LoginEntity;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.net.entity.NoteEntity;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 基础ApiService，可以继承
 * Created by yi on 2017/03/21.
 */
public interface BaseApiService {

    public static final String Base_URL = "http://hao.oudot.cn/graduation/";

    /**
     * 接口的统一封装
     * @param type
     * @param req
     * @return
     */

    @POST("api")
    Observable<BaseResult> regist(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResponse<LoginEntity>> login(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResult> changeInfo(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResult> pushNote(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResponse<List<NoteEntity>>> getNoteList(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResponse<List<NewsEntity>>> getNews(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResult> addLike(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResult> cancelLike(@Query("type") String type, @Query("json") String req);
    @POST("api")
    Observable<BaseResult> comment(@Query("type") String type, @Query("json") String req);
    @GET("api")
    Observable<ChatEntity> chat(@Query("key") String key,@Query("info") String info);

}
