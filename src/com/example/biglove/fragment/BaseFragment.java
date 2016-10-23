package com.example.biglove.fragment;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.example.net.AsyncGameRunner;

/**
 * 基类fragment
 * 
 * @author sun_zhen
 * 
 */
public abstract class BaseFragment extends Fragment implements
		com.example.net.RequestListenr {

	public static final int TAG_ERROE = 100;// 数据请求错误
	public static final int TAG_REQUEST_MYHD = 200;//
	public static final int TAG_REQUEST_TIMELINEHD = 250;//
	public static final int TAG_REQUEST_REFRESH = 300;//
	public static final int TAG_WHO_COLLECT_ME = 400;//
	public static final int TAG_ME_COLLECT_WHO = 500;//

	public static final int TAG_REQUEST_MYCOLLECTHD = 700;//
	public static final int TAG_REQUEST_MY_NOT_COLLECTHD = 800;//
	public static final int TAG_PUBLISH_HD = 1003;

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handleMsg(msg);
		};
	};

	public abstract void handleMsg(android.os.Message msg);

	public void getData(String method, int tag, String url, HashMap<String, String> map) {

		AsyncGameRunner.request(method, tag, url, this, getActivity(), map);

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
		handler.sendEmptyMessage(TAG_ERROE);
	}
}
