package com.example.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AsyncGameRunner {
	public static void request(String requestStyle, final int tag,
			final String url, final RequestListenr re, Context context, Map m) {
		RequestQueue queue = Volley.newRequestQueue(context);

		if ("GET".equals(requestStyle)) {

			getReqest(tag, url, re, queue);
		} else {
			postReqest(tag, url, re, queue, m);
		}

	}

	public static void getReqest(final int tag, final String url,
			final RequestListenr re, RequestQueue queue) {
		//StringRequest： 表示将请求的数据转化为字符串返回
		StringRequest sr = new StringRequest(Request.Method.GET, url,
				new Listener<String>() {// 处理返回请求返回的结果
					@Override
					public void onResponse(String arg0) {
						re.onCompelete(tag, arg0);
						Log.d("asker", "请求成功=========" + arg0);

					}
				}, new Response.ErrorListener() {// 请求出错，处理出错程序

					@Override
					public void onErrorResponse(VolleyError arg0) {
						re.onException(arg0.toString());
						Log.d("vivi", "请求失败=========" + arg0.toString());

					}
				});

		queue.add(sr);
	}

	public static void postReqest(final int tag, final String url,
			final RequestListenr re, RequestQueue queue, final Map m) {
		StringRequest sr = new StringRequest(Request.Method.POST, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						re.onCompelete(tag, arg0);
						Log.d("asker", "请求成功=========" + arg0);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						re.onException(arg0.toString());
						Log.d("vivi", "请求失败=========" + arg0.toString());
						File file = new File("/mnt/sdcard/a.text");
						FileWriter fw = null;
						try {
							if (!file.exists()) {
								file.createNewFile();
							}
							fw = new FileWriter(file);
							BufferedWriter out = new BufferedWriter(fw);
							out.write(arg0.toString(), 0, arg0.toString()
									.length() - 1);
							out.flush();
							out.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return m;
			}
		};

		queue.add(sr);
	}

}
