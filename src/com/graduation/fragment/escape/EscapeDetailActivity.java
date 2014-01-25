package com.graduation.fragment.escape;

import com.graduation.parking.R;
import com.graduation.util.ViewUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EscapeDetailActivity extends Activity
{
	private String car_no;
	private String car_type;
	private String car_state;
	private String parking_name;
	private String parking_stamp;
	private String leave_stamp;
	private String parking_time;
	private Double cost;
	private Double act_cost;
	private String creater;
	private String coster;
	private int f_id;

	private Double act_pay;
	private String pay_stamp;
	private String user_name;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.escape_detail);
		ViewUtil.setUpActionBar(this);

		Intent intent = getIntent();
		f_id = intent.getIntExtra("f_id", 0);
		car_no = intent.getStringExtra("f_car_no");
		car_state = intent.getStringExtra("f_car_state");
		car_type = intent.getStringExtra("f_car_type");
		parking_time = intent.getStringExtra("parking_time");
		parking_name = intent.getStringExtra("parking_name");
		parking_stamp = intent.getStringExtra("f_parking_stamp");
		leave_stamp = intent.getStringExtra("f_leave_stamp");
		cost = intent.getDoubleExtra("f_cost", 0);
		act_cost = intent.getDoubleExtra("f_act_cost", 0);
		creater = intent.getStringExtra("f_creater_name");
		coster = intent.getStringExtra("f_coster_name");

		act_pay = intent.getDoubleExtra("act_pay", 0);
		pay_stamp = intent.getStringExtra("pay_stamp");
		user_name = intent.getStringExtra("user_name");

		((TextView) findViewById(R.id.car_no)).setText(car_no);
		((TextView) findViewById(R.id.car_type)).setText(car_type);
		((TextView) findViewById(R.id.car_state)).setText(car_state);
		((TextView) findViewById(R.id.parking_name)).setText(parking_name);
		((TextView) findViewById(R.id.parking_stamp)).setText(parking_stamp);
		((TextView) findViewById(R.id.leave_stamp)).setText(leave_stamp);
		((TextView) findViewById(R.id.parking_time)).setText(parking_time);
		((TextView) findViewById(R.id.cost)).setText(String.valueOf(cost) + "元");
		((TextView) findViewById(R.id.act_cost)).setText(String.valueOf(act_cost) + "元");
		((TextView) findViewById(R.id.creater)).setText(creater);
		((TextView) findViewById(R.id.coster)).setText(coster);

		((TextView) findViewById(R.id.act_pay)).setText(String.valueOf(act_pay) + "元");
		((TextView) findViewById(R.id.pay_stamp)).setText(pay_stamp);
		((TextView) findViewById(R.id.user_name)).setText(user_name);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onNavigateUp()
	{
		// TODO Auto-generated method stub
		finish();
		return super.onNavigateUp();
	}

}
