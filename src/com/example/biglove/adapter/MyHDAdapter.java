package com.example.biglove.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.example.biglove.activity.HDDetailsActivity;
import com.example.biglove.bean.HdBean;
import com.example.biglove.constant.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的活动页面适配器
 * 
 * @author sun_zhen
 * 
 */
public class MyHDAdapter extends BaseAdapter {
	private List<HdBean> list;
	private Context context;
	private LayoutInflater inflater;

	public MyHDAdapter(List<HdBean> list, Context context) {
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
			holder.hdName = (TextView) view.findViewById(R.id.hd_name_tv);
			holder.hdAddress = (TextView) view.findViewById(R.id.hd_address_tv);
			holder.hdDate = (TextView) view.findViewById(R.id.hd_date_tv);
			holder.hdImg = (ImageView) view.findViewById(R.id.hd_img);
			holder.hdMoreBtn = (Button) view.findViewById(R.id.hd_more_btn);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.topRativeLayout.setVisibility(View.GONE);
		holder.hdName.setText(list.get(position).getHdname());
		holder.hdAddress.setText(list.get(position).getaddress());
		holder.hdAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		holder.hdDate.setText("开始日期" + list.get(position).getStartdate());
		ImageLoader.getInstance().displayImage(
				Constant.URL + list.get(position).getHdImg(), holder.hdImg);
		holder.hdMoreBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, HDDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("hdbean", list.get(position));
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});

		return view;
	}

	public void addData(ArrayList<HdBean> beans) {
		list = beans;
	}

	static class ViewHolder {
		RelativeLayout topRativeLayout;
		TextView hdName;
		TextView hdAddress;
		TextView hdDate;
		ImageView hdImg;
		Button hdMoreBtn;
	}

}
