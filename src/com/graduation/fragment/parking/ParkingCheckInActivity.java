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

public class ParkingCheckInActivity extends Activity
{
	private SharedPreferences sp;

	private String province;
	private String city;
	private Double act_cost;
	private String car_type;
	private String car_state;
	private String car_no;
	private String parking_code;
	private int street_id;
	private String key;
	private int user_id;
	private int shift_id;
	private int f_id;

	private EditText car_no_text;
	private TextView parking_code_text;

	private Spinner province_sp;
	private Spinner city_sp;
	private Spinner act_cost_sp;
	private Spinner car_type_sp;
	private Spinner car_state_sp;

	private Button save;

	private View form;
	private View progress;

	private final String[] PROVINCE = new String[] { "川", "赣", "桂", "甘", "贵", "沪", "黑", "京", "津", "晋", "辽", "鲁",
			"蒙", "闽", "宁", "青", "鄂", "琼", "苏", "皖", "湘", "新", "陕", "渝", "冀", "豫", "云", "粤", "浙", "藏" };
	private final String[] CITY = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private final Double[] ACT_COST = new Double[] { (double) 0, (double) 5, (double) 10, (double) 20,
			(double) 50, (double) 100 };
	private final String[] CAR_TYPE = new String[] { "小型轿车", "小型货车", "大型货车", "执勤车辆", "特殊车辆", "其他" };
	private final String[] CAR_STATE = new String[] { "车况良好", "轻微擦伤", "严重损坏", "其他" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		parking_code = getIntent().getStringExtra("parking_code");
		street_id = getIntent().getIntExtra("f_street_id", 0);
		f_id = getIntent().getIntExtra("f_id", 0);
		key = TimeUtil.createParkingRecordKey();

		sp = getSharedPreferences("user", MODE_PRIVATE);
		user_id = sp.getInt("f_id", 0);
		shift_id = sp.getInt("f_shift_id", 0);

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				save();
			}

		});

		form = (View) findViewById(R.id.form);
		progress = (View) findViewById(R.id.progress);

		car_no_text = (EditText) findViewById(R.id.car_no);
		parking_code_text = (TextView) findViewById(R.id.parking_code);

		parking_code_text.setText(parking_code + "号");

		province_sp = (Spinner) findViewById(R.id.province);
		city_sp = (Spinner) findViewById(R.id.city);
		act_cost_sp = (Spinner) findViewById(R.id.act_cost);
		car_type_sp = (Spinner) findViewById(R.id.car_type);
		car_state_sp = (Spinner) findViewById(R.id.car_state);

		ArrayAdapter<String> province_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, PROVINCE);
		ArrayAdapter<String> city_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CITY);
		ArrayAdapter<Double> act_cost_ada = new ArrayAdapter<Double>(this,
				android.R.layout.simple_spinner_item, ACT_COST);
		ArrayAdapter<String> car_type_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CAR_TYPE);
		ArrayAdapter<String> car_state_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CAR_STATE);

		province_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		act_cost_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		car_type_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		car_state_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		province_sp.setAdapter(province_ada);
		city_sp.setAdapter(city_ada);
		act_cost_sp.setAdapter(act_cost_ada);
		car_type_sp.setAdapter(car_type_ada);
		car_state_sp.setAdapter(car_state_ada);

		setListener();

	}

	private void save()
	{
		car_no = car_no_text.getText().toString();
		System.out.println("car_no" + car_no);
		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(car_no))
		{
			car_no_text.setError("车牌号不能为空！");
			focusView = car_no_text;
			cancel = true;
		}
		else if (car_no.length() < 5)
		{
			car_no_text.setError("车牌号不完整！");
			focusView = car_no_text;
			cancel = true;
		}

		if (cancel)
		{
			focusView.requestFocus();
		}
		else
		{
			showProgress(true);
			new CheckInTask().execute();
		}

	}

	private class CheckInTask extends AsyncTask<Void, Void, Boolean>
	{
		@Override
		protected Boolean doInBackground(Void... params)
		{
			Timestamp time = TimeUtil.getTime();
			car_no = province + city + " " + car_no;
			ArrayList<Object> list = new ArrayList<Object>();
			list.add(key);
			list.add(car_no);
			list.add(car_type);
			list.add(act_cost);
			list.add(user_id);
			list.add(user_id);
			list.add(car_state);
			list.add(parking_code);
			list.add(street_id);
			list.add(shift_id);
			list.add(time);
			list.add(f_id);

			int i = DbUtil.checkin(list);

			if (1 == i)
				return true;
			else
				return false;

		}

		@Override
		protected void onPostExecute(Boolean result)
		{

			if (result)
			{
				Toast.makeText(ParkingCheckInActivity.this, "update success!",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ParkingCheckInActivity.this, MainActivity.class);
				intent.putExtra("state", "check");
				startActivity(intent);
				finish();

			}
			else
			{
				showProgress(false);
				Toast.makeText(ParkingCheckInActivity.this, "false", Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void showProgress(boolean show)
	{
		progress.setVisibility(show ? View.VISIBLE : View.GONE);
		form.setVisibility(show ? View.GONE : View.VISIBLE);
	}

	private void setListener()
	{
		// TODO Auto-generated method stub
		province_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				province = PROVINCE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		city_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				city = CITY[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		act_cost_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				act_cost = (Double) (ACT_COST[arg2]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		car_type_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				car_type = CAR_TYPE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		car_state_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				car_state = CAR_STATE[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});

	}

	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}

}
