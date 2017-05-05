package com.oujian.graduation.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.oujian.graduation.net.base.BaseApiService;
import com.oujian.graduation.net.base.BaseInterceptor;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.entity.ChatEntity;
import com.oujian.graduation.net.entity.LoginEntity;
import com.oujian.graduation.net.entity.NewsEntity;
import com.oujian.graduation.net.entity.NoteEntity;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RetrofitClient
 *  Created by yi on 2016-12-03.
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 20;
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl = BaseApiService.Base_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;
    public static final String LOGIN_TYPE = "1";
    public static final String REGIST_TYPE = "2";
    public static final String CHANGE_PWD_TYPE = "3";
    public static final String GET_NEWS_TYPE = "5";
    public static final String PUBLISH_NOTE_TYPE = "7";
    public static final String NOTE_LIST_TYPE = "8";
    public static final String COMMENT_TYPE = "9";
    public static final String ADD_LIKE_TYPE = "10";
    public static final String CANCEL_LIKE_TYPE = "11";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl);
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }

        return new RetrofitClient(context, url);
    }

    /**
     * 换了url和头部参数
     * @param context
     * @param url
     * @param headers
     * @return
     */
    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

   private RetrofitClient() {

   }

    private RetrofitClient(Context context) {

        this(context, baseUrl, null);
    }

    private RetrofitClient(Context context, String url) {

        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if ( httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "graduation_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

   /**
     * ApiBaseUrl
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * create BaseApi  defalte ApiManager
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public  <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * 登录
     * @param req
     * @param subscriber
     */
    public void login(String req, BaseSubscriber<BaseResponse<LoginEntity>> subscriber){
        apiService.login(LOGIN_TYPE,req)
                .compose(this.<BaseResponse<LoginEntity>>applySchedulers())
                .subscribe(subscriber);
    }
    /**注册
     * @param req
     * @param subscriber
     */
    public void regist(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.regist(REGIST_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    /**修改信息
     * @param req
     * @param subscriber
     */
    public void changeInfo(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.changeInfo(CHANGE_PWD_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    /**发布帖子
     * @param req
     * @param subscriber
     */
    public void pushNote(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.pushNote(PUBLISH_NOTE_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取帖子列表
     * @param req
     * @param subscriber
     */
    public void getNoteList(String req, BaseSubscriber<BaseResponse<List<NoteEntity>>> subscriber) {
        apiService.getNoteList(NOTE_LIST_TYPE,req)
                .compose(this.<BaseResponse<List<NoteEntity>>>applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 点赞
     * @param req
     * @param subscriber
     */
    public void addLike(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.addLike(ADD_LIKE_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    /**
     * 获取新闻列表
     * @param subscriber
     */
    public void getNews( BaseSubscriber<BaseResponse<List<NewsEntity>>> subscriber) {
        apiService.getNews(GET_NEWS_TYPE)
                .compose(this.<BaseResponse<List<NewsEntity>>>applySchedulers())
                .subscribe(subscriber);
    }
    /**
     * 取消点赞
     * @param req
     * @param subscriber
     */
    public void cancelLike(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.cancelLike(CANCEL_LIKE_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    /**
     * 评论
     * @param req
     * @param subscriber
     */
    public void comment(String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.comment(COMMENT_TYPE,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    public void chat(String key,String info ,BaseSubscriber<ChatEntity> subscriber){
                apiService.chat(key,info)
                .compose(this.<ChatEntity>applySchedulers())
                .subscribe(subscriber);
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer();
    }

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {


            @Override
            public Object call(Object observable) {
                return ((Observable)  observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

        };
    }
}
