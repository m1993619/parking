package com.graduation.util;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.graduation.fragment.parking.ParkingCheckInActivity;
import com.graduation.fragment.parking.ParkingCheckInEditActivity;
import com.graduation.parking.R;

public class ViewUtil
{
	private static final String[] PROVINCE = new String[] { "川", "赣", "桂", "甘", "贵", "沪", "黑", "京", "津", "晋",
			"辽", "鲁", "蒙", "闽", "宁", "青", "鄂", "琼", "苏", "皖", "湘", "新", "陕", "渝", "冀", "豫", "云", "粤",
			"浙", "藏" };
	private static final String[] CITY = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private static final Double[] ACT_COST = new Double[] { (double) 0, (double) 5, (double) 10, (double) 20,
			(double) 50, (double) 100 };
	private static final String[] CAR_TYPE = new String[] { "小型轿车", "小型货车", "大型货车", "执勤车辆", "特殊车辆", "其他" };
	private static final String[] CAR_STATE = new String[] { "车况良好", "轻微擦伤", "严重损坏", "其他" };

	public static Spinner getProvinceSp(final ParkingCheckInActivity context)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.province);

		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				PROVINCE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.province = PROVINCE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getProvinceSp(final ParkingCheckInEditActivity context, String province)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.province);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				PROVINCE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);

		int position = Arrays.asList(PROVINCE).indexOf(province);
		sp.setSelection(position);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.province = PROVINCE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getCitySp(final ParkingCheckInActivity context)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.city);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				CITY);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.city = CITY[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getCitySp(final ParkingCheckInEditActivity parkingCheckInEditActivity, String city)
	{
		Spinner sp = (Spinner) parkingCheckInEditActivity.findViewById(R.id.city);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(parkingCheckInEditActivity,
				android.R.layout.simple_spinner_item, CITY);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);

		int position = Arrays.asList(CITY).indexOf(city);
		sp.setSelection(position);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				parkingCheckInEditActivity.city = CITY[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getCarTypeSp(final ParkingCheckInActivity context)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.car_type);

		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				CAR_TYPE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.car_type = CAR_TYPE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getCarTypeSp(final ParkingCheckInEditActivity context, String car_type)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.car_type);

		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				CAR_TYPE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);
		int position = Arrays.asList(CAR_TYPE).indexOf(car_type);
		sp.setSelection(position);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.car_type = CAR_TYPE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

		return sp;
	}

	public static Spinner getCarStateSp(final ParkingCheckInActivity context)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.car_state);

		ArrayAdapter<String> ada = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,
				CAR_STATE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.car_state = CAR_STATE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

		return sp;
	}

	public static Spinner getCarStateSp(final ParkingCheckInEditActivity parkingCheckInEditActivity,
			String car_state)
	{
		Spinner sp = (Spinner) parkingCheckInEditActivity.findViewById(R.id.car_state);

		ArrayAdapter<String> ada = new ArrayAdapter<String>(parkingCheckInEditActivity,
				android.R.layout.simple_spinner_item, CAR_STATE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);

		int position = Arrays.asList(CAR_STATE).indexOf(car_state);
		sp.setSelection(position);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				parkingCheckInEditActivity.car_state = CAR_STATE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		return sp;
	}

	public static Spinner getActCostSp(final ParkingCheckInActivity context)
	{
		Spinner sp = (Spinner) context.findViewById(R.id.act_cost);

		ArrayAdapter<Double> ada = new ArrayAdapter<Double>(context, android.R.layout.simple_spinner_item,
				ACT_COST);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				context.act_cost = ACT_COST[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

		return sp;
	}

	public static Spinner getActCostSp(final ParkingCheckInEditActivity parkingCheckInEditActivity,
			Double car_type)
	{
		Spinner sp = (Spinner) parkingCheckInEditActivity.findViewById(R.id.act_cost);

		ArrayAdapter<Double> ada = new ArrayAdapter<Double>(parkingCheckInEditActivity,
				android.R.layout.simple_spinner_item, ACT_COST);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(ada);

		int position = Arrays.asList(ACT_COST).indexOf(car_type);
		sp.setSelection(position);

		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				parkingCheckInEditActivity.act_cost = ACT_COST[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

		return sp;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public static void setUpActionBar(Activity activity)
	{
		System.out.println("SDFGSDFGSDFGSDFGSDFGSDF");
		if (Build.VERSION.SDK_INT >= 11)
		{

			ActionBar actionBar = activity.getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		System.out.println("sadfasdfasdfasdfasdfasdf");

	}

}
