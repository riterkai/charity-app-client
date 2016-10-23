package com.example.biglove.fragment;

import java.util.Calendar;

import android.app.Dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;

public class DatePickerFragment extends DialogFragment  {
	
	int _year=1970;
	int _month=0;
	int _day=0;
	EditText txtTime;
	DatePickerDialog.OnDateSetListener onDateSet;
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
		int day=c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(),onDateSet,  year, month, day);
//		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	
//	public void onDateSet(DatePicker view, int year, int monthOfYear,
//			int dayOfMonth) {
//		_year=year;
//		_month=monthOfYear+1;
//		_day=dayOfMonth;
//		
//	}


	 

	    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
	        onDateSet = onDate;
	    }
}