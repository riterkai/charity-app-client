package com.example.biglove.fragment;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import android.widget.TimePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;

public class TimePickerFragment extends DialogFragment  {
	

	OnTimeSetListener onTimeSet;
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c=Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
	    int minute = c.get(Calendar.MINUTE);
		return new TimePickerDialog(getActivity(),onTimeSet,hour,minute, 
				DateFormat.is24HourFormat(getActivity()));
//		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	


	    public void setCallBack(OnTimeSetListener onTime) {
	        onTimeSet = onTime;
	    }
}