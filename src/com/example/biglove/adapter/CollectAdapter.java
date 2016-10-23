package com.example.biglove.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biglove2.R;
import com.example.biglove.bean.UserBean;
import com.example.biglove.constant.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CollectAdapter extends BaseAdapter {
	private List<UserBean> list;
	private LayoutInflater inflater;

	public CollectAdapter(List<UserBean> list, Context context) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.who_collect_me_item, null);
			holder.head = (ImageView) view.findViewById(R.id.head_img);
			holder.zan = (ImageView) view.findViewById(R.id.collect_img);
			holder.name = (TextView) view.findViewById(R.id.name_text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.name.setText(list.get(position).getUname());
		ImageLoader.getInstance().displayImage(
				Constant.URL + list.get(position).getUavatar(), holder.head);
		return view;
	}

	public void addData(ArrayList<UserBean> beans) {
		this.list = beans;
	}

	static class ViewHolder {
		ImageView head, zan;
		TextView name;
	}

}
