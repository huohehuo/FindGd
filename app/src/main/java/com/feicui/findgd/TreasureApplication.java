package com.feicui.findgd;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.feicui.findgd.user.UserPrefs;

/**
 * Created by LINS on 2017/1/4.
 * Please Try Hard
 */
public class TreasureApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        UserPrefs.init(getApplicationContext());
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());


    }
}
