package com.feicui.findgd.user.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feicui.findgd.MainActivity;
import com.feicui.findgd.R;
import com.feicui.findgd.commons.ActivityUtils;
import com.feicui.findgd.commons.RegexUtils;
import com.feicui.findgd.custom.AlertDialogFragment;
import com.feicui.findgd.treasure.HomeActivity;
import com.feicui.findgd.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LINS on 2017/1/3.
 * Please Try Hard
 */
public class LoginActivity extends AppCompatActivity implements LoginView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private ProgressDialog mDialog;
    private ActivityUtils mActivityUtils;
    private String mUsername,mPassword;

    private Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder=ButterKnife.bind(this);

        mActivityUtils = new ActivityUtils(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.login);
        }
        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUsername = etUsername.getText().toString();
            mPassword = etPassword.getText().toString();
            boolean conLogin=!(TextUtils.isEmpty(mUsername)||
            TextUtils.isEmpty(mPassword));
            btnLogin.setEnabled(conLogin);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_Login)
    public void onClick() {
        if (RegexUtils.verifyUsername(mUsername)!=RegexUtils.VERIFY_SUCCESS){
            AlertDialogFragment.getInstances(
                    getString(R.string.username_error),
                    getString(R.string.username_rules)
            ).show(getSupportFragmentManager(),"usernameError");
            return;
        }
        if (RegexUtils.verifyPassword(mPassword)!=RegexUtils.VERIFY_SUCCESS){
            AlertDialogFragment.getInstances(getString(R.string.password_error),
                    getString(R.string.password_rules)).show(getSupportFragmentManager(),"passwordError");
            return;
        }
        //去做业务逻辑的处理
        new LoginPresenter(this).login(new User(mUsername,mPassword));
    }

    //-------------视图接口方法的具体实现
    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(this,"登录","正在登录，请稍候~");
    }

    @Override
    public void hideProgress() {
        if (mDialog !=null){
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
        //发送广播，关闭MainActivity界面
        Intent intent = new Intent(MainActivity.MAIN_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
