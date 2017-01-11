package com.feicui.findgd.user.login;

import android.os.AsyncTask;
import android.os.Handler;

import com.feicui.findgd.net.NetClient;
import com.feicui.findgd.user.User;
import com.feicui.findgd.user.UserPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/1/3.
 */

// 登录的业务类
public class LoginPresenter {

    private LoginView mLoginView;

    public LoginPresenter(LoginView loginView) {
        mLoginView = loginView;
    }

    public void login(User user){
        mLoginView.showProgress();
        Call<LoginResult> loginResultCall = NetClient.getInstances().getTreasureApi().lgoin(user);
        loginResultCall.enqueue(mCallback);
    }
    private Callback<LoginResult> mCallback = new Callback<LoginResult>(){

        @Override
        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
            mLoginView.hideProgress();
            if (response.isSuccessful()){
                LoginResult loginResult = response.body();
                if (loginResult == null){
                    mLoginView.showMessage("未知错误");
                    return;
                }
                if (loginResult.getCode()==1){
                    // 真正的登录成功了
                    // 保存头像和tokenId
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL+loginResult.getHeadpic());
                    UserPrefs.getInstance().setTokenid(loginResult.getTokenid());
                    mLoginView.navigationToHome();
                }
                mLoginView.showMessage(loginResult.getMsg());
            }
        }

        @Override
        public void onFailure(Call<LoginResult> call, Throwable t) {
                mLoginView.hideProgress();
            mLoginView.showMessage("请求失败"+t.getMessage());
        }
    };





//    public void login(){
//        new AsyncTask<Void, Integer, Void>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                mLoginView.showProgress();
//            }
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                mLoginView.hideProgress();
//                mLoginView.showMessage("登录成功");
//                mLoginView.navigationToHome();
//            }
//        }.execute();
//    }
}
