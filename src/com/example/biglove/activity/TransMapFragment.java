package com.example.biglove.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;




public class TransMapFragment extends FragmentActivity {
	

	private View view;
	GoogleMap map;
	private LocationManager locationMgr;
	String provider;
	//lat經度  lon 緯度

	
	
	HashMap<String, String> map1 = new HashMap<String, String>();
	private Location location2;
	
	private double mlat;
	private double mlon;
	private String maddress;
	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		if (view == null) {
//			setContentView(R.layout.activity_map);
////			view = inflater.inflate(R.layout.activity_transmap, null);
//			initviews();
//			initMap();
//			
//		 
//		}
//		ViewGroup group = (ViewGroup) view.getParent();
//		if (group != null) {
//			group.removeView(view);
//		}
//		return view;
//	}
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
//			Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.activity_transmap);
			initviews();
			initMap();
//		
		
	}
	
	
	private void initviews() {
		
		Intent intent = getIntent();
//		Bundle bundle=getArguments();
//		map1 =(HashMap)bundle.getSerializable("map");
		HashMap<String, String>map1 =(HashMap<String, String>)intent.getSerializableExtra("map");
		
		maddress = map1.get("address");
		mlat = Double.parseDouble(map1.get("lat"));
		mlon = Double.parseDouble(map1.get("lon"));
	}
	
	
	
	private void initLocationProvider() {
		   
		locationMgr = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		
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
		SupportMapFragment mapFrag =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFrag.getMap();
		   
//		SupportMapFragment map =
//				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		   if (map != null){
		    map.setMyLocationEnabled(true);
		   
		    initLocationProvider();
			Location location = locationMgr.getLastKnownLocation(provider);
			location2 =location;
			cameraFocusOnMe(mlat, mlon);
			setmarker();
		    
		   }
	}
	
	private void cameraFocusOnMe(double lat, double lng){
		CameraPosition camPosition = new CameraPosition.Builder()
										.target(new LatLng(lng,lat))
										.zoom(16)
										.build();
		
		map.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
	}
	
	private void setmarker(){
			
           map.addMarker(new MarkerOptions().position(new LatLng(mlon,mlat)).title(maddress).snippet("address"));
			
	}
	
	
		
	
	
			
			
			
			
	
		
		
	
	public void handleMsg(Message msg) {


	}
	
	
	
	
}
