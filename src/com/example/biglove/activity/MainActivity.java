package com.example.biglove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.example.biglove2.R;
import com.example.biglove.fragment.MapFragment;
import com.example.biglove.fragment.MyCollectHDFragment;
import com.example.biglove.fragment.NotifyFragment;
import com.example.biglove.fragment.PersonalFragment;
import com.example.biglove.fragment.PublishHDFragment;

/**
 * 主页activity
 * 
 * @author sun_zhen
 * 
 */
public class MainActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;
	private LayoutInflater layoutInflater;
	private long mExitTime;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { MapFragment.class,
			MyCollectHDFragment.class, PublishHDFragment.class,
			PersonalFragment.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_works_btn, R.drawable.tab_photo_btn,
			R.drawable.tab_person_btn };

	// 每个Fragment的标签
	private String[] tag = { "home", "works", "photo", "person" };

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activiy_main);
		initView();
	}

	private void initView() {
		layoutInflater = LayoutInflater.from(this);// 实例化布局对象
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);// 实例化TabHost对象，得到TabHost
		mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(tag[i]);
			tabSpec.setIndicator(getTabItemView(i));

			mTabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		mTabHost.setCurrentTabByTag(tag[3]);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}

	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		return view;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {// back退出应用程序
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// 处理连按退出
			if ((System.currentTimeMillis() - mExitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(getApplicationContext(), "再按一次退出",
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

}
