package com.example.biglove.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biglove2.R;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private Button mRegisterBtn;
	private TextView back_login;
	private EditText input_email, input_pwd, input_confirm_pwd;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initViews();
		mRegisterBtn = (Button) findViewById(R.id.reg_btn);
		mRegisterBtn.setOnClickListener(this);
	}

	private void initViews() {
		back_login = (TextView) findViewById(R.id.back_login);
		input_email = (EditText) findViewById(R.id.input_email);
		input_pwd = (EditText) findViewById(R.id.input_pwd);
		input_confirm_pwd = (EditText) findViewById(R.id.input_confirm_pwd);
		mRegisterBtn = (Button) findViewById(R.id.reg_btn);
		mRegisterBtn.setOnClickListener(this);
		back_login.setOnClickListener(this);
		back_login.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在注册...");
		dialog.setCanceledOnTouchOutside(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_login:
			finish();
			break;
		case R.id.reg_btn:
			String email = input_email.getText().toString();
			String pwd = input_pwd.getText().toString();
			String pwd_confim = input_confirm_pwd.getText().toString();
			if (TextUtils.isEmpty(email)) {
				Toast.makeText(this, "请先填写邮箱", Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(pwd)) {
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (TextUtils.isEmpty(pwd_confim)) {
				Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!pwd.equals(pwd_confim)) {
				Toast.makeText(this, "两次输入密码不一致，请核对再试", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("uemail", email);
			map.put("pwd", pwd);
			getData("POST", TAG_REGISTER, Constant.REGISTER_URL, map);
			dialog.show();
			break;

		default:
			break;
		}

	}

	@Override
	public void handleMsg(Message msg) {
		dialog.show();
		String json = msg.getData().getString("json");
		Log.d("sunzhen", "注册返回json是" + json);
		switch (msg.what) {
		case TAG_REGISTER:
			int code = JsonUtil.parseLoginJson(this, json);
			if (code == 1) {
				Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, MainActivity.class));
				setResult(RESULT_OK);
				finish();
			} else {
				Toast.makeText(this, JsonUtil.parseMsg(this, json),
						Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}
	}
}
