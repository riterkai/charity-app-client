package com.example.biglove.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biglove.BigLoveApplacation;
import com.example.biglove2.R;
import com.example.biglove.adapter.MyHDAdapter;
import com.example.biglove.bean.HdBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove.view.XListView;
import com.example.biglove.view.XListView.IXListViewListener;

/**
 * 我的活动fragment
 * 
 * @author sun_zhen
 * 
 */
public class MyHDFragment extends BaseFragment implements IXListViewListener {
	private View view;
	private TextView title_left;
	private XListView mListView;
	private MyHDAdapter mAdapter;
	private List<HdBean> list = new ArrayList<HdBean>();
	BigLoveApplacation application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_myhd, null);
			initviews();

		} else {
			ViewGroup group = (ViewGroup) view.getParent();
			if (group != null) {
				group.removeView(view);
			}
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (application.tag) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
			getData("POST", TAG_REQUEST_REFRESH, Constant.GET_MYHD_URL, map);
		}
	}

	private void initviews() {
		application = (BigLoveApplacation) getActivity().getApplication();
		title_left = (TextView) view.findViewById(R.id.title_left);
		title_left.setVisibility(View.GONE);
		mListView = (XListView) view.findViewById(R.id.xListView);
		mAdapter = new MyHDAdapter(list, getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mListView.setFooterDividersEnabled(false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
		getData("POST", TAG_REQUEST_MYHD, Constant.GET_MYHD_URL, map);
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case TAG_REQUEST_MYHD:
			String json = msg.getData().getString("json");
			ArrayList<HdBean> beans = JsonUtil.parseHDJson(json);
			mAdapter.addData(beans);
			mAdapter.notifyDataSetChanged();
			break;
		case TAG_REQUEST_REFRESH:
			String json1 = msg.getData().getString("json");
			ArrayList<HdBean> beans1 = JsonUtil.parseHDJson(json1);
			mAdapter.addData(beans1);
			mAdapter.notifyDataSetChanged();
			handler.sendEmptyMessageDelayed(1, 500);
			application.tag = false;
			break;
		case 1:
			mListView.stopRefresh();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
		getData("POST", TAG_REQUEST_REFRESH, Constant.GET_MYHD_URL, map);

	}

	@Override
	public void onLoadMore() {

	}
}
