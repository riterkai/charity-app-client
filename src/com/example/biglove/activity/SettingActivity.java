package com.example.biglove.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.biglove2.R;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		backBtn = (Button) findViewById(R.id.action_back);
		backBtn.setOnClickListener(this);
	}

	@Override
	public void handleMsg(Message msg) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_back:
			finish();
			break;

		default:
			break;
		}
	}

}
