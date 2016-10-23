package com.example.biglove.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.biglove2.R;
import com.example.biglove.activity.CollectedHdDetailsActivity;
import com.example.biglove.activity.PersonInfoActivity;
import com.example.biglove.bean.UserHdBean;
import com.example.biglove.constant.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyCollectHD2Adapter extends BaseAdapter {
	private List<UserHdBean> list;
	private Context context;
	private LayoutInflater inflater;

	public MyCollectHD2Adapter(List<UserHdBean> list, Context context) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int position, View view, ViewGroup group) {
		ViewHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.myhd_item, null);
			holder = new ViewHolder();
			holder.topRativeLayout = (RelativeLayout) view
					.findViewById(R.id.top_two_rel);
			holder.userAvatarImg = (ImageView) view
					.findViewById(R.id.action_user_head_img);
			holder.userName = (TextView) view
					.findViewById(R.id.action_user_name_text);
			holder.hdName = (TextView) view.findViewById(R.id.hd_name_tv);
			holder.hdAddress = (TextView) view.findViewById(R.id.hd_address_tv);
			holder.hdDate = (TextView) view.findViewById(R.id.hd_date_tv);
			holder.hdImg = (ImageView) view.findViewById(R.id.hd_img);
			holder.hdMoreBtn = (Button) view.findViewById(R.id.hd_more_btn);
			holder.datetext=(TextView) view.findViewById(R.id.Datetext);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.userName.setText(list.get(position).getUname());
		holder.hdName.setText(list.get(position).getHdname());
		holder.hdAddress.setText(list.get(position).getaddress());
		holder.hdAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		holder.hdDate.setText("开始日期" + list.get(position).getStartdate());
		holder.datetext.setText("");
		
		ImageLoader.getInstance().displayImage(
				Constant.URL + list.get(position).getHdImg(), holder.hdImg);
		ImageLoader.getInstance().displayImage(
				Constant.URL + list.get(position).getUavatar(),
				holder.userAvatarImg);
		holder.hdMoreBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,
						CollectedHdDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("uh", list.get(position));
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		holder.topRativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, PersonInfoActivity.class)
						.putExtra("uid", list.get(position).getHdsuid());
				Log.d("sunzhen", list.get(position).getHdsuid() + "这是活动是谁的 uid");
				context.startActivity(intent);
			}
		});
		return view;
	}

	
	
	
	public String caculatetime(String startTime){
		
		  
		
		  
		//java.util.Calendar c1=java.util.Calendar.getInstance();
		  
		//java.util.Calendar c2=java.util.Calendar.getInstance();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		
		     
		        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数    
		        long nh = 1000 * 60 * 60;// 一小时的毫秒数    
		        long nm = 1000 * 60;// 一分钟的毫秒数    
		        long ns = 1000;// 一秒钟的毫秒数    
		        long diff = 0;    
		        long day = 0;    
		        long hour = 0;    
		        long min = 0;    
		        long sec = 0;    
		        // 获得两个时间的毫秒时间差异    
		        try {
		        	
		            //diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		        	try {
						diff = System.currentTimeMillis()- df.parse(startTime).getTime();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            day = diff / nd;// 计算差多少天    
		            hour = diff % nd / nh + day * 24;// 计算差多少小时    
		            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟    
		            if (day == 0 && hour == 0){
		            	String output =min+"分前";
		            	return output;
		            } else if (day == 0){
		            	String output =hour+"小時前";
		            	return output;	
		            }else{
		            	return startTime+"發佈";
		            }
		           
		        } catch (ParseException e) {    
		            // TODO Auto-generated catch block    
		            e.printStackTrace();    
		        }    
		return "3";
		
	}
	
	
	
	
	
	
	public void addData(ArrayList<UserHdBean> beans) {
		list = beans;
	}

	static class ViewHolder {
		RelativeLayout topRativeLayout;
		TextView userName;
		ImageView userAvatarImg;
		TextView hdName;
		TextView hdAddress;
		TextView hdDate;
		ImageView hdImg;
		Button hdMoreBtn;
		TextView datetext;
	}

}
