package com.jared.jplayer.ui;

import android.databinding.DataBindingUtil;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jared.jplayer.R;
import com.jared.jplayer.app.BaseActivity;
import com.jared.jplayer.databinding.InternetPlayerBinding;

import io.vov.vitamio.utils.Log;

/**
 * Created by jared on 2016/10/8.
 */

public class InternetVideoPlayer extends BaseActivity {

    InternetPlayerBinding binding;

    @Override
    protected void initParams() {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_internet_player);
    }

    @Override
    protected void initViews() {
        super.initViews();
        binding.webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        //binding.webView.getSettings();
        binding.webView.loadUrl("http://3g.youku.com");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("InternetVideoPlayer", url);
                return true;
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
