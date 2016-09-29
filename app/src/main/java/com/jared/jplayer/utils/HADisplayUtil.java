/**
 * Copyright © 2014 XiaoKa. All Rights Reserved
 */
package com.jared.jplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 显示相关的工具类
 * 
 */
public class HADisplayUtil {

	public static int getWidth(Context context){
		int screenWidth  = context.getResources().getDisplayMetrics().widthPixels;  
		return screenWidth;
	}
	
	public static int getHeigth(Context context){
		int screenHeight  = context.getResources().getDisplayMetrics().heightPixels;
		return screenHeight;
	}

	public static int getHeigth(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * DP转化为PX
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * PX转化为DP
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 设置全屏
	 * 
	 * @param act
	 */
	public static void fullScreen(Activity act) {
		act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 退出全屏
	 * 
	 * @param act
	 */
	public static void quitFullScreen(Activity act) {
		final WindowManager.LayoutParams attrs = act.getWindow()
				.getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		act.getWindow().setAttributes(attrs);
		act.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	/**
	 * 设置屏幕方向为横屏
	 * 
	 * @param act
	 */
	public static void setRequestedOrientationLANDSCAPE(Activity act) {
		act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * 设置屏幕方向为竖屏
	 * 
	 * @param act
	 */
	public static void setRequestedOrientationPORTRAIT(Activity act) {
		act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

}
