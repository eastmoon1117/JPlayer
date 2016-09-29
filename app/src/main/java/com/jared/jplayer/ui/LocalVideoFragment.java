package com.jared.jplayer.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
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

import java.io.File;

import io.vov.vitamio.utils.Log;

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
            eachAllMedias(Environment.getExternalStorageDirectory());
            return null;
        }

        @Override
        protected void onProgressUpdate(File... values) {
            LocalSource localSource = new LocalSource();
            localSource.setName(values[0].getName());
            localSource.setUrl(values[0].getAbsolutePath());
            localSource.setBitmap(getVideoThumbnail(localSource, localSource.getUrl()));
            localSource.setBitmap(getVideoThumbnail(localSource.getUrl()));
            Log.d("LocalVideoFragment", "hhh"+localSource.getName()+":"+localSource.getUrl());
            fileAdapter.appendItem(localSource);
            fileAdapter.notifyDataSetChanged();

        }

        /** 遍历所有文件夹，查找出视频文件 */
        public void eachAllMedias(File f) {
            if (f != null && f.exists() && f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null) {
                    for (File file : f.listFiles()) {
                        if (file.isDirectory()) {
                            eachAllMedias(file);
                        } else if (file.exists() && file.canRead() && isVideo(file)) {
                            publishProgress(file);
                        }
                    }
                }
            }
        }
    }

    private boolean isVideo(File file) {
        String name = file.getName();

        int i = name.indexOf('.');
        if (i != -1) {
            Log.d("isVideo", name);
            name = name.substring(i);
            if (name.equalsIgnoreCase(".mp4")
                    || name.equalsIgnoreCase(".3gp")
                    || name.equalsIgnoreCase(".wmv")
                    || name.equalsIgnoreCase(".ts")
                    || name.equalsIgnoreCase(".rmvb")
                    || name.equalsIgnoreCase(".mov")
                    || name.equalsIgnoreCase(".m4v")
                    || name.equalsIgnoreCase(".avi")
                    || name.equalsIgnoreCase(".m3u8")
                    || name.equalsIgnoreCase(".3gpp")
                    || name.equalsIgnoreCase(".3gpp2")
                    || name.equalsIgnoreCase(".mkv")
                    || name.equalsIgnoreCase(".flv")
                    || name.equalsIgnoreCase(".divx")
                    || name.equalsIgnoreCase(".f4v")
                    || name.equalsIgnoreCase(".rm")
                    || name.equalsIgnoreCase(".asf")
                    || name.equalsIgnoreCase(".ram")
                    || name.equalsIgnoreCase(".mpg")
                    || name.equalsIgnoreCase(".v8")
                    || name.equalsIgnoreCase(".swf")
                    || name.equalsIgnoreCase(".m2v")
                    || name.equalsIgnoreCase(".asx")
                    || name.equalsIgnoreCase(".ra")
                    || name.equalsIgnoreCase(".ndivx")
                    || name.equalsIgnoreCase(".xvid")) {
                return true;
            }
        }
        return false;
    }

    public Bitmap getVideoThumbnail(LocalSource localSource, String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(0);
            localSource.setAuthor(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
            localSource.setDuration(Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
            localSource.setDate(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));
            localSource.setBitrate(Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, 0);
        return bitmap;
    }


}
