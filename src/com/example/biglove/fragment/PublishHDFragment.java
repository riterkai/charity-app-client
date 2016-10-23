package com.example.biglove.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.location.Address;
import android.location.Geocoder;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;

import com.example.biglove2.R;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove.util.UpLoadUtil;
import com.example.biglove.util.UpLoadUtil2;
/**
 * 底部tab之发布活动图标对应的fragment
 * @author sun_zhen
 *
 */
public class PublishHDFragment extends BaseFragment implements OnClickListener{
	private View view;
	private LayoutInflater inflater;
	private ImageButton action_album_ib, action_pic_ib;
	public static final int NONE = 0;
	private static final int PHOTOHRAPH = 1;
	private static final int PHOTOZOOM = 2;
	private static final int PHOTORESOULT = 3;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String imgUrl, imageName;
	private DateFormat dateFormat;
	private File picture;
	private Uri uri;
	private Bitmap photo;
	private ImageView select_action_img;
	private Button action_back;
	private File uploadfile;
	private EditText action_name_edit, action_address_edit,
			action_start_time_edit, action_end_time_edit, action_content_edit;
    private String time;
    private String date;
    private int choose =0;
    String path;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.action_edit, null);
			initView();
		}
		ViewGroup group = (ViewGroup) view.getParent();
		if (group != null) {
			group.removeView(view);
		}
		return view;
	}

	private void initView() {
		action_album_ib = (ImageButton)view.findViewById(R.id.action_album_ib);
		action_pic_ib = (ImageButton) view.findViewById(R.id.action_pic_ib);
		select_action_img = (ImageView) view.findViewById(R.id.select_action_img);
		action_back=(Button) view.findViewById(R.id.action_back);
		action_name_edit = (EditText) view.findViewById(R.id.action_name_edit);
		action_address_edit = (EditText) view.findViewById(R.id.action_address_edit);
		action_start_time_edit = (EditText) view.findViewById(R.id.action_start_time_edit);
		action_end_time_edit = (EditText) view.findViewById(R.id.action_end_time_edit);
		action_content_edit = (EditText) view.findViewById(R.id.action_content_edit);
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
		case R.id.action_album_ib:
			imageName = "/" + getStringToday() + ".jpg";
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), imageName)));
			startActivityForResult(intent, PHOTOHRAPH);
			break;
		case R.id.action_pic_ib:
			//showTimePickerFragemnt();
			
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
			String address =action_address_edit.getText().toString();
			String location = address;
			senddata(hdName,startdate,enddate,hdContent,address);
//			if(validate(hdName,address,startdate,enddate,hdContent)){
//				senddata(hdName,startdate,enddate,hdContent,address);
//			}
			break;
			
		default:
			break;
		}
	}
    
	public void localize(String address){
		
		
		
	}
	
	public void cleanvalue(){
		
		action_name_edit.setText("");
		action_start_time_edit.setText("");
		action_end_time_edit.setText("");
		action_content_edit.setText("");
		action_address_edit.setText("");
		
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
						  
						  Toast.makeText(getActivity(), "請輸入活動內容",3).show();
						  return false;
					  }
					}else{
						Toast.makeText(getActivity(), "開始時間不能超過結束時間",3).show();
						return false;
					} 
						  
				  }else{
					  Toast.makeText(getActivity(), "請輸入結束時間",3).show();
					  return false;
				  }
				  
				}else{
					Toast.makeText(getActivity(), "請輸入開始時間",3).show();
					return false;
				}
			  } 
			   else{
				  Toast.makeText(getActivity(), "請輸入地址",3).show();
				  return false;
			   }
			}else{
				Toast.makeText(getActivity(), "請輸入活動名稱",3).show();
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
	public void senddata(String hdName,String startdate,String enddate,String hdContent,String address){
		
		Geocoder gc = new Geocoder(getActivity());
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
		map.put("hdname", hdName);
		map.put("startdate", startdate);
		map.put("enddate", enddate);
		map.put("hdcontent", hdContent);
		map.put("address", address);
		map.put("hdlat",String.valueOf(lng));
		map.put("hdlon", String.valueOf(lat));
		map.put("creattime",getStringToday());
		map.put("uid", SharedPreferencesUtil.getUid(getActivity())+"");
		getData("POST", TAG_PUBLISH_HD, Constant.PUBLISH_HD_URL, map );
		
		cleanvalue();
		
		
	}
	
	
	/**
	 * 图片剪切
	 * 
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
				
			    //stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.PNG, 80, stream);
				select_action_img.setImageBitmap(photo);
				uploadfile =saveImage(photo, getActivity());
			}
		}
	}
    
	public File saveImage(Bitmap myBitmap, Context context) {

        File myDir=new File( Environment.getExternalStorageDirectory(), context.getPackageName());
        if(!myDir.exists()){  
            myDir.mkdir();  
          }
        String fname = "UploadedImage.png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete (); 
        try {
               FileOutputStream out = new FileOutputStream(file);
               myBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
               out.flush();
               out.close();

        } catch (Exception e) {
               e.printStackTrace();
        }
        return file;
    }
	
	private void showDatePickerFragemnt(){
		DatePickerFragment datefragment=new DatePickerFragment();
		datefragment.setCallBack(onDate);
		//android.support.v4.app.FragmentManager fragmentManager2 = getFragmentManager();
		datefragment.show(getFragmentManager(), "datePicker");
		//showTimePickerFragemnt();
		// =
		//action_address_edit.setText(time);
	}
	private void showTimePickerFragemnt(){
		TimePickerFragment Timefragment=new TimePickerFragment();
		Timefragment.setCallBack(onTime);
		//android.support.v4.app.FragmentManager fragmentManager2 = getFragmentManager();
		Timefragment.show(getFragmentManager(), "timePicker");
		showDatePickerFragemnt();
		// =
		//action_address_edit.setText(time);
	}
	
	
	
	 Runnable uploadRun = new Runnable(){  
		  
		 @Override  
		 public void run() {  
			 //UpLoadUtil.uploadFile(hdid, getActivity(), Constant.UPLOAD_HDIMG_URL ,String.valueOf(hdid)+".png",uploadfile); 
		 
			 String abc = UpLoadUtil2.uploadFile(hdid,Constant.UPLOAD_USERIMG_URL,uploadfile,1);
			 Looper.prepare();
			 Toast.makeText(getActivity(), abc,5).show();
			 Looper.loop();
		 }  
		   };
		   
		   
	private int hdid = 0;  
	
	
	@Override
	public void handleMsg(Message msg) {
		if(msg.what == TAG_PUBLISH_HD ) {
			if(JsonUtil.parseCode(getActivity(), msg.getData().getString("json"))) {
				try {
					 hdid = JsonUtil.gethdid(msg.getData().getString("json"));
					 new Thread(uploadRun).start(); 
					    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Toast.makeText(getActivity(), "活动发布成功", 500).show();
			} else {
				Toast.makeText(getActivity(), "活动发布失败", 500).show();

			}
		}
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
	
}
