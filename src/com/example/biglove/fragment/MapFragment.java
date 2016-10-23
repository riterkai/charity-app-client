package com.example.biglove.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.biglove.activity.CollectedHdDetailsActivity;
import com.example.biglove.activity.HDDetailsActivity;
import com.example.biglove.activity.TransMapFragment;
import com.example.biglove.constant.Constant;
import com.example.biglove.bean.HdBean;
import com.example.biglove.bean.UserHdBean;
import com.example.biglove.util.JsonUtil;
import com.example.biglove.util.SharedPreferencesUtil;
import com.example.biglove2.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * 底部tab之地图图标对应的fragment
 * @author sun_zhen
 *
 */



public class MapFragment extends BaseFragment {
	
	
	private View view;
	GoogleMap map;
	private LocationManager locationMgr;
	String provider;
	int distance = 5000;
	Marker marker;
	private ImageButton mSetBtn;
	private List<UserHdBean> list = new ArrayList<UserHdBean>();//lat經度  lon 緯度

	
	private Map<Marker, UserHdBean> allMarkersMap = new HashMap<Marker, UserHdBean>();
	private Location location2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_map, null);
			initviews();
			initMap();
			
		 
		}
		ViewGroup group = (ViewGroup) view.getParent();
		if (group != null) {
			group.removeView(view);
		}
		return view;
	}
	
	private void initviews() {
		mSetBtn = (ImageButton) view.findViewById(R.id.distance);
		mSetBtn.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	
	        	   showDialogButtonClick();
	            }	
	        }

	    );
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
		getData("POST",Constant.ALLHD_TAG, Constant.ALLHD_URL, map);
		
		
	}
	
	
	
	private void initLocationProvider() {
		   
		locationMgr = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		
		//1.選擇最佳提供器
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
	    provider = locationMgr.getBestProvider(criteria, true);
		
		//if (provider != null) {
			//return true;
		//}
		
		
		
		//2.選擇使用GPS提供器
		if (locationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
			//return true;
		}
		
		
		
		//3.選擇使用網路提供器
		if (locationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
			//return true;
		}
		
		
	}
	
	
	

  
	private void initMap(){
		   map = ((SupportMapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
		   if (map != null){
		    map.setMyLocationEnabled(true);
		    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

		        @Override
		        public void onInfoWindowClick(Marker marker) {

		            Log.d("", marker.getTitle());
		            UserHdBean userHdBean = allMarkersMap.get(marker);
		            Intent intent;
		           //校驗uid換頁
		            if(userHdBean.getHdsuid() ==  SharedPreferencesUtil.getUid(getActivity())){
					 intent = new Intent( getActivity(),HDDetailsActivity.class);
					 HdBean hdbean =new HdBean(userHdBean.getHdid(),userHdBean.getHdname()
							 ,userHdBean.getHdlon(),userHdBean.getHdlat(),userHdBean.getStartdate()
							 ,userHdBean.getEnddate(),userHdBean.getHdImg(),userHdBean.getHdcontent()
							 ,userHdBean.getHdsuid(),userHdBean.getaddress());
					 	
					 Bundle bundle = new Bundle();
						bundle.putParcelable("hdbean", hdbean);
						intent.putExtras(bundle);
						startActivity(intent);
		            }else{
					 intent = new Intent( getActivity(),CollectedHdDetailsActivity.class);
					 Bundle bundle = new Bundle();
						bundle.putParcelable("uh", userHdBean);
						intent.putExtras(bundle);
						startActivity(intent);
		            }
		           
		            
		        }
		    });
		    initLocationProvider();
			Location location = locationMgr.getLastKnownLocation(provider);
			location2 =location;
		    updateWithNewLocation(location);
		   
		    

		   }
	}
	
	private void cameraFocusOnMe(double lat, double lng){
		CameraPosition camPosition = new CameraPosition.Builder()
										.target(new LatLng(lat, lng))
										.zoom(16)
										.build();
		
		map.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
	}
	
	private void setmarker(Location location){

		for (int i = 0; i < list.size(); i++) {

			  double lati = list.get(i).getHdlat();
			  double longLat = list.get(i).getHdlon();
			  double clon = location.getLongitude();

			  double clat = location.getLatitude();
			  String name =list.get(i).getHdname();
			  //double distance = getDistance(120.2801827,22.730398, lati, longLat);
			  double locdist = getDistance(clon,clat, lati, longLat);
			  Timeexpire(list.get(i).getEnddate());
			 boolean timeex =Timeexpire(list.get(i).getEnddate());
			  if(timeex){
			   if(locdist<distance){
			    UserHdBean vaildbean =list.get(i);
				marker=map.addMarker(new MarkerOptions().position(new LatLng(longLat,lati)).title(name).snippet("距"+(int)locdist+"m"));
				//markers.add(marker);
				allMarkersMap.put(marker, vaildbean);
			      }
			    }
		      }
		
	}
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {  
	    float[] results=new float[1];  
	    Location.distanceBetween(lat1, lon1, lat2, lon2, results);  
	    return results[0];  
	}  
	
	
	
	public boolean Timeexpire(String DATE1){
		
		  
		
		  
		java.util.Calendar c1=java.util.Calendar.getInstance();
		  
		java.util.Calendar c2=java.util.Calendar.getInstance();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try{
		  
		      c1.setTime(df.parse(DATE1));
		  
	    }catch(java.text.ParseException e){
		  
		     System.err.println("格式不正确");
		  
		}
		  
		try {
		  
//		Date dt1 = (Date) df.parse(DATE1);
		//Date curTime = (Date) df.parse(formattedDate);
		//java.util.Date curTime=(java.util.Date)df.parse(formattedDate);
		
//		Date curTime = (Date)df.parse(formattedDate); 
		//Date dt2 = (Date) df.parse();
		//Date curTime = (Date) c.getTime();
		
		//System.out.println("活動"+dt1.getTime());
		int result=c1.compareTo(c2);
		if (result==0) {
		  
		System.out.println("時間相等了");
		  
		   return true;
		  
		} else if (result<0) {
		  
		    System.out.println("過期");
		  
		    return false;
		  
		}else{
		  
		    System.out.println("未過期");
		  
		    return true;
		  
		}
		  
		} catch (Exception exception) {
		  
		     exception.printStackTrace();
		  
		}
		return false;	
		
	}
	
	
	
	
	private void updateWithNewLocation(Location location) {
		String where = "";
		if (location != null) {
			//經度
			double lng = location.getLongitude();
			//緯度
			double lat = location.getLatitude();
			//速度
			float speed = location.getSpeed();
			//時間
			long time = location.getTime();
			
			
			where = "經度: " + lng + 
					"\n緯度: " + lat;
			
			
			cameraFocusOnMe(lat, lng);
			
		}else{
			where = "No location found.";
		}
		
		
	}
	
	
	private int selected = 0; // change selected to global 
	private void showDialogButtonClick() {
	  
	    AlertDialog.Builder builder = 
	        new AlertDialog.Builder(getActivity());
	    builder.setTitle("請選擇活動範圍");
	     
	    final CharSequence[] choiceList = 
	    {"5公里", "10公里" , "20公里" , };
	 
	    builder.setSingleChoiceItems(
	            choiceList, 
	            selected, 
	            new DialogInterface.OnClickListener() {
	         
	        @Override
	        public void onClick(
	                DialogInterface dialog, 
	                int which) {
	            selected = which;
	            
	            switch (which) {
	            case 0:
	             distance =5000;
	             break;
	            case 1:
	             distance =10000;
	             break;
	            case 2:
	             distance =20000;
	             break;
	            default:
	             break;
	            }
	            
	            
	            
	        }
	    })
	    .setCancelable(false)
	    .setNegativeButton("取消", 
	        new DialogInterface.OnClickListener() 
	        {
	            @Override
	            public void onClick(DialogInterface dialog, 
	                    int which) {
	                
	                     dialog.cancel();  
	                
	                
	             }  
	                
	                
	            })
	    .setPositiveButton("確定", 
	        new DialogInterface.OnClickListener() 
	        {
	            @Override
	            public void onClick(DialogInterface dialog, 
	                    int which) {
	                
	             
	                Toast.makeText(
	                		getActivity(), 
	                        "Select "+distance, 
	                        Toast.LENGTH_SHORT
	                        )
	                        .show();
	                //marker.remove(); 
	                map.clear();
	                HashMap<String, String> map = new HashMap<String, String>();
	        		map.put("uid", SharedPreferencesUtil.getUid(getActivity()) + "");
	        		getData("POST",Constant.ALLHD_TAG, Constant.ALLHD_URL, map);
	                
	                
	                
	                
	                
	                
	            }
	        });
	     
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void handleMsg(Message msg) {
//		String json = msg.getData().getString("json");
//		list = JsonUtil.parseHDJson(json);
//		System.out.print("安安"); 
		switch (msg.what) {
		case Constant.ALLHD_TAG:
			String json = msg.getData().getString("json");
			list = JsonUtil.parseUserHDJson(json);
		    setmarker(location2);
			break;
		default:
			break;
		}
	}
	
	
	
	
}
