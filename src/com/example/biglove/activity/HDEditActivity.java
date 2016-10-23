package com.example.biglove.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.biglove.BigLoveApplacation;
import com.example.biglove2.R;
import com.example.biglove.bean.HdBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.fragment.DatePickerFragment;
import com.example.biglove.fragment.TimePickerFragment;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove.util.UpLoadUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
@SuppressLint("NewApi")
public class HDEditActivity extends BaseActivity implements OnClickListener {
	private ImageButton action_album_ib, action_pic_ib;
	public static final int NONE = 0;
	private static final int PHOTOHRAPH = 1;
	private static final int PHOTOZOOM = 2;
	private static final int PHOTORESOULT = 3;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String imageName;
	private File picture;
	private Uri uri;
	private Bitmap photo;
	private ImageView select_action_img;
	private Button action_back;
	private EditText action_name_edit, action_address_edit,
			action_start_time_edit, action_end_time_edit, action_content_edit;

	private HdBean mHdBean ;
	private String time;
	private String date;
	private int choose =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_edit);
		Intent intent = getIntent();
		mHdBean = intent.getParcelableExtra("hdbean");
		initView();
	}

	private void initView() {
		action_album_ib = (ImageButton) findViewById(R.id.action_album_ib);
		action_pic_ib = (ImageButton) findViewById(R.id.action_pic_ib);
		select_action_img = (ImageView) findViewById(R.id.select_action_img);
		action_back=(Button) findViewById(R.id.action_back);
		
		action_name_edit = (EditText) findViewById(R.id.action_name_edit);
		action_address_edit = (EditText) findViewById(R.id.action_address_edit);
		action_start_time_edit = (EditText) findViewById(R.id.action_start_time_edit);
		action_end_time_edit = (EditText) findViewById(R.id.action_end_time_edit);
		action_content_edit = (EditText) findViewById(R.id.action_content_edit);
		ImageLoader.getInstance().displayImage(Constant.URL+mHdBean.getHdImg(), select_action_img);
		action_name_edit.setText(mHdBean.getHdname());
		action_address_edit.setText(mHdBean.getaddress());
		action_start_time_edit.setText(mHdBean.getStartdate());
		action_end_time_edit.setText(mHdBean.getEnddate());
		action_content_edit.setText(mHdBean.getHdcontent());
		action_album_ib.setOnClickListener(this);
		action_pic_ib.setOnClickListener(this);
		action_back.setOnClickListener(this);
		action_start_time_edit.setOnClickListener(this);
		action_end_time_edit.setOnClickListener(this);
		action_start_time_edit.setInputType(InputType.TYPE_NULL);
		action_end_time_edit.setInputType(InputType.TYPE_NULL);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		
		case R.id.action_start_time_edit:
			choose =1;
			showTimePickerFragemnt();
			break;
		case R.id.action_end_time_edit:
			choose =2;
			showTimePickerFragemnt();
			break;
		
		case R.id.action_pic_ib:
			imageName = "/" + getStringToday() + ".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), imageName)));
			startActivityForResult(intent, PHOTOHRAPH);
			break;
		case R.id.action_album_ib:
			Intent intent1 = new Intent(Intent.ACTION_PICK, null);
			intent1.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					IMAGE_UNSPECIFIED);
			startActivityForResult(intent1, PHOTOZOOM);
			break;

		case R.id.action_back:
			String hdName = action_name_edit.getText().toString();
			String startdate = action_start_time_edit.getText().toString();
			String enddate = action_end_time_edit.getText().toString();
			String hdContent = action_content_edit.getText().toString();
			String address = action_address_edit.getText().toString();
			if(validate(hdName,address,startdate,enddate,hdContent)){
				senddata(hdName,startdate,enddate,hdContent,address);
			}
			
