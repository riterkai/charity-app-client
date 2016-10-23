package com.example.biglove.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	/**
	 * 存储用户id
	 * 
	 * @param context
	 * @param uid
	 */
	public static void setUid(Context context, int uid) {
		SharedPreferences preferences = context.getSharedPreferences("uid",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("uid", uid);
		editor.commit();
	}

	/**
	 * 获得用户的id
	 * 
	 * @param context
	 * @return
	 */
	public static int getUid(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("uid",
				Context.MODE_PRIVATE);
		int uid = preferences.getInt("uid", 0);
		return uid;
	}

	public static void setRefreshTime(Context context, String tag, String msg) {
		SharedPreferences preferences = context.getSharedPreferences(tag,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(tag, msg);
		editor.commit();
	}

	public static String getRefreshTime(Context context, String tag) {
		SharedPreferences preferences = context.getSharedPreferences(tag,
				Context.MODE_PRIVATE);
		String time = preferences.getString(tag, "�ո�");
		return time;
	}
}
