package com.example.biglove.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biglove2.R;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;

/**
 * 登录页面
 * 
 * @author sun_zhen
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private Button mRegisterBtn, mLoginBtn;
	private ProgressDialog dialog;
	private EditText userName, pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();

	}

	private void initViews() {
		mRegisterBtn = (Button) findViewById(R.id.login_reg_btn);
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		userName = (EditText) findViewById(R.id.login_username);
		pwd = (EditText) findViewById(R.id.login_pwd);
		dialog = new ProgressDialog(this);
		dialog.setMessage("登录中...");
		dialog.setCanceledOnTouchOutside(false);
		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_reg_btn:// 注册
			startActivityForResult((new Intent(this, RegisterActivity.class)),
					134);
			break;
		case R.id.login_btn:// 登录
			String user = userName.getText().toString().trim();
			String password = pwd.getText().toString().trim();
			if (password.length() < 6 || password.length() > 12) {
				Toast.makeText(this, "密码长度介于6到12位", Toast.LENGTH_SHORT).show();
				return;
			}
			getData("GET", Constant.LOGIN_TAG, Constant.URL
					+ Constant.LOGIN_URL + "?uemail=" + user + "&pwd="
					+ password, null);
			dialog.show();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			finish();
		}
	}

	@Override
	public void handleMsg(Message msg) {
		dialog.dismiss();
		String json = msg.getData().getString("json");
		switch (msg.what) {
		case Constant.LOGIN_TAG:
			if (JsonUtil.parseLoginJson(LoginActivity.this, json) == -1) {// 登录失败
				Toast.makeText(this, JsonUtil.parseMsg(this, json),
						Toast.LENGTH_SHORT).show();
			} else {// 登录成功
				Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, MainActivity.class));
				finish();
			}
			break;
		default:
			break;
		}
	}

}
