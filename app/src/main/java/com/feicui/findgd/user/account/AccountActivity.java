package com.feicui.findgd.user.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feicui.findgd.R;
import com.feicui.findgd.custom.IconSelectWindow;
import com.feicui.findgd.user.UserPrefs;
import com.pkmmte.view.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LINS on 2017/1/13.
 * Please Try Hard
 */
public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_userIcon)
    CircularImageView mIvIcon;

    private IconSelectWindow mSelectWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        // toolBar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("个人信息");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 加载头像
        String photo = UserPrefs.getInstance().getPhoto();
        if (photo != null) {
            // 加载头像
            Glide.with(this)
                    .load(photo)
                    .error(R.mipmap.user_icon)
                    .placeholder(R.mipmap.user_icon)// 占位图
                    .dontAnimate()// 处理偶尔会出现加载的图片会覆盖原图
                    .into(mIvIcon);
        }

    }
    // toolbar上返回箭头的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_userIcon)
    public void onClick() {
        // 点击头像会弹出一个选择的PopupWindow
        if (mSelectWindow == null) {
            mSelectWindow = new IconSelectWindow(this, listener);
        }
        if (mSelectWindow.isShowing()) {
            mSelectWindow.dismiss();
            return;
        }
        mSelectWindow.show();
    }

    private IconSelectWindow.Listener listener = new IconSelectWindow.Listener() {
        @Override
        public void toGallery() {
            Toast.makeText(AccountActivity.this, "到相册", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void toCamera() {
            Toast.makeText(AccountActivity.this, "到相机", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
