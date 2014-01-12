package com.graduation.fragment.parking;

import java.sql.Timestamp;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.parking.MainActivity;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.graduation.util.TimeUtil;

public class ParkingCheckOutActivity extends Activity
{
	private String parking_code;
	private String car_no;
	private String parking_stamp;
	private String time;
	private String street_name;
	private String pre_pay;
	private String cost;
	private String act_cost;
	private String cost_type;
	private int f_id;
	private String reason;
	private String f_key;

	private TextView parking_code_text;
	private TextView car_no_text;
	private TextView parking_stamp_text;
	private TextView time_text;
	private TextView street_name_text;
	private TextView pre_pay_text;
	private TextView cost_text;

	private Spinner cost_type_sp;

	private EditText act_cost_text;
	private EditText reason_text;

	private Button pay;

	private View reason_view;

	private SharedPreferences sp;

	private final String[] COST_TYPE = new String[] { "正常缴费", "免费时段", "免费车辆", "车辆逃逸", "其他情况" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		parking_code = intent.getStringExtra("parking_code");
		car_no = intent.getStringExtra("f_car_no");
		parking_stamp = intent.getStringExtra("f_parking_stamp").substring(5);
		street_name = intent.getStringExtra("f_street_name");
		pre_pay = intent.getStringExtra("pre_pay").substring(5);
		f_id = intent.getIntExtra("f_id", 0);
		f_key = intent.getStringExtra("f_key");

		parking_code_text = (TextView) findViewById(R.id.parking_code);
		car_no_text = (TextView) findViewById(R.id.car_no);
		parking_stamp_text = (TextView) findViewById(R.id.parking_stamp);
		time_text = (TextView) findViewById(R.id.time);
		street_name_text = (TextView) findViewById(R.id.street_name);
		pre_pay_text = (TextView) findViewById(R.id.pre_pay);
		cost_text = (TextView) findViewById(R.id.cost);

		act_cost_text = (EditText) findViewById(R.id.act_cost);
		reason_text = (EditText) findViewById(R.id.reason);

		cost_type_sp = (Spinner) findViewById(R.id.cost_type);
		ArrayAdapter<String> cost_type_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, COST_TYPE);
		cost_type_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cost_type_sp.setAdapter(cost_type_ada);
		cost_type_sp.setOnItemSelectedListener(new CostTypeListener());

		reason_view = (View) findViewById(R.id.reason_view);

		pay = (Button) findViewById(R.id.pay);
		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				update();
			}
		});

		time = TimeUtil.getParkingFee(parking_stamp).get("time");
		cost = TimeUtil.getParkingFee(parking_stamp).get("fee");
		System.out.println("time:" + time + "fee:" + cost);

		parking_code_text.setText(parking_code);
		car_no_text.setText(car_no);
		parking_stamp_text.setText(parking_stamp);
		time_text.setText(time);
		street_name_text.setText(street_name);
		pre_pay_text.setText(pre_pay);
		cost_text.setText(cost + "元");

		sp = getSharedPreferences("user", MODE_PRIVATE);

	};

	private void update()
	{
		reason = reason_text.getText().toString();
		act_cost = act_cost_text.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(act_cost_text.getText().toString()))
		{
			act_cost_text.setError("实收金额不能为空！");
			focusView = act_cost_text;
			cancel = true;
		}

		if (cancel)
		{
			focusView.requestFocus();
		}
		else
		{

			System.out.println(reason);
			new CheckOutTask().execute();
		}

	}

	private class CheckOutTask extends AsyncTask<Void, Void, Boolean>
	{

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			ParkingCheckOutActivity.this.showDialog(DialogUtil.PROGRESS_DIALOG);

		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			Timestamp leave_stamp = TimeUtil.getTime();

			ArrayList<Object> list = new ArrayList<Object>();
			list.add(leave_stamp);
			list.add(Double.parseDouble(cost));
			list.add(Double.parseDouble(act_cost));
			list.add(cost_type);
			list.add(reason);
			list.add(sp.getInt("f_id", 0));
			list.add(f_key);
			list.add(f_id);

			int i = DbUtil.checkout(list);
			return (1 == i ? true : false);
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Boolean result)
		{

			if (result)
			{

				Intent intent = new Intent(ParkingCheckOutActivity.this, MainActivity.class);
				intent.putExtra("state", "check");
				startActivity(intent);
				Toast.makeText(ParkingCheckOutActivity.this, "update success!",
						Toast.LENGTH_LONG).show();
				finish();
			}
			else
			{

				ParkingCheckOutActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);
				Toast.makeText(ParkingCheckOutActivity.this, "update failed!",
						Toast.LENGTH_LONG).show();
			}

		}
	}

	private class CostTypeListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			reason_view.setVisibility(View.GONE);
			reason_text.setText("");
			cost_type = COST_TYPE[arg2];

			if (arg2 == 0)
				act_cost_text.setText(cost);
			else if (arg2 == 4)
			{
				reason_view.setVisibility(View.VISIBLE);
				act_cost_text.setText("0");
			}
			else
				act_cost_text.setText("0");

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		return DialogUtil.showDialog(this, id);

	}

	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
