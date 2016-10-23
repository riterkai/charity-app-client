package com.example.biglove.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.biglove2.R;
import com.example.biglove.activity.PersonInfoActivity;
import com.example.biglove.adapter.CollectAdapter;
import com.example.biglove.bean.UserBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove.view.XListView;
import com.example.biglove.view.XListView.IXListViewListener;

/**
 * 我收藏谁fragment
 * 
 * @author sun_zhen
 * 
 */
public class UserCollectWhoFragment extends BaseFragment implements
		IXListViewListener {
	private View view;
	private XListView mListView;
	private ArrayList<UserBean> list = new ArrayList<UserBean>();
	private CollectAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.who_collect_me, null);
			initViews();
		} else {
			ViewGroup group = (ViewGroup) view.getParent();
			if (group != null) {
				group.removeView(view);
			}
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				Intent intent = new Intent(getActivity(),
						PersonInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("userbean", list.get(position - 1));
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
			}
		});

		return view;
	}

	private void initViews() {
		mListView = (XListView) view.findViewById(R.id.xListView);
		adapter = new CollectAdapter(list, getActivity());
		mListView.setAdapter(adapter);
		mListView.setXListViewListener(this);
		mListView.setFooterDividersEnabled(false);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
		getData("POST", TAG_ME_COLLECT_WHO, Constant.ME_COLLECT_WHO_URL, map);
	}

	@Override
	public void handleMsg(Message msg) {
		String json = msg.getData().getString("json");
		switch (msg.what) {
		case TAG_ME_COLLECT_WHO:
			list = JsonUtil.parseUserJson(getActivity(), json);
			adapter.addData(list);
			adapter.notifyDataSetChanged();
			break;

		case TAG_REQUEST_REFRESH:
			list = JsonUtil.parseUserJson(getActivity(), json);
			adapter.addData(list);
			adapter.notifyDataSetChanged();
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
		getData("POST", TAG_REQUEST_REFRESH, Constant.ME_COLLECT_WHO_URL, map);
	}

	@Override
	public void onLoadMore() {

	}

}
