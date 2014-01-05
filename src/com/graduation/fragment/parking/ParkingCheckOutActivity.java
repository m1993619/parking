package com.graduation.fragment.parking;

import java.sql.Timestamp;
import java.util.ArrayList;

import android.app.Activity;
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
	private View progress;
	private View form;

	private SharedPreferences sp;

	private String[] COST_TYPE = new String[] { "正常缴费", "免费时段", "免费车辆", "逃逸", "其他" };

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
		progress = (View) findViewById(R.id.progress);
		form = (View) findViewById(R.id.form);

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
		// TODO Auto-generated method stub
		if (reason_view.getVisibility() == View.VISIBLE)
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
			showProgress(true);
			new CheckOutTask().execute();
		}

	}

	private class CheckOutTask extends AsyncTask<Void, Void, Boolean>
	{

		@Override
		protected Boolean doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
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

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub

			if (result)
			{
				Toast.makeText(ParkingCheckOutActivity.this, "update success!",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ParkingCheckOutActivity.this, MainActivity.class);
				intent.putExtra("state", "check");
				startActivity(intent);
				finish();
			}
			else
			{
				showProgress(false);
				Toast.makeText(ParkingCheckOutActivity.this, "false", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	private class CostTypeListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub
			reason_view.setVisibility(View.GONE);
			reason = "";
			switch (arg2)
			{
			case 0:
				act_cost_text.setText(cost);
				cost_type = COST_TYPE[0];
				break;
			case 1:
				act_cost_text.setText("0");
				cost_type = COST_TYPE[1];
				break;
			case 2:
				act_cost_text.setText("0");
				cost_type = COST_TYPE[2];
				break;
			case 3:
				act_cost_text.setText("0");
				cost_type = COST_TYPE[3];
				break;
			case 4:
				reason_view.setVisibility(View.VISIBLE);
				act_cost_text.setText("0");
				cost_type = COST_TYPE[4];
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub

		}

	}

	private void showProgress(boolean show)
	{
		progress.setVisibility(show ? View.VISIBLE : View.GONE);
		form.setVisibility(show ? View.GONE : View.VISIBLE);
	}

	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
