package com.example.biglove.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biglove2.R;
import com.example.biglove.bean.UserBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonInfoActivity extends BaseActivity implements OnClickListener {
	private Button backBtn;
	private UserBean bean;
	private ImageView headImg;
	private EditText nameText, addressText, telText;
	private EditText introEdit;
	private ImageButton hotBtn;
	private int uid;
	private ImageView markerImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);
		Intent intent = getIntent();
		bean = intent.getParcelableExtra("userbean");
		uid = intent.getIntExtra("uid", 0);
		
		if (uid != 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("uid", uid + "");
			getData("POST", TAG_GET_USERINFO, Constant.GET_USER_INFO_URL, map);
			Map<String, String> params = new HashMap<String, String>();
			params.put("uid", SharedPreferencesUtil.getUid(this)+"");
			params.put("personID", uid + "");
			getData("POST", TAG_PERSON_ISCOLLECT, Constant.ISCOLLECT_PERSON_URL, params);
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("uid", SharedPreferencesUtil.getUid(this)+"");
			params.put("personID", bean.getUid()+"");
			getData("POST", TAG_PERSON_ISCOLLECT, Constant.ISCOLLECT_PERSON_URL, params);
		}
		initViews();
	}

	private void initViews() {
		hotBtn = (ImageButton) findViewById(R.id.title_right);
		backBtn = (Button) findViewById(R.id.action_back);
		markerImg = (ImageView) findViewById(R.id.marker_img);
		headImg = (ImageView) findViewById(R.id.person_head_img);
		nameText = (EditText) findViewById(R.id.person_name_text);
		addressText = (EditText) findViewById(R.id.person_address_text);
		telText = (EditText) findViewById(R.id.person_tel_text);
		introEdit = (EditText) findViewById(R.id.intro_edit);
		hotBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		markerImg.setOnClickListener(new View.OnClickListener() {
	        
	        public void onClick(View v) {
	            
	        	  HashMap<String, String> map1 = new HashMap<String, String>();
			
				  map1.put("address", bean.getAddress());
				  map1.put("lat",String.valueOf(bean.getUlat()));
				  map1.put("lon", String.valueOf(bean.getUlon()));
				  Intent intent = new Intent(PersonInfoActivity.this,TransMapFragment.class);
				
				  intent.putExtra("map",map1);
				  startActivity(intent);
	            }
		    });
		nameText.setEnabled(false);
		addressText.setEnabled(false);
		telText.setEnabled(false);
		introEdit.setEnabled(false);
		if (bean != null) {
			initViewsValue();
		}

	}

	private void initViewsValue() {
		ImageLoader.getInstance().displayImage(
				Constant.URL + bean.getUavatar(), headImg);
		nameText.setText(bean.getUname());
		addressText.setText(bean.getAddress());
		telText.setText(bean.getUphone());
		introEdit.setText(bean.getUsummary());
		Log.d("sunzhen", bean.getUsummary());
	}

	@Override
	public void handleMsg(Message msg) {
	
		switch (msg.what) {
		case TAG_GET_USERINFO:
			String json = msg.getData().getString("json");
			bean = JsonUtil.parseUserSingleJson(this, json);
			initViewsValue();
			break;

		case TAG_PERSON_ISCOLLECT:
			String json1 = msg.getData().getString("json");
			int tag = JsonUtil.parseCodeInt(this, json1);
			Log.d("sunzhen", "收藏返回的code是"+ tag);
			if(tag == 1) {
				hotBtn.setSelected(true);
			} else {
				hotBtn.setSelected(false);

			}
			break;
		case 1:
			String json2 = msg.getData().getString("json");
			if(JsonUtil.parseCode(this, json2)) {
				Toast.makeText(this, "取消收藏成功", 500).show();
			} else {
				Toast.makeText(this, "取消收藏失败",500).show();
				hotBtn.setSelected(true);
			}
			break;
		case 2:
			String json3 = msg.getData().getString("json");
			if(JsonUtil.parseCode(this, json3)) {
				Toast.makeText(this, "收藏成功", 500).show();
			} else {
				Toast.makeText(this, "收藏失败",500).show();
				hotBtn.setSelected(false);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_back:
			finish();
			break;
		case R.id.title_right:// 收藏或者不收藏

			// TODO 通知后台
			if(hotBtn.isSelected()) { // 取消收藏
				hotBtn.setSelected(false);
				Map<String, String> map = new HashMap<String, String>();
				map.put("uid", SharedPreferencesUtil.getUid(this)+"");
				map.put("wscsuid", bean.getUid()+"");
				getData("POST", 1, Constant.NOT_COLLECT_PERSON_URL, map);

			} else {  // 收藏
				hotBtn.setSelected(true);
				Map<String, String> map = new HashMap<String, String>();
				map.put("uid", SharedPreferencesUtil.getUid(this)+"");
				map.put("wscsuid", bean.getUid()+"");
				getData("POST", 2, Constant.COLLECT_PERSON_URL, map);

			}
			break;

		default:
			break;
		}
	}

}
