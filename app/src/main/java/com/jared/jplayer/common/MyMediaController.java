package com.jared.jplayer.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.jared.jplayer.R;

import io.vov.vitamio.widget.MediaController;

/**
 * Created by jared on 2016/9/28.
 */
public class MyMediaController extends MediaController {

    Context mContext;

    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MyMediaController(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected View makeControllerView() {
        return ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mymediacontroller, this);
        //return super.makeControllerView();
    }

}
