package com.example.biglove.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.biglove.BigLoveApplacation;
import com.example.biglove2.R;
import com.example.biglove.activity.CollectedHdDetailsActivity;
import com.example.biglove.activity.HDDetailsActivity;
import com.example.biglove.activity.TransMapFragment;
import com.example.biglove.bean.UserBean;
import com.example.biglove.constant.Constant;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.UpLoadUtil2;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人资料fragment
 * 
 * @author sun_zhen
 * 
 */
public class MyInfoFragment extends BaseFragment implements OnClickListener {
	private View view;
	private ImageView headImg;
	private ImageView markerImg;
	private Button EditBtn;
	private EditText nameText, addressText, telText;
	private EditText introEdit;
	private UserBean bean;
	private ProgressDialog dialog;
	private Button person_edit_btn;
	public static final int NONE = 0;
	private static final int PHOTOHRAPH = 1;
	private static final int PHOTOZOOM = 2;
	private static final int PHOTORESOULT = 3;
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String imgUrl, imageName;
	private File picture;
	private Uri uri;
	private Bitmap photo;
	private File uploadfile;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.person_one_fg, null);
			initViews();
		} else {
			ViewGroup group = (ViewGroup) view.getParent();
			if (group != null) {
				group.removeView(view);
			}
		}
		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		headImg = (ImageView) view.findViewById(R.id.person_head_img);
		EditBtn = (Button) view.findViewById(R.id.person_edit_btn);
		nameText = (EditText) view.findViewById(R.id.person_name_text);
		addressText = (EditText) view.findViewById(R.id.person_address_text);
		markerImg = (ImageView) view.findViewById(R.id.marker_img);
		
		telText = (EditText) view.findViewById(R.id.person_tel_text);
		introEdit = (EditText) view.findViewById(R.id.intro_edit);

		nameText.setEnabled(false);
		addressText.setEnabled(false);
		telText.setEnabled(false);
		introEdit.setEnabled(false);

		bean = BigLoveApplacation.getPersonInfoBean();
		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("数据提交中...");
		dialog.setCanceledOnTouchOutside(false);

		ImageLoader.getInstance().displayImage(
				Constant.URL + bean.getUavatar(), headImg);
		nameText.setText(bean.getUname());
		addressText.setText(bean.getAddress());
		telText.setText(bean.getUphone());
		introEdit.setText(bean.getUsummary());
		person_edit_btn = (Button) view.findViewById(R.id.person_edit_btn);
		person_edit_btn.setOnClickListener(this);
		headImg.setOnClickListener(this);
		markerImg.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            if ("編輯".equals(EditBtn.getText().toString())){
	        	  HashMap<String, String> map1 = new HashMap<String, String>();
			
				  map1.put("address", bean.getAddress());
				  map1.put("lat",String.valueOf(bean.getUlat()));
				  map1.put("lon", String.valueOf(bean.getUlon()));
				  Intent intent = new Intent( getActivity(),TransMapFragment.class);
				
				  intent.putExtra("map",map1);
				  startActivity(intent);
	            }
//
				
	        }

	    });
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.person_edit_btn:// 编辑按钮
			if ("編輯".equals(EditBtn.getText().toString())) {
				EditBtn.setText("完成");
				Log.d("tag", "11111111");
				nameText.setEnabled(true);
				addressText.setEnabled(true);
				telText.setEnabled(true);
				introEdit.setEnabled(true);
				markerImg.setEnabled(true);
			} else {
				Log.d("tag", "222222");
				
				String name = nameText.getText().toString().trim();
				String address = addressText.getText().toString();
				String tel = telText.getText().toString().trim();
				String note = introEdit.getText().toString().trim();
				if(validate(name,address,tel,note)){
					EditBtn.setText("編輯");
					nameText.setEnabled(false);
					addressText.setEnabled(false);
					telText.setEnabled(false);
					introEdit.setEnabled(false);
					markerImg.setEnabled(false);
				 senddata(name,address,tel,note);
				}
			}

			break;
	
		case R.id.person_head_img:
			Intent intent1 = new Intent(Intent.ACTION_PICK, null);
			intent1.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					IMAGE_UNSPECIFIED);
			startActivityForResult(intent1, PHOTOZOOM);
			System.out.print("eeeeeee");
			break;
			
		default:
			break;
		}
	}
     
	public boolean validate(String name,String address,String tel,String note){
		if(!name.equals("")){
			  if(!address.equals("")){	
				if(!tel.equals("")){
				  if(!note.equals("")){
					 
			            return true;
					  
						  
				  }else{
					  Toast.makeText(getActivity(), "請輸入個人簡介",3).show();
					  return false;
				  }
				  
				}else{
					Toast.makeText(getActivity(), "請輸入連絡電話",3).show();
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
	 
	
	 public void senddata(String name,String address,String tel,String note){
			
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
			map.put("uid", bean.getUid() + "");
			map.put("uname", name);
			map.put("usummary", note);
			map.put("address", address);
			map.put("ulat",String.valueOf(lng));
			map.put("ulon", String.valueOf(lat));
			map.put("uphone",tel);
			getData("POST", Constant.PERSON_INFO_TAG, Constant.URL+ Constant.PERSON_INFO_URL, map);
			bean.setUlat(lng);
			bean.setUlon(lat);
			bean.setAddress(address);
			Log.d("tag", "PERSON_INFO_URL----->" + Constant.URL
					+ Constant.PERSON_INFO_URL + "?uid=" + bean.getUid()
					+ "&uname=" + name + "&usummary=" + note + "&ulon="
					+ "" + "&ulat=" + "");
			dialog.show();
					
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
					headImg.setImageBitmap(photo);
					uploadfile =saveImage(photo, getActivity());
					new Thread(uploadRun).start(); 
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
	 
	 
	 
		Runnable uploadRun = new Runnable(){  
			  
			 @Override  
			 public void run() {  
				 //UpLoadUtil.uploadFile(hdid, getActivity(), Constant.UPLOAD_HDIMG_URL ,String.valueOf(hdid)+".png",uploadfile); 
			 
				 String abc = UpLoadUtil2.uploadFile(bean.getUid(),Constant.UPLOAD_HDIMG_URL,uploadfile,0);
				 //Looper.prepare();
				 //Toast.makeText(getActivity(), abc,5).show();
				 //Looper.loop();
			 }  
			   };
	 
	 
	 
	 
	 
	 
	
	
	@Override
	public void handleMsg(Message msg) {
		dialog.dismiss();
		String json = msg.getData().getString("json");
		switch (msg.what) {
		case Constant.PERSON_INFO_TAG:
			int code = JsonUtil.parseLoginJson(getActivity(), json);
			if (code == -1) {
				Toast.makeText(getActivity(), "信息修改失败！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(), "信息修改成功！", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		default:
			break;
		}

	}

}
