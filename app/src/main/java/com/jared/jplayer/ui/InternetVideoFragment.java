package com.jared.jplayer.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jared.jplayer.R;
import com.jared.jplayer.app.BaseFragment;
import com.jared.jplayer.databinding.InternetSrcBinding;

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

}
