package com.example.net;

/**
 * 请求数据回调接口
 * 
 * @author asker 2014-5-17
 * 
 */
public interface RequestListenr {

	void onCompelete(int tag, String json);//

	void onException(String exception); //

}
