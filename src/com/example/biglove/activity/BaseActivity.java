package com.example.biglove.activity;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.example.net.AsyncGameRunner;
import com.example.net.RequestListenr;


public abstract class BaseActivity extends FragmentActivity implements RequestListenr {
	public static final int TAG_ERROES = 200;
	public static final int TAG_REGISTER = 289;
	public static final int TAG_UPDATE_HD = 21;
	public static final int TAG_GET_USERINFO = 777;
	public static final int TAG_PERSON_ISCOLLECT = 128;
	public static final int TAG_HD_ISCOLLECT = 797;


	public static final String JSON = "json";

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handleMsg(msg);
		};
	};

	public abstract void handleMsg(android.os.Message msg);

	public void getData(String str, int tag, String url, Map map) {
		AsyncGameRunner.request(str, tag, url, this, this, map);
	}

	/**
	 * 请求成功
	 */
	@Override
	public void onCompelete(int tag, String json) {
		Message msg = handler.obtainMessage();
		msg.what = tag;
		Bundle data = new Bundle();
		data.putString("json", json);
		msg.setData(data);
		handler.sendMessage(msg);
	}

	/**
	 * 请求出现异常时
	 */

	@Override
	public void onException(String exception) {
		handler.sendEmptyMessage(TAG_ERROES);
	}

}
