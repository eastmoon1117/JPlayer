package com.jared.jplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jared.jplayer.R;
import com.jared.jplayer.utils.HADisplayUtil;

public abstract class HARecycleAdapter extends RecyclerView.Adapter<ViewHolder> {

    public boolean mShowFooter;
    public boolean mShowHead;
    protected String mHeadText;
    protected int mHeadImageRes;
    public final static int FOOTER_TYPE = 99;
    public final static int HEAD_TYPE = 100;
    protected Context mContext;
    protected Fragment fragment;
    public static final int TYPE_NO_IMG = -1;

    protected LayoutInflater inflater;

    public HARecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);

    }

    public HARecycleAdapter(Context context, Fragment fragment) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    public HARecycleAdapter(Context context, int noDataViewPos) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.noDataViewPos = noDataViewPos;
    }

    public static class Footer extends ViewHolder {
        public Footer(View itemView) {
            super(itemView);
        }
    }

    public static class Head extends ViewHolder {
        public Head(View itemView) {
            super(itemView);
        }
    }

    public void showFooter() {
        hideHead();
        mShowFooter = true;
    }

    public void hideFooter() {
        mShowFooter = false;
        notifyDataSetChanged();
    }

    public void showHead() {
        mShowHead = true;
        hideFooter();
    }

    public void hideHead() {
        mShowHead = false;
    }

    public void setHeadText(String headText) {
        this.mHeadText = headText;
    }

    public void setHeadImageRes(int res) {
        this.mHeadImageRes = res;
    }

    private int noDataViewPos = 0;

    @Override
    public int getItemViewType(int position) {
        if (mShowHead && position == noDataViewPos) {
            return HEAD_TYPE;
        } else if (mShowFooter && position == getHYItemCount()) {
            return FOOTER_TYPE;
        }
        return getHYItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder arg0, int positon) {
        int type = getItemViewType(positon);
        switch (type) {
            case HEAD_TYPE:
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1,
                        HADisplayUtil.getHeigth(mContext) / 2);
                arg0.itemView.setLayoutParams(params);
                ((TextView) arg0.itemView.findViewById(R.id.tv_error))
                        .setText(mHeadText);
                if (mHeadImageRes != TYPE_NO_IMG) {
                    ((ImageView) arg0.itemView.findViewById(R.id.iv_error))
                            .setImageResource(mHeadImageRes);
                } else {
                    (arg0.itemView.findViewById(R.id.iv_error))
                            .setVisibility(View.GONE);
                }
                break;
            case FOOTER_TYPE:
                break;
            default:
                onHYBindViewHolder(arg0, mShowHead ? positon - 1 : positon);
                break;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HEAD_TYPE:
                return new Footer(LayoutInflater.from(mContext).inflate(
                        R.layout.head_error_layout, null));
            case FOOTER_TYPE:
                return new Footer(LayoutInflater.from(mContext).inflate(
                        R.layout.footer_loading_layout, null));
            default:
                return onHYCreateViewHolder(viewGroup, viewType);
        }
    }

    @Override
    public int getItemCount() {
        return getHYItemCount() + (mShowFooter ? 1 : 0) + (mShowHead ? 1 : 0);
    }

    /**
     * 替代getItemViewType
     *
     * @return
     */
    public abstract int getHYItemCount();

    /**
     * 替代onCreateViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract ViewHolder onHYCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 替代onBindViewHolder
     *
     * @param viewHolder
     * @param position
     */
    public abstract void onHYBindViewHolder(ViewHolder viewHolder, int position);

    /**
     * 替代getItemViewType
     *
     * @param position
     * @return
     */
    public abstract int getHYItemViewType(int position);

    /**
     * 是否可以显示空View,一般重写此接口的情况出现在自己有维护另外一个head,或者多个
     *
     * @return
     */
    protected boolean hasShowEmpty() {
        return true;
    }

    public View inflateLayout(int layoutId, ViewGroup parent) {
        return inflater.inflate(layoutId, parent, false);
    }

    public interface FooterClick {
        void onFooterClick();
    }

    public <F extends android.databinding.ViewDataBinding> F getDataBinding(int layoutId, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, layoutId, parent, false);
    }
}

