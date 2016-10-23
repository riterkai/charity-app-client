package com.example.biglove.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biglove.BigLoveApplacation;
import com.example.biglove2.R;
import com.example.biglove.adapter.MyCollectHD2Adapter;
import com.example.biglove.adapter.MyCollectHDAdapter;
import com.example.biglove.bean.UserHdBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove.view.XListView;
import com.example.biglove.view.XListView.IXListViewListener;

/**
 * 底部tab之红心图标对应的fragment
 * 
 * @author sun_zhen
 * 
 */
public class MyCollectHDFragment2 extends BaseFragment implements
		IXListViewListener {
	private View view;
	private XListView mListView;
	private MyCollectHD2Adapter mAdapter;
	private List<UserHdBean> list = new ArrayList<UserHdBean>();
	BigLoveApplacation application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.who_collect_me, null);
			initviews();

		} else {
			ViewGroup group = (ViewGroup) view.getParent();
			if (group != null) {
				group.removeView(view);
			}
		}
		return view;
	}

	private void initviews() {
		application = (BigLoveApplacation) getActivity().getApplication();
		mListView = (XListView) view.findViewById(R.id.xListView);
		mAdapter = new MyCollectHD2Adapter(list, getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(this);
		mListView.setFooterDividersEnabled(false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
		getData("POST", TAG_REQUEST_MYCOLLECTHD, Constant.COLLECT_HD_ALL_URL,
				map);
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case TAG_REQUEST_MYCOLLECTHD:
			String json = msg.getData().getString("json");
			ArrayList<UserHdBean> beans = JsonUtil.parseUserHDJson(json);
			mAdapter.addData(beans);
			mAdapter.notifyDataSetChanged();
			break;
		case TAG_REQUEST_REFRESH:
			String json1 = msg.getData().getString("json");
			ArrayList<UserHdBean> beans1 = JsonUtil.parseUserHDJson(json1);
			mAdapter.addData(beans1);
			mAdapter.notifyDataSetChanged();
			handler.sendEmptyMessageDelayed(1, 500);
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
		getData("POST", TAG_REQUEST_REFRESH, Constant.COLLECT_HD_ALL_URL, map);

	}

	@Override
	public void onLoadMore() {

	}
}