package com.example.biglove.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biglove2.R;
/**
 * 底部tab之通知图标对应的fragment
 * @author sun_zhen
 *
 */
public class NotifyFragment extends Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_main, null);
			
		}
		ViewGroup group = (ViewGroup) view.getParent();
		if (group != null) {
			group.removeView(view);
		}
		return view;
	}

}
