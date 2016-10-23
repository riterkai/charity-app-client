package com.example.biglove.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.style.UnderlineSpan;

import com.alibaba.fastjson.JSON;
import com.example.biglove.BigLoveApplacation;
import com.example.biglove.bean.HdBean;
import com.example.biglove.bean.UserBean;
import com.example.biglove.bean.UserHdBean;

/**
 * 解析json
 * 
 * @author xiaoyunfei
 * 
 */
public class JsonUtil {
	public static ArrayList<HdBean> parseHDJson(String json) {
		ArrayList<HdBean> list = new ArrayList<HdBean>();
		HdBean bean = null;
		try {
			JSONObject object = new JSONObject(json);
			JSONArray infoArray = object.getJSONArray("infoArray");
			for (int i = 0; i < infoArray.length(); i++) {
				JSONObject obj = infoArray.getJSONObject(i);
				bean = new HdBean();
				if (obj.has("hdid"))
					bean.setHdid(obj.getInt("hdid"));
				if (obj.has("hdlat"))
					bean.setHdlat(obj.getDouble("hdlat"));
				if (obj.has("hdlon"))
					bean.setHdlon(obj.getDouble("hdlon"));
				if (obj.has("enddate"))
					bean.setEnddate(obj.getString("enddate"));
				if (obj.has("startdate"))
					bean.setStartdate(obj.getString("startdate"));
				if (obj.has("hdImg"))
					bean.setHdImg(obj.getString("hdImg"));
				if (obj.has("hdcontent"))
					bean.setHdcontent(obj.getString("hdcontent"));
				if (obj.has("hdname"))
					bean.setHdname(obj.getString("hdname"));
				if (obj.has("hdsuid"))
					bean.setHdsuid(obj.getInt("hdsuid"));
				if (obj.has("address"))
					bean.setaddress(obj.getString("address"));
				if (obj.has("creattime"))
					bean.setCreattime(obj.getString("creattime"));
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static ArrayList<UserHdBean> parseUserHDJson(String json) {
		ArrayList<UserHdBean> list = new ArrayList<UserHdBean>();
		UserHdBean bean = null;
		try {
			JSONObject object = new JSONObject(json);
			JSONArray infoArray = object.getJSONArray("infoArray");
			for (int i = 0; i < infoArray.length(); i++) {
				JSONObject obj = infoArray.getJSONObject(i);
				bean = new UserHdBean();
				if (obj.has("uname"))
					bean.setUname(obj.getString("uname"));
				if (obj.has("uavatar"))
					bean.setUavatar(obj.getString("uavatar"));
				if (obj.has("hdid"))
					bean.setHdid(obj.getInt("hdid"));
				if (obj.has("hdlat"))
					bean.setHdlat(obj.getDouble("hdlat"));
				if (obj.has("hdlon"))
					bean.setHdlon(obj.getDouble("hdlon"));
				if (obj.has("enddate"))
					bean.setEnddate(obj.getString("enddate"));
				if (obj.has("startdate"))
					bean.setStartdate(obj.getString("startdate"));
				if (obj.has("hdImg"))
					bean.setHdImg(obj.getString("hdImg"));
				if (obj.has("hdcontent"))
					bean.setHdcontent(obj.getString("hdcontent"));
				if (obj.has("hdname"))
					bean.setHdname(obj.getString("hdname"));
				if (obj.has("hdsuid"))
					bean.setHdsuid(obj.getInt("hdsuid"));
				if (obj.has("address"))
					bean.setaddress(obj.getString("address"));
				if (obj.has("creattime"))
					bean.setCreattime(obj.getString("creattime"));
				list.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 
	 * @param context
	 * @param json
	 * @return
	 */
	public static ArrayList<UserBean> parseUserJson(Context context, String json){
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		boolean bo  = JsonUtil.parseCode(context, json);
		if(bo){
			try {
				list = (ArrayList<UserBean>) JSON.parseArray(new  JSONObject(json).getJSONArray("infoArray").toString(), UserBean.class);
				return list;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 解析登录的json数据
	 * 
	 * @param json
	 * @return
	 */
	public static int parseLoginJson(Context context, String json) {

		try {
			JSONObject object = new JSONObject(json);
			int code = object.getInt("code");
			JSONObject subObject = object.getJSONObject("info");
			int uid = subObject.getInt("uid");
			String pwd = subObject.getString("pwd");
			String uphone = subObject.getString("uphone");
			String uschdid = subObject.getString("uschdid");
			double ulat = subObject.getDouble("ulat");
			double ulon = subObject.getDouble("ulon");
			String uname = subObject.getString("uname");
			String usummary = subObject.getString("usummary");
			String uemail = subObject.getString("uemail");
			String uavatar = subObject.getString("uavatar");
			String scwuid = subObject.getString("scwuid");
			String uhdid = subObject.getString("uhdid");
			String wscsuid = subObject.getString("wscsuid");
			String address = subObject.getString("address");
			UserBean bean = new UserBean(uid, uemail, uname, pwd, usummary, uavatar,
					ulon, ulat, uphone, uhdid, uschdid, scwuid, wscsuid,address);
			BigLoveApplacation.setPersonInfoBean(bean);
			SharedPreferencesUtil.setUid(context, uid);
			return code;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 解析登录的json数据
	 * 
	 * @param json
	 * @return
	 */
	public static UserBean parseUserSingleJson(Context context, String json) {

		try {
			JSONObject object = new JSONObject(json);
			int code = object.getInt("code");
			JSONObject subObject = object.getJSONObject("info");
			int uid = subObject.getInt("uid");
			String pwd = subObject.getString("pwd");
			String uphone = subObject.getString("uphone");
			String uschdid = subObject.getString("uschdid");
			double ulat = subObject.getDouble("ulat");
			double ulon = subObject.getDouble("ulon");
			String uname = subObject.getString("uname");
			String usummary = subObject.getString("usummary");
			String uemail = subObject.getString("uemail");
			String uavatar = subObject.getString("uavatar");
			String scwuid = subObject.getString("scwuid");
			String uhdid = subObject.getString("uhdid");
			String wscsuid = subObject.getString("wscsuid");
			String address = subObject.getString("address");
			UserBean bean = new UserBean(uid, uemail, uname, pwd, usummary, uavatar,
					ulon, ulat, uphone, uhdid, uschdid, scwuid, wscsuid,address);
			return bean;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean parseCode(Context context, String json) {
		JSONObject object;
		try {
			object = new JSONObject(json);
			int code = object.getInt("code");
			if(code  == 1){
				return true;
			} else {
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int parseCodeInt(Context context, String json) {
		JSONObject object;
		try {
			object = new JSONObject(json);
			return object.getInt("code");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 解析返回msg
	 * @param context
	 * @param json
	 * @return
	 */
	public static String parseMsg(Context context, String json) {
		JSONObject object;
		try {
			object = new JSONObject(json);
			return object.getString("msg");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int gethdid(String json) throws JSONException{
		
	 JSONObject object = new JSONObject(json);
	 JSONObject subObject = object.getJSONObject("info");
	 int hdid = subObject.getInt("hdid");
	 return hdid;
		
		
	
	}
}
