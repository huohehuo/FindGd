package com.feicui.findgd.net;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LINS on 2017/1/10.
 * Please Try Hard
 */
//网络初始化的类
public class NetClient {
    private static NetClient mNetClient;
    private static final String BASE_URL="http://admin.syfeicuiedu.com";
    private TreasureApi mTreasureApi;
    private final Retrofit mRetrofit;
    public static synchronized NetClient getInstances(){
        if (mNetClient==null){
            mNetClient = new NetClient();
        }
        return mNetClient;
    }
    public TreasureApi getTreasureApi(){
        if (mTreasureApi == null){
            mTreasureApi = mRetrofit.create(TreasureApi.class);
        }
        return mTreasureApi;
    }
    private NetClient(){
        //Retrofit的初始化
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        //初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
