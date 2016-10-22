package com.jared.jplayer.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jared.jplayer.R;
import com.jared.jplayer.app.BaseFragment;
import com.jared.jplayer.databinding.InternetSrcBinding;
import com.jared.jplayer.utils.VideoUtil;

/**
 * Created by jared on 2016/9/29.
 */
public class InternetVideoFragment extends BaseFragment {

    private InternetSrcBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_internet, container, false);
        return binding.getRoot();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.webView.getSettings().setUseWideViewPort(true);//关键点
        binding.webView.getSettings().setDisplayZoomControls(true);
        binding.webView.getSettings().setSupportZoom(true);
        //binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.loadUrl("http://3g.youku.com");
        //binding.webView.loadUrl("http://www.youku.com");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("onPageFinished", "url:"+url);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("shouldOverrideUrl", "url:"+url);
                //LocalVideoPlayer.launch(getContext(), "http://113.215.6.76/youku/6976C1A0F894B83FF6BEC2201F/030008170057F7E1E1A2C231993867D63F436A-B92E-8145-D826-F9938F4D3AE7.mp4");
                if (VideoUtil.isVideo(url)) {
                    return true;
                }
                return false;
            }
        });

        binding.webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((i == KeyEvent.KEYCODE_BACK) && binding.webView != null && binding.webView.canGoBack()) {
                    binding.webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }
}
