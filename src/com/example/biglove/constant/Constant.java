package com.example.biglove.constant;

/**
 * 常量类
 * @author sun_zhen
 *
 */
public class Constant {
	public static final int LOGIN_TAG = 100;//
	public static final int PERSON_INFO_TAG = 101;//
	public static final int ALLHD_TAG = 102;//
	//这个是局域网的ip
	public static final String URL = "http://10.105.3.153:8080";
	
	public static final String LOGIN_URL = "/biglovenew/Login.json";//登录接口
	public static final String REGISTER_URL = URL + "/biglovenew/register.json";//注册接口
	public static final String PERSON_INFO_URL = "/biglovenew/userInfoUpdate.json";//修改个人基本资料接口
	public static final String GET_USER_INFO_URL = URL +"/biglovenew/userInfoGet.json"; // 获取用户信息接口
	
	public static final String PUBLISH_HD_URL = URL+"/biglovenew/publishHd.json"; // 发布活动接口

	public static final String GET_MYHD_URL = URL+"/biglovenew/userhdInfo.json"; // 获得我的活动接口
	public static final String UPDATE_HD_INFO = URL + "/biglovenew/updatehdinfo.json";// 修改活动信息接口
	
	public static final String WHO_COLLECT_ME_URL = URL + "/biglovenew/whoCollectUser.json";//谁收藏我的监控饿了
	public static final String ME_COLLECT_WHO_URL = URL + "/biglovenew/userCollectPerson.json";//我收藏谁的监控饿了
	public static final String COLLECT_HD_ALL_URL = URL +"/biglovenew/usercollecthdinfo.json";// 用户收藏的所有活动信息
	public static final String NOT_COLLECT_HD_URL = URL +"/biglovenew/collectHdAvaiable.json";// 用户未收藏的所有活动信息
	
	public static final String COLLECT_PERSON_URL = URL +"/biglovenew/collectPerson.json";// 收藏某人接口
	public static final String NOT_COLLECT_PERSON_URL = URL +"/biglovenew/cancelCollectSomeone.json";// 取消收藏某人接口
	
	public static final String COLLECT_HD_URL = URL +"/biglovenew/collectSomeHd.json";// 收藏活动接口
	public static final String CANCEL_COLLECT_HD_URL = URL +"/biglovenew/cancelCollectHd.json";// 取消收藏活动接口

	public static final String ISCOLLECT_PERSON_URL = URL +"/biglovenew/IsCollectPerson.json";// 是否收藏某人接口
	public static final String ISCOLLECT_HD_URL = URL +"/biglovenew/IsCollectHd.json";// 是否收藏某一个活动接口
	//public static final String UPLOAD_HDIMG_URL = "http://192.168.1.2/UploadToServer.php";
	public static final String UPLOAD_HDIMG_URL = URL +"/biglovenew/UpLoadHdImgServlet.json";// 上传图片
	public static final String ALLHD_URL = URL +"/biglovenew/AllHdServlet.json";// 全部活動
	public static final String TIME_LINEURL = URL +"/biglovenew/TimelineServlet.json";
	public static final String UPLOAD_USERIMG_URL = URL +"/biglovenew/UpLoadUserImgServlet.json";
	
	
	
 
}
