package com.oujian.graduation.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.oujian.graduation.net.base.BaseApiService;
import com.oujian.graduation.net.base.BaseInterceptor;
import com.oujian.graduation.net.base.BaseSubscriber;
import com.oujian.graduation.net.base.ExceptionHandle;
import com.oujian.graduation.net.entity.BaseChatRes;
import com.oujian.graduation.net.entity.ChatEntity;
import com.oujian.graduation.net.entity.LoginEntity;
import com.oujian.graduation.net.res.BaseResponse;
import com.oujian.graduation.net.res.BaseResult;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
            httpCacheDirectory = new File(mContext.getCacheDir(), "regist_cache");
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
     *addcookieJar
     */
    public static void addCookie() {
        okHttpClient.newBuilder().cookieJar(new NovateCookieManger(mContext)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        builder.client(httpClient.build()).build();
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


    /**基础请求方法
    * @param type
     * @param req
     * @param subscriber
     */
    public void getData(String type,String req, BaseSubscriber<BaseResult> subscriber) {
        apiService.getData(type,req)
                .compose(this.<BaseResult>applySchedulers())
                .subscribe(subscriber);
    }
    /**基础请求方法
     * @param type
     * @param req
     * @param subscriber
     */
    public void login(String type, String req, BaseSubscriber<BaseResponse<LoginEntity>> subscriber) {
                apiService.login(type,req)
               .compose(this.<BaseResponse<LoginEntity>>applySchedulers())
                .subscribe(subscriber);
    }
    public void chat(String key,String info ,BaseSubscriber<ChatEntity> subscriber){
                apiService.chat(key,info)
                .compose(this.<ChatEntity>applySchedulers())
                .subscribe(subscriber);
    }
    public void post(String url, Map<String, String> parameters, Subscriber<ResponseBody> subscriber) {
        apiService.executePost(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    public void upload(String url, RequestBody requestBody,Subscriber<ResponseBody> subscriber) {
        apiService.upLoadFile(url, requestBody)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    public void download(String url, final CallBack callBack) {
        apiService.downloadFile(url)
                .compose(schedulersTransformer())
                .subscribe(new DownSubscriber<ResponseBody>(callBack));
    }


    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {


            @Override
            public Object call(Object observable) {
                return ((Observable)  observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

           /* @Override
            public Observable call(Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }*/
        };
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer();
    }

    public <T> Observable.Transformer<BaseResponse<T>, T> transformer() {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private class HandleFuc<T> implements Func1<BaseResponse<T>, T> {
        @Override
        public T call(BaseResponse<T> response) {
            if (!response.isOk()) throw new RuntimeException(response.getRetCode() + "" + response.getRetMsg() != null ? response.getRetMsg(): "");
            return response.getUser();
        }
    }


    /**
     * /**
     * execute your customer API
     * For example:
     *  MyApiService service =
     *      RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     *
     *  RetrofitClient.getInstance(MainActivity.this)
     *      .execute(service.lgon("name", "password"), subscriber)
     *     * @param subscriber
     */

    public static <T> T execute(Observable<T> observable ,Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }


    /**
     * DownSubscriber
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends Subscriber<ResponseBody> {
        CallBack callBack;

        public DownSubscriber(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onCompleted() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);

        }
    }

}
