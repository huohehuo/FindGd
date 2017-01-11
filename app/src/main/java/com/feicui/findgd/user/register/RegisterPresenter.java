package com.feicui.findgd.user.register;

import android.os.AsyncTask;

import com.feicui.findgd.net.NetClient;
import com.feicui.findgd.user.User;
import com.feicui.findgd.user.UserPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// 注册的业务类
public class RegisterPresenter {

    /**
     * 视图的交互怎么处理？
     * 1. RegisterActivity
     * 2. 接口回调
     * 接口的实例化和接口方法的具体实现
     * 让Activity实现视图接口
     */

    private RegisterView mRegisterView;

    public RegisterPresenter(RegisterView registerView) {
        mRegisterView = registerView;
    }

    public void register(User user) {
        mRegisterView.showProgress();
        Call<RegisterResult> resultCall = NetClient.getInstances().getTreasureApi().register(user);
        resultCall.enqueue(mResultCallback);
    }

    private Callback<RegisterResult> mResultCallback = new Callback<RegisterResult>() {

        // 请求成功
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

            mRegisterView.hideProgress();

            // 响应成功
            if (response.isSuccessful()) {
                RegisterResult result = response.body();

                // 响应体是不是为null
                if (result == null) {
                    mRegisterView.showMessage("发生了未知的错误");
                    return;
                }
                // 不为空
                if (result.getCode() == 1) {
                    // 真正的注册成功
                    // 保存用户token
                    UserPrefs.getInstance().setTokenid(result.getTokenId());
                    mRegisterView.navigationToHome();
                }
                mRegisterView.showMessage(result.getMsg());
            }
        }

        // 请求失败
        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            mRegisterView.hideProgress();
            mRegisterView.showMessage("请求失败" + t.getMessage());
        }
    };


//    public void register(){
//
//        new AsyncTask<Void, Integer, Void>() {
//
//            // 可以使用进度条增加用户体验度。 此方法在主线程执行，用于显示任务执行的进度。
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                // UI的处理：进度条的展示
//                mRegisterView.showProgress();
//            }
//
//            // 后台执行，比较耗时的操作都可以放在这里,后台线程，不可以做UI的更新
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                // 后台线程，做网络请求
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            // 相当于Handler 处理UI的方式，在这里面可以使用在doInBackground 得到的结果处理操作UI
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//
//                // 拿到数据，做UI更新
//                // 注册成功之后的处理
//                mRegisterView.hideProgress();
//                mRegisterView.showMessage("注册成功");
//                mRegisterView.navigationToHome();
//            }
//        }.execute();
//    }

}
