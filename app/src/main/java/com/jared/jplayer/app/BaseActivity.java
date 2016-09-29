package com.jared.jplayer.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jared.jplayer.R;

/**
 * Created by jared on 2016/04/20
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView ivTitleLeft;
    protected TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushActivity(this);
        initParams();
        initViews();
        initEvents();
    }

    /**
     * 显示加载对话框
     */
    public void showLoadingDialog() {
    }

    /**
     * 隐藏加载对话框
     */
    public void hideLoadingDialog() {
    }

    protected void initViews() {

    }

    protected void initEvents() {
        //BusProvider.getInstance().register(this);
    }

    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public String getLabel() {
        return getString(R.string.app_name);
    }

    /**
     * 初始化参数(获取传递过来的参数和设置布局)
     */
    protected abstract void initParams();


    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BusProvider.getInstance().unregister(this);
        ActivityManager.getInstance().finishActivity(this);
        //IMMLeaks.fixInputMethodManagerLeak(this);
    }

    public Activity getActivity() {
        return BaseActivity.this;
    }

    @Override
    public void finish() {
        ActivityManager.getInstance().finishActivity(getActivity().getClass().getSimpleName());
        super.finish();
    }
}
