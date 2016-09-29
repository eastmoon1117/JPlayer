package com.jared.jplayer.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jared on 16/08/04
 */
public class BaseViewHolder<T extends android.databinding.ViewDataBinding> extends RecyclerView.ViewHolder {
    public T binding;

    public BaseViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}