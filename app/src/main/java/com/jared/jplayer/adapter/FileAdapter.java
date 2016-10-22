package com.jared.jplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jared.jplayer.R;
import com.jared.jplayer.bean.LocalSource;
import com.jared.jplayer.databinding.ItemLocalSrcBinding;
import com.jared.jplayer.ui.LocalVideoPlayer;
import com.jared.jplayer.utils.VideoUtil;

/**
 * Created by jared on 2016/9/29.
 */
public class FileAdapter  extends BaseAdapter<LocalSource> {

    public FileAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onHYCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLocalSrcBinding itemLocalSrcBinding = getDataBinding(R.layout.item_local_source, parent);
        return new FileViewHolder(itemLocalSrcBinding);
    }

    @Override
    public void onHYBindViewHolder(RecyclerView.ViewHolder arg0, int position) {
        final FileViewHolder fileViewHolder = (FileViewHolder) arg0;
        LocalSource localSource = getModel(position);
        fileViewHolder.binding.tvName.setText(localSource.getName());
        if (localSource.getBitmap() != null)
            fileViewHolder.binding.ivCover.setImageBitmap(localSource.getBitmap());

        fileViewHolder.binding.tvDuration.setText(VideoUtil.timeToString(localSource.getDuration()));
        fileViewHolder.binding.tvSize.setText(VideoUtil.getSize(localSource.getDuration(), localSource.getBitrate()));
        fileViewHolder.binding.cardView.setTag(localSource);
        fileViewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalSource localSource = (LocalSource)view.getTag();
                LocalVideoPlayer.launch(mContext, localSource.getUrl());
            }
        });
    }

    @Override
    public int getHYItemViewType(int position) {
        return 0;
    }

    public static class FileViewHolder extends BaseViewHolder<ItemLocalSrcBinding> {

        public FileViewHolder(ItemLocalSrcBinding binding) {
            super(binding);
        }
    }
}