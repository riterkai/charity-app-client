package com.example.biglove.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HdBean implements Parcelable {
	public int hdid;
	public String hdname;
	public double hdlon;
	public double hdlat;
	public String startdate;// 开始时间
	public String enddate; // 结束时间
	public String hdImg; // 活动的 图片
	public String hdcontent; // 活动内容
	public int hdsuid;
	public String address;
	public String creattime;

	public HdBean() {
		super();
	}

	public HdBean(int hdid, String hdname, double hdlon, double hdlat,
			String startdate, String enddate, String hdImg, String hdcontent,
			int hdsuid) {
		super();
		this.hdid = hdid;
		this.hdname = hdname;
		this.hdlon = hdlon;
		this.hdlat = hdlat;
		this.startdate = startdate;
		this.enddate = enddate;
		this.hdImg = hdImg;
		this.hdcontent = hdcontent;
		this.hdsuid = hdsuid;
	}
	public HdBean(int hdid, String hdname, double hdlon, double hdlat,
			String startdate, String enddate, String hdImg, String hdcontent,
			int hdsuid,String address) {
		super();
		this.hdid = hdid;
		this.hdname = hdname;
		this.hdlon = hdlon;
		this.hdlat = hdlat;
		this.startdate = startdate;
		this.enddate = enddate;
		this.hdImg = hdImg;
		this.hdcontent = hdcontent;
		this.hdsuid = hdsuid;
		this.address= address;
	}
	
	public HdBean(int hdid, String hdname, double hdlon, double hdlat,
			String startdate, String enddate, String hdImg, String hdcontent,
			int hdsuid,String address,String creattime) {
		super();
		this.hdid = hdid;
		this.hdname = hdname;
		this.hdlon = hdlon;
		this.hdlat = hdlat;
		this.startdate = startdate;
		this.enddate = enddate;
		this.hdImg = hdImg;
		this.hdcontent = hdcontent;
		this.hdsuid = hdsuid;
		this.address = address;
		this.creattime = creattime; 
	}
	

	public HdBean(Parcel in) {
		hdid = in.readInt();
		hdname = in.readString();
		hdlon = in.readDouble();
		hdlat = in.readDouble();
		startdate = in.readString();// 开始时间
		enddate = in.readString(); // 结束时间
		hdImg = in.readString(); // 活动的 图片
		hdcontent = in.readString(); // 活动内容
		hdsuid = in.readInt();
		address = in.readString();
		creattime =in.readString();
	}

	public int getHdid() {
		return hdid;
	}

	public void setHdid(int hdid) {
		this.hdid = hdid;
	}

	public String getHdname() {
		return hdname;
	}

	public void setHdname(String hdname) {
		this.hdname = hdname;
	}

	public double getHdlon() {
		return hdlon;
	}

	public void setHdlon(double hdlon) {
		this.hdlon = hdlon;
	}

	public double getHdlat() {
		return hdlat;
	}

	public void setHdlat(double hdlat) {
		this.hdlat = hdlat;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getHdImg() {
		return hdImg;
	}

	public void setHdImg(String hdImg) {
		this.hdImg = hdImg;
	}

	public String getHdcontent() {
		return hdcontent;
	}

	public void setHdcontent(String hdcontent) {
		this.hdcontent = hdcontent;
	}

	public int getHdsuid() {
		return hdsuid;
	}

	public void setHdsuid(int hdsuid) {
		this.hdsuid = hdsuid;
	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}
	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}
	
	
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(hdid);
		out.writeString(hdname);
		out.writeDouble(hdlon);
		out.writeDouble(hdlat);
		out.writeString(startdate);
		out.writeString(enddate);
		out.writeString(hdImg);
		out.writeString(hdcontent);
		out.writeInt(hdsuid);
		out.writeString(address);
		out.writeString(creattime);

	}

	public static final Creator<HdBean> CREATOR = new Creator<HdBean>() {

		@Override
		public HdBean[] newArray(int size) {
			return new HdBean[size];
		}

		@Override
		public HdBean createFromParcel(Parcel in) {
			return new HdBean(in);
		}
	};

}
