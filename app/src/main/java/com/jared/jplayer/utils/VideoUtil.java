package com.jared.jplayer.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.jared.jplayer.bean.LocalSource;

import java.io.File;

/**
 * Created by jared on 2016/9/30.
 */
public class VideoUtil {

    /**
     * 判断是否为视频文件
     * @param file
     * @return
     */
    public static boolean isVideo(File file) {
        String name = file.getName();
        return nameIsVideo(name);
    }

    public static boolean isVideo(String url) {
        return nameIsVideo(url);
    }

    private static boolean nameIsVideo(String name) {
        //int i = name.indexOf('.');
        int i = name.lastIndexOf('.');
        if (i != -1) {
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

    /**
     * 获取视频的缩略图,播放时长,比特率等信息
     * @param localSource
     * @return bitmap
     */
    public static Bitmap getVideoThumbnail(LocalSource localSource) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(localSource.getUrl());
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

    /** 遍历所有文件夹，查找出视频文件 */
    public static void eachAllMedias(File f, Callback callback) {
        if (f != null && f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : f.listFiles()) {
                    if (file.isDirectory()) {
                        eachAllMedias(file, callback);
                    } else if (file.exists() && file.canRead() && VideoUtil.isVideo(file)) {
                        callback.findFile(file);
                    }
                }
            }
        }
    }

    public interface Callback {
        void findFile(File file);
    }

    /**
     *
     * @param time
     * @return 时间字符串
     */
    public static String timeToString(int time) {
        int second = (time/1000) % 60;
        int minute = (time/1000) / 60;
        return String.format("%02d", minute) +":"+String.format("%02d", second);
    }

    /**
     * 通过时间和比特率计算文件大小
     * @param time
     * @param bitrate
     * @return 文件大小
     */
    public static String getSize(int time, int bitrate) {
        double size = 1.0 * bitrate * time / 8.0 / 1024.0 / 1024.0 / 1024.0;
        return String.format("%.2f", size) + "M";
    }
}
