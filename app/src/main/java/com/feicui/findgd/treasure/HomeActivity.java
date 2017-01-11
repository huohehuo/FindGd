package com.feicui.findgd.treasure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.feicui.findgd.MainActivity;
import com.feicui.findgd.R;
import com.feicui.findgd.commons.ActivityUtils;
import com.feicui.findgd.user.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LINS on 2017/1/3.
 * Please Try Hard
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    private ImageView mIvIcon;

    private ActivityUtils mActivityUtils;
    private Unbinder mBind;
    private Target<GlideDrawable> mTarget;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBind=ButterKnife.bind(this);

        // 进入页面，将宝藏数据的缓存清空
        TreasureRepo.getInstance().clear();

        mActivityUtils = new ActivityUtils(this);

        // toolbar的处理
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // drawerLayout设置监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();// 同步状态
        mDrawerLayout.addDrawerListener(toggle);
        // 设置Navigation每一条的选中监听
        mNavigationView.setNavigationItemSelectedListener(this);
        mIvIcon = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.iv_usericon);
        mIvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 更换头像
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        // 更新侧滑上面的头像信息
        String photo = UserPrefs.getInstance().getPhoto();
        if (photo!=null){
            // 加载头像
            Glide.with(this)
                    .load(photo)
                    .error(R.mipmap.user_icon)
                    .placeholder(R.mipmap.user_icon)// 占位图
                    .into(mIvIcon);
        }
    }

    // Navigation每一条的选中监听
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_hide:
                break;
            case R.id.menu_logout:
                // 清空登录用户数据
                UserPrefs.getInstance().clearUser();
                mActivityUtils.startActivity(MainActivity.class);
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    // 处理back返回键
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
