package com.jared.jplayer.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jared.jplayer.R;
import com.jared.jplayer.adapter.FileAdapter;
import com.jared.jplayer.app.BaseFragment;
import com.jared.jplayer.bean.LocalSource;
import com.jared.jplayer.databinding.LocalSrcBinding;
import com.jared.jplayer.utils.VideoUtil;

import java.io.File;

/**
 * Created by jared on 2016/9/29.
 */
public class LocalVideoFragment extends BaseFragment {

    private LocalSrcBinding binding;
    FileAdapter fileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_local, container, false);
        return binding.getRoot();
    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
        fileAdapter = new FileAdapter(getActivity());
        new ScanVideoTask().execute();
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvLocalSrc.setLayoutManager(linearLayoutManager);
        binding.rvLocalSrc.setAdapter(fileAdapter);
    }

    /** 扫描SD卡 */
    private class ScanVideoTask extends AsyncTask<Void, File, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            VideoUtil.eachAllMedias(Environment.getExternalStorageDirectory(), new VideoUtil.Callback() {
                @Override
                public void findFile(File file) {
                    publishProgress(file);
                }
            });
            return null;
        }

        @Override
        protected void onProgressUpdate(File... values) {
            LocalSource localSource = new LocalSource();
            localSource.setName(values[0].getName());
            localSource.setUrl(values[0].getAbsolutePath());
            localSource.setBitmap(VideoUtil.getVideoThumbnail(localSource));
            fileAdapter.appendItem(localSource);
            fileAdapter.notifyDataSetChanged();
        }
    }
}
