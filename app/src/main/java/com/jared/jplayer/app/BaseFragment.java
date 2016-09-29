package com.jared.jplayer.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View rootView;

    /**
     * fragment当前可见 且 初始化过views
     */
    protected boolean isLoaded;
    /**
     * 是否可见
     */
    private boolean isVisible;
    /**
     * 视图是否初始化完毕
     */
    private boolean initedView;

    /**
     * setUserVisibleHint()方法是否被调用
     */
    private boolean isSetUserVisibleHintInvoked;

    private int contentViewId;

    protected ImageView ivTitleLeft;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isSetUserVisibleHintInvoked = true;
        if (isVisible = getUserVisibleHint()) {
            onVisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = createView(inflater, container);//inflater.inflate(contentViewId, container, false);
        }
        return rootView;
    }

    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(contentViewId, container, false);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!initedView) {
            initViews(view);
        }

        //说明不是和Viewpager一起用
        if (!isSetUserVisibleHintInvoked) {
            isVisible = true;
        }

        // 第一个Tab的setUserVisibleHint在onViewCreated执行之前，
        // 这样lazyLoad不会被执行，所以重新判断一下
        if (!isLoaded) {
            onVisible();
        }
    }

    protected void initParams() {

    }

    protected void setContentViewId(int contentViewId) {
        this.contentViewId = contentViewId;
    }


    private void onVisible() {
        if (isVisible && initedView) {
            isLoaded = true;
            lazyLoad();
        }
    }

    public void initViews(View view) {
        initedView = true;
    }

    /**
     * 当界面可见且View初始化完毕，发送请求。fragment和viewpager一起使用是才会自动调用
     */
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View view) {

    }

    public String getTitle() {
        return null;
    }

    protected String getLabel() {
        return "";
    }

}