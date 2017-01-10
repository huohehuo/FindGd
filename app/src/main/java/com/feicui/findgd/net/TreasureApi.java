package com.feicui.findgd.net;

import com.feicui.findgd.user.User;
import com.feicui.findgd.user.login.LoginResult;
import com.feicui.findgd.user.register.RegisterResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LINS on 2017/1/10.
 * Please Try Hard
 */
//请求构造的接口
public interface TreasureApi {
    //登录的请求
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> lgoin(@Body User user);

    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);
}
