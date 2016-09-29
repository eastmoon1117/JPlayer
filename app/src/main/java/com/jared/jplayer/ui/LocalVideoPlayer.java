package com.jared.jplayer.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.jared.jplayer.R;
import com.jared.jplayer.app.BaseActivity;
import com.jared.jplayer.common.MyMediaController;
import com.jared.jplayer.databinding.LocalPlayerBinding;

import java.io.IOException;

import io.vov.vitamio.MediaMetadataRetriever;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;

/**
 * Created by jared on 2016/9/29.
 */
public class LocalVideoPlayer extends BaseActivity {

    //private String path="file:///storage/emulated/0/tencent/QQfile_recv/demo.mp4";
    private String subtitle_path = "";
    private long mPosition = 0;

    private LocalPlayerBinding binding;

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, LocalVideoPlayer.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void initParams() {
        Vitamio.isInitialized(getActivity());
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_local_player);
    }

    @Override
    protected void initViews() {
        super.initViews();
        String url = getActivity().getIntent().getStringExtra("url");
        initVideo("file://"+url);

        //getVideoThumbnail("/storage/emulated/0/tencent/QQfile_recv/demo.mp4");
    }

    @Override
    protected void onPause() {
        mPosition = binding.surfaceView.getCurrentPosition();
        binding.surfaceView.stopPlayback();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mPosition > 0) {
            binding.surfaceView.seekTo(mPosition);
            mPosition = 0;
        }
        super.onResume();
        binding.surfaceView.start();
    }

    private void initVideo(String path) {

        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(getActivity(), "Please edit VideoViewSubtitle Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
            return;
        } else {

            binding.surfaceView.setVideoPath(path);

            MyMediaController myMediaController = new MyMediaController(getActivity());
            binding.surfaceView.setMediaController(myMediaController);
            binding.surfaceView.requestFocus();

            binding.surfaceView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    binding.surfaceView.addTimedTextSource(subtitle_path);
                    binding.surfaceView.setTimedTextShown(true);

                }
            });
            binding.surfaceView.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {

                @Override
                public void onTimedText(String text) {
                    binding.subtitleView.setText(text);
                }

                @Override
                public void onTimedTextUpdate(byte[] pixels, int width, int height) {

                }
            });
        }
    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever(getActivity());
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