//			Geocoder gc = new Geocoder(this);
//			List<Address> list = null;
//			try {
//				list = gc.getFromLocationName(address, 1);
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}
//			Address add = list.get(0);
//			String locality = add.getLocality();
//		
//			
//			double lat = add.getLatitude();
//			double lng = add.getLongitude();
//			
//			
//			
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("hdid", mHdBean.getHdid()+"");
//			map.put("hdname", hdName);
//			map.put("startdate", startdate);
//			map.put("enddate", enddate);
//			map.put("hdcontent", hdContent);
//			map.put("address", address);
//			map.put("hdlat",String.valueOf(lng));
//			map.put("hdlon", String.valueOf(lat));
//			getData("POST", TAG_UPDATE_HD, Constant.UPDATE_HD_INFO, map );
			break;
		default:
			break;
		}
	}

	
	
      public void senddata(String hdName,String startdate,String enddate,String hdContent,String address){
		
		Geocoder gc = new Geocoder(this);
		List<Address> list = null;
		try {
			list = gc.getFromLocationName(address, 1);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Address add = list.get(0);
		String locality = add.getLocality();
	
		
		double lat = add.getLatitude();
		double lng = add.getLongitude();
		
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("hdid", mHdBean.getHdid()+"");
		map.put("hdname", hdName);
		map.put("startdate", startdate);
		map.put("enddate", enddate);
		map.put("hdcontent", hdContent);
		map.put("address", address);
		map.put("hdlat",String.valueOf(lng));
		map.put("hdlon", String.valueOf(lat));
		getData("POST", TAG_UPDATE_HD, Constant.UPDATE_HD_INFO, map );
				
	}

   public boolean validate(String hdName,String address,String startdate,String enddate,String hdContent){
	if(!hdName.equals("")){
		  if(!address.equals("")){	
			if(!startdate.equals("")){
			  if(!enddate.equals("")){
				if(vaildtime(startdate ,enddate)){  
				  if(!hdContent.equals("")){
		            
		            return true;
				  }else{
					  
					  Toast.makeText(this, "請輸入活動內容",3).show();
					  return false;
				  }
				}else{
					Toast.makeText(this, "開始時間不能超過結束時間",3).show();
					return false;
				} 
					  
			  }else{
				  Toast.makeText(this, "請輸入結束時間",3).show();
				  return false;
			  }
			  
			}else{
				Toast.makeText(this, "請輸入開始時間",3).show();
				return false;
			}
		  } 
		   else{
			  Toast.makeText(this, "請輸入地址",3).show();
			  return false;
		   }
		}else{
			Toast.makeText(this, "請輸入活動名稱",3).show();
			return false;
		  }
	}

public boolean vaildtime(String DATE1 ,String DATE2){
	
	  
	
	  
	java.util.Calendar c1=java.util.Calendar.getInstance();
	  
	java.util.Calendar c2=java.util.Calendar.getInstance();
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	try{
	  
	      c1.setTime(df.parse(DATE1));
	      c2.setTime(df.parse(DATE2));
	  
    }catch(java.text.ParseException e){
	  
	     System.err.println("格式不正确");
	  
	}
	  
	try {
	  
	int result=c1.compareTo(c2);
	if (result==0) {
	  
	System.out.println("時間相等了");
	  
	   return true;
	  
	} else if (result<0) {
	  
	    System.out.println("正常");
	  
	    return true;
	  
	}else{
	  
	    System.out.println("未過期");
	  
	    return false;
	  
	}
	  
	} catch (Exception exception) {
	  
	     exception.printStackTrace();
	  
	}
	return false;	
	
}








	/**
	 * 图片剪切
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == NONE)
			return;
		else if (requestCode == PHOTOHRAPH) {

			picture = new File(Environment.getExternalStorageDirectory()
					+ imageName);
			startPhotoZoom(Uri.fromFile(picture));
		} else if (data == null) {
			return;
		} else if (requestCode == PHOTOZOOM) {
			uri = data.getData();
			startPhotoZoom(uri);
		}
		// 剪切结果
		else if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.PNG, 80, stream);
				select_action_img.setImageBitmap(photo);
//				UpLoadUtil.uploadFile(mHdBean.getHdid(), this, Constant.UPLOAD_HDIMG_URL, "imag", data.getData());
			}
		}
	}
	
	private void showDatePickerFragemnt(){
		DatePickerFragment datefragment=new DatePickerFragment();
		datefragment.setCallBack(onDate);
		//Object fragmentManager2 = getFragmentManager();
		datefragment.show(getSupportFragmentManager(), "datePicker");
		//showTimePickerFragemnt();
		// =
		//action_address_edit.setText(time);
	}
	@SuppressLint("NewApi")
	private void showTimePickerFragemnt(){
		TimePickerFragment Timefragment=new TimePickerFragment();
		Timefragment.setCallBack(onTime);
//		Object fragmentManager2 = getFragmentManager();
		Timefragment.show(getSupportFragmentManager(), "timePicker");
		showDatePickerFragemnt();
		// =
		//action_address_edit.setText(time);
	}
	
	DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        	int _year=year;
    		int _month=monthOfYear;
    		int _day=dayOfMonth;
    		
    		date=""+_year+"-"+(_month+1)+"-"+_day+" ";
    		
    		//action_start_time_edit.setText(time);
            
        }
    };
    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
    		int hour  =hourOfDay;
    		int minutes = minute;
    		if(minutes < 10){
    			time=""+hour+":0"+minutes; 
    		}else{
    			
    			time=""+hour+":"+minutes; 
    		}
    		String localtime=date +time;
    		if(choose ==1){
    		 action_start_time_edit.setText(localtime);
    		}else{
    		 action_end_time_edit.setText(localtime);
    	    }
		}
    	
    
    };
    
	
	
	

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case TAG_UPDATE_HD:
			String json = msg.getData().getString("json");
			boolean bo = JsonUtil.parseCode(HDEditActivity.this, json);
			if(bo){
		       BigLoveApplacation application= (BigLoveApplacation) HDEditActivity.this.getApplication();
		       application.tag = true;
				setResult(RESULT_OK);
				finish();
			} else {
				try {
					Toast.makeText(this, new JSONObject(json).getString("msg"), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			Log.d("TAG1", json);
			break;

		default:
			break;
		}
	}
 }
