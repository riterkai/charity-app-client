package com.example.biglove.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class NetWorkUtil {
	public static boolean isNetWorkAvailable(Context context){
		ConnectivityManager conn= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=conn.getActiveNetworkInfo();
		if(info!=null&&info.isAvailable())
			return true;
		return false;
	}

}
