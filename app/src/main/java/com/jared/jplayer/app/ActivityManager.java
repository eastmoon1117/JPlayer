package com.jared.jplayer.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {
	private static Stack<Activity> mActivityStack;
	private static ActivityManager instance;
	public final static String TAG = ActivityManager.class.getSimpleName();

	private ActivityManager() {
		mActivityStack = new Stack<Activity>();
	}

	public static ActivityManager getInstance() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	/**
	 * 销毁最近使用的activity(前提已经添加进入了管理器)
	 * 
	 * @return true=销毁成功,false=集合为空没有或者activity==null
	 */
	public boolean finishActivity() {
		if (mActivityStack.size() == 0)
			return false;
		Activity activity = mActivityStack.lastElement();
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
			return true;
		}
		return false;
	}

	/**
	 * 将某个activity销毁掉
	 * 
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			mActivityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 销毁指定的Ativity
	 * 
	 * @param actName
	 */
	@SuppressLint("DefaultLocale")
	public void finishActivity(String actName) {
		Log.d(TAG, "remove activity , name = " + actName);
		if (actName != null) {
			Iterator<Activity> i = mActivityStack.iterator();
			while (i.hasNext()) {
				Activity activity = (Activity) i.next();
				if (null != activity) {
					if (activity.getClass().getSimpleName().toLowerCase()
							.equals(actName.toLowerCase())) {
						i.remove();
						//activity.finish();
						activity = null;
					}
				}
			}
		}
	}

	/**
	 * 销毁除参数指定Activity之外的所有Activity
	 * 
	 * @param actName
	 */
	@SuppressLint("DefaultLocale")
	public void finishOther(String actName) {
		if (actName != null) {
			Iterator<Activity> i = mActivityStack.iterator();
			while (i.hasNext()) {
				Activity activity = (Activity) i.next();
				if (null != activity) {
					if (!activity.getClass().getSimpleName().toLowerCase()
							.equals(actName.toLowerCase())) {
						i.remove();
						activity.finish();
						activity = null;
					}
				}
			}
		}
	}

	@SuppressLint("DefaultLocale")
	public boolean checkActivity(String actName) {
		if (actName != null) {
			Iterator<Activity> i = mActivityStack.iterator();
			while (i.hasNext()) {
				Activity activity = (Activity) i.next();
				if (null != activity) {
					if (activity.getClass().getSimpleName().toLowerCase()
							.equals(actName.toLowerCase())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Activity getActivity(String actName) {
		if (actName != null) {
			Iterator<Activity> i = mActivityStack.iterator();
			while (i.hasNext()) {
				Activity activity = (Activity) i.next();
				if (null != activity) {
					if (activity.getClass().getSimpleName().toLowerCase()
							.equals(actName.toLowerCase())) {
						return activity;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获得最近使用的activity实例
	 * 
	 * @return
	 */
	public synchronized Activity getCurrentActivity() {
		return mActivityStack.lastElement();
	}

	/**
	 * 添加一个Activity
	 * 
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		if (mActivityStack.contains(activity)) {
			mActivityStack.remove(activity);
		}
		mActivityStack.add(activity);
	}

	/**
	 * 销毁所有的activity.System.exit(0)方式退出app
	 */
	public void finishAll() {
		while (true) {
			if (!finishActivity())
				break;
		}
	}

	public int getSize() {
		return null == mActivityStack ? 0 : mActivityStack.size();
	}

	public Stack<Activity> getActivityStack() {
		return mActivityStack;
	}
}
