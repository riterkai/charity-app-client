package com.example.biglove.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.biglove.BigLoveApplacation;
import com.example.biglove2.R;
import com.example.biglove.activity.LoginActivity;
import com.example.biglove.activity.SettingActivity;
import com.example.biglove.adapter.PersonFragAdp;
/**
 * 底部tab之个人图标对应的fragment
 * @author sun_zhen
 *
 */
public class PersonalFragment extends BaseFragment implements OnClickListener,
		OnPageChangeListener {
	private View view;
	private LayoutInflater inflater;
	private ViewPager viewpager;
	private PersonFragAdp adapter;
	private List<Fragment> list;
	private RelativeLayout relTitleOne, relTitleTwo, relTitleThree,
			relTitleFour, relTitleFive;
	private TextView email;
	private ImageButton mSetBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.personal, null);
			initView();
		}
		ViewGroup group = (ViewGroup) view.getParent();
		if (group != null) {
			group.removeView(view);
		}
		return view;
	}

	private void initView() {
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		relTitleThree = (RelativeLayout) view.findViewById(R.id.relTitleThree);
		relTitleOne = (RelativeLayout) view.findViewById(R.id.relTitleOne);
		relTitleTwo = (RelativeLayout) view.findViewById(R.id.relTitleTwo);
		relTitleFour = (RelativeLayout) view.findViewById(R.id.relTitleFour);
		relTitleFive = (RelativeLayout) view.findViewById(R.id.relTitleFive);
		mSetBtn = (ImageButton) view.findViewById(R.id.title_right);
		
		list = new ArrayList<Fragment>();
		list.add(new MyInfoFragment());
		list.add(new MyHDFragment());
		list.add(new WhoCollectMeFragment());
		list.add(new UserCollectWhoFragment());
		list.add(new MyCollectHDFragment2());
		adapter = new PersonFragAdp(list, getChildFragmentManager());
		viewpager.setAdapter(adapter);
		mSetBtn.setOnClickListener(this);
		relTitleOne.setOnClickListener(this);
		relTitleTwo.setOnClickListener(this);
		relTitleThree.setOnClickListener(this);
		relTitleFour.setOnClickListener(this);
		relTitleFive.setOnClickListener(this);
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one);
		viewpager.setOnPageChangeListener(this);
		email=(TextView) view.findViewById(R.id.title_left);
		email.setText(BigLoveApplacation.getPersonInfoBean().getUemail());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.relTitleOne:
			oneBackGround();
			viewpager.setCurrentItem(0);
			break;
		case R.id.relTitleTwo:
			twoBackGround();
			viewpager.setCurrentItem(1);
			break;
		case R.id.relTitleThree:
			threeBackGround();
			viewpager.setCurrentItem(2);
			break;
		case R.id.relTitleFour:
			fourBackGround();
			viewpager.setCurrentItem(3);
			break;
		case R.id.relTitleFive:
			fiveBackGround();
			viewpager.setCurrentItem(4);
			break;

		case R.id.title_right:
			showDialog(getActivity()); 
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			oneBackGround();
			viewpager.setCurrentItem(0);
			break;
		case 1:
			twoBackGround();
			viewpager.setCurrentItem(1);
			break;
		case 2:
			threeBackGround();
			viewpager.setCurrentItem(2);
			break;
		case 3:
			fourBackGround();
			viewpager.setCurrentItem(3);
			break;
		case 4:
			fiveBackGround();
			viewpager.setCurrentItem(4);
			break;

		default:
			break;
		}
	}

	public void oneBackGround() {
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one);
		relTitleTwo.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleThree.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFour.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFive.setBackgroundResource(R.drawable.bg_message_three_us);

	}

	public void twoBackGround() {
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one_us);
		relTitleTwo.setBackgroundResource(R.drawable.bg_message_two);
		relTitleThree.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFour.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFive.setBackgroundResource(R.drawable.bg_message_three_us);

	}

	public void threeBackGround() {
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one_us);
		relTitleTwo.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleThree.setBackgroundResource(R.drawable.bg_message_two);
		relTitleFour.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFive.setBackgroundResource(R.drawable.bg_message_three_us);

	}

	public void fourBackGround() {
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one_us);
		relTitleTwo.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleThree.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFour.setBackgroundResource(R.drawable.bg_message_two);
		relTitleFive.setBackgroundResource(R.drawable.bg_message_three_us);

	}

	public void fiveBackGround() {
		relTitleOne.setBackgroundResource(R.drawable.bg_message_one_us);
		relTitleTwo.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleThree.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFour.setBackgroundResource(R.drawable.bg_message_two_un);
		relTitleFive.setBackgroundResource(R.drawable.bg_message_three);

	}

	
	private void showDialog(Context context) {  
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  
        
        builder.setTitle("登出提示");  
        builder.setMessage("是否登出用戶");  
        builder.setPositiveButton("是",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	Intent intent = new Intent(getActivity(), LoginActivity.class);
                    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
                    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    	startActivity(intent);
                    }  
                });  
       
        builder.setNegativeButton("否",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	dialog.cancel();  
                    }  
                });  
        builder.show();  
    }  
	
	
	@Override
	public void handleMsg(Message msg) {
		
	}

}
