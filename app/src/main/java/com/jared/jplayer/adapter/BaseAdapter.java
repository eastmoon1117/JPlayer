package com.jared.jplayer.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.jared.jplayer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jared on 2016/08/04
 */
public abstract class BaseAdapter<T> extends HARecycleAdapter {

    protected LayoutInflater inflater;

    private int lastAnimatedPosition = -1;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    private static final int PHOTO_ANIMATION_DELAY = 30;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    private boolean lockedAnimations = false;
    protected int lastAnimatedItem = -1;

    //内部维护数据源
    private List<T> list = new ArrayList<>();

    public BaseAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, Fragment fragment) {
        super(context, fragment);
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, int noDataViewPos) {
        super(context, noDataViewPos);
        inflater = LayoutInflater.from(context);
    }

    public void appendItems(List items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        int startPosition = list.size();
        list.addAll(items);
        notifyItemRangeInserted(startPosition, items.size());
    }

    public void appendItem(T item) {
        if (item == null) {
            return;
        }
        appendItems(Arrays.asList(item));
    }

    /**
     * list size
     *
     * @return list size
     */
    public int getCount() {
        return list.size();
    }

    public void appendItem(T item, int position) {
        if (item == null) {
            return;
        }
        list.add(position, item);
        notifyDataSetChanged();
        //notifyItemInserted(position);
    }


    public void updateItem(int position) {
        notifyItemChanged(position);
    }


    public void updateItems(int startPosition, int itemCount) {
        notifyItemRangeChanged(startPosition, itemCount);
    }

    public void removeAll() {
        list.clear();
        hideFooter();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position < 0) {
            return;
        }
        list.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position);
    }

    public void removeObject(T object) {
        if (object != null) {
            list.remove(object);
            notifyDataSetChanged();
        }

    }

    public T getModel(int position) {
        if (list.size() == 0) {
            return null;
        }
        return list.get(position);
    }

    public int getHYItemCount() {
        return list.size();
    }

    public List getList() {
        return list;
    }

    public View getLayout(int layoutId, ViewGroup parent) {
        return inflater.inflate(layoutId, parent, false);
    }

    public RecyclerView.ViewHolder getUnKnowViewHolder(ViewGroup parent) {
        return new UnknownViewHolder(inflater.inflate(R.layout.item_unknown, parent, false));
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    public void animatePhoto(RecyclerView.ViewHolder viewHolder, ImageView image) {

        if (lastAnimatedItem > viewHolder.getAdapterPosition()) {
            setLockedAnimations(true);
        } else {
            setLockedAnimations(false);
        }

        if (!lockedAnimations) {

            long animationDelay = PHOTO_ANIMATION_DELAY + viewHolder.getAdapterPosition() * 10;

            image.setAlpha(.3f);

            image.animate()
                    .alpha(1.f)
                    .setDuration(300)
//                    .setInterpolator(INTERPOLATOR)
                    .setStartDelay(animationDelay)
                    .start();
        }
    }

    public void setLockedAnimations(boolean lockedAnimations) {
        this.lockedAnimations = lockedAnimations;
    }
}
