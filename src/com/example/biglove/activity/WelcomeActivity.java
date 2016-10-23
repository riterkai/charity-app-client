package com.example.biglove.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.biglove2.R;

public class WelcomeActivity extends Activity {
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(WelcomeActivity.this,
						LoginActivity.class));
				finish();
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			}
		}, 2000);
	}
}
