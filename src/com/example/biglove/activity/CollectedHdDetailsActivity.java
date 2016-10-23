package com.example.biglove.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biglove2.R;
import com.example.biglove.bean.UserHdBean;
//import com.example.biglove.fragment.AndroidFacebookConnectActivity;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class CollectedHdDetailsActivity extends BaseActivity implements
		OnClickListener {
	private ImageButton hotBtn; // 右顶部收藏按钮
	private TextView mUname;
	private ImageView mUAvatar;
	private ImageView markerImg;
	private TextView mHdName;
	private TextView mHdAddress;
	private TextView mHdDate;
	private TextView mHdContent;
	private ImageView mHdImg;
	private Button mshareBtn;
	private Button mBackBtn;
	private UserHdBean mHdBean;
	
	protected static final String TAG = null;

	// Your Facebook APP ID
	private static String APP_ID = "715933855158824"; // Replace with your App ID

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collected_hd_details);
		Intent intent = getIntent();
		mHdBean = intent.getParcelableExtra("uh");
		initViews();
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", SharedPreferencesUtil.getUid(this)+"");
		params.put("hdID", mHdBean.getHdid()+"");
		getData("POST", TAG_HD_ISCOLLECT, Constant.ISCOLLECT_HD_URL, params);
	}

	private void initViews() {
		
		hotBtn = (ImageButton) findViewById(R.id.title_right);
		mBackBtn = (Button) findViewById(R.id.action_back);
		mshareBtn = (Button) findViewById(R.id.hd_share);
		markerImg = (ImageView) findViewById(R.id.marker_img);
		mUname = (TextView) findViewById(R.id.hd_uname_tv);
		mUAvatar = (ImageView) findViewById(R.id.hd_uavatar_iv);
		mHdName = (TextView) findViewById(R.id.hd_name_tv);
		mHdAddress = (TextView) findViewById(R.id.hd_address_tv);
		mHdDate = (TextView) findViewById(R.id.hd_date_tv);
		mHdContent = (TextView) findViewById(R.id.hd_content_tv);
		mHdImg = (ImageView) findViewById(R.id.hd_img);

		mUname.setText(mHdBean.getUname());
		mHdName.setText(mHdBean.getHdname());
		mHdAddress.setText("地址:"+mHdBean.getaddress());
		mHdAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mHdDate.setText("從"+mHdBean.getStartdate() + "到" + mHdBean.getEnddate());
		mHdContent.setText("內容:"+mHdBean.getHdcontent());
		ImageLoader.getInstance().displayImage(
				Constant.URL + mHdBean.getHdImg(), mHdImg);
		ImageLoader.getInstance().displayImage(
				Constant.URL + mHdBean.getUavatar(), mUAvatar);
		
		mBackBtn.setOnClickListener(this);
		hotBtn.setOnClickListener(this);
		mshareBtn.setOnClickListener(this);
		mUAvatar.setOnClickListener(this);
        markerImg.setOnClickListener(new View.OnClickListener() {
	        
	        public void onClick(View v) {
	            
	        	  HashMap<String, String> map1 = new HashMap<String, String>();
			
				  map1.put("address", mHdBean.getaddress());
				  map1.put("lat",String.valueOf(mHdBean.getHdlat()));
				  map1.put("lon", String.valueOf(mHdBean.getHdlon()));
				  Intent intent = new Intent(CollectedHdDetailsActivity.this,TransMapFragment.class);
				
				  intent.putExtra("map",map1);
				  startActivity(intent);
	            }
		    });
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.hd_share:
			loginToFacebook();
			postToWall();
			break;
		
		case R.id.action_back:
			finish();
			break;
		case R.id.title_right:
			// TODO 通知后台
						if(hotBtn.isSelected()) { // 取消收藏
							hotBtn.setSelected(false);
							Map<String, String> map = new HashMap<String, String>();
							map.put("uid", SharedPreferencesUtil.getUid(this)+"");
							map.put("uschdid", mHdBean.getHdid()+"");
							getData("POST", 1, Constant.CANCEL_COLLECT_HD_URL, map);

						} else {  // 收藏
							hotBtn.setSelected(true);
							Map<String, String> map = new HashMap<String, String>();
							map.put("uid", SharedPreferencesUtil.getUid(this)+"");
							map.put("uschdid", mHdBean.getHdid()+"");
							getData("POST", 2, Constant.COLLECT_HD_URL, map);

						}
			break;
		case R.id.hd_uavatar_iv:
			Intent intent = new Intent(this, PersonInfoActivity.class)
			.putExtra("uid", mHdBean.getHdsuid());
	        startActivity(intent);
			
			break;
		default:
			break;
		}
	}
	
	
	
	public void loginToFacebook() {
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			
			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

						
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}


	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					
					// getting name of the user
					final String name = profile.getString("name");
					
					// getting email of the user
					final String email = profile.getString("email");
					
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
						}

					});

					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		Bundle postParams = new Bundle();
	    postParams.putString("name", mHdBean.getHdname());
	    postParams.putString("caption", "地點:"+mHdBean.getaddress());
	    postParams.putString("description",
	                    "活動內容:"+mHdBean.getHdcontent());
	    postParams.putString("link", "http://maps.google.com/maps?q=loc:"+mHdBean.getHdlon()+","+mHdBean.getHdlat());
//	    postParams.putString("picture",
//	                    "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");
		
		
		facebook.dialog(this, "feed",postParams, new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}

	
	/**
	 * Function to show Access Tokens
	 * */
	public void showAccessTokens() {
		String access_token = facebook.getAccessToken();

		Toast.makeText(getApplicationContext(),
				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
	}
	
	

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {

		case TAG_HD_ISCOLLECT:
			String json = msg.getData().getString("json");
			int tag = JsonUtil.parseCodeInt(this, json);
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

}
