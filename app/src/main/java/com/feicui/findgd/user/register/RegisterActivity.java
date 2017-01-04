package com.feicui.findgd.user.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicui.findgd.MainActivity;
import com.feicui.findgd.R;
import com.feicui.findgd.commons.ActivityUtils;
import com.feicui.findgd.commons.RegexUtils;
import com.feicui.findgd.custom.AlertDialogFragment;
import com.feicui.findgd.treasure.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LINS on 2017/1/3.
 * Please Try Hard
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.et_Confirm)
    EditText etConfirm;
    @BindView(R.id.btn_Register)
    Button btnRegister;

    private String username;
    private String password;
    private ActivityUtils mActivityUtils;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 会触发onContentChanged方法
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);
        //toolbar的展示和返回箭头的监听
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null){
            //激活左上角的返回图标（内部使用选项菜单处理）
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.register);
        }
        //EditText的输入监听
        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirm.addTextChangedListener(textWatcher);
    }
    //文本输入监听
    private TextWatcher textWatcher = new TextWatcher() {
        //文本变化前
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //文本输入变化
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        //文本输入之后，在这里处理
        @Override
        public void afterTextChanged(Editable s) {
            //处理文本输入之后的按钮事件
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            String confirm = etConfirm.getText().toString();
            boolean canregister = !(TextUtils.isEmpty(username)||
                    TextUtils.isEmpty(password)||
                    TextUtils.isEmpty(confirm)
                    &&password.equals(confirm));
            btnRegister.setEnabled(canregister);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // 处理ActionBar的返回箭头事件
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Register)
    public void onClick() {
        //注册的视图和业务处理
        if (RegexUtils.verifyUsername(username)!=RegexUtils.VERIFY_SUCCESS){
            // 显示一个错误的对话框
            AlertDialogFragment.getInstances(
                    getString(R.string.username_error),
                    getString(R.string.username_rules)
            ).show(getSupportFragmentManager(),"usernameError");
            return;
        }
        if (RegexUtils.verifyPassword(password)!=RegexUtils.VERIFY_SUCCESS){
            // 显示一个错误的对话框
            AlertDialogFragment.getInstances(
                    getString(R.string.password_error),
                    getString(R.string.password_rules)
            ).show(getSupportFragmentManager(),"passwordError");
            return;
        }
        // 进行注册的功能：模拟场景进行注册，业务逻辑
        /**
         * 3个泛型：
         * 3. 1. 启动任务输入的参数类型：请求的地址、上传的数据等类型
         * 3. 2. 后台任务执行的进度：一般是Integer类型(int的包装类)
         * 3. 3. 后台返回的结果类型：比如String类型、Void等
         * 模拟注册，三个泛型都不需要的时候都可以设置成Void
         */
         new RegisterPresenter(this).register();
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(this,"注册","正在注册中。。。");
    }

    @Override
    public void hideProgress() {
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        mActivityUtils.showToast(msg);
    }

    @Override
    public void navigationToHome() {
        mActivityUtils.startActivity(HomeActivity.class);
        finish();
        Intent intent = new Intent(MainActivity.MAIN_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
