package com.graduation.fragment.parking;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.graduation.parking.R;

public class ParkingCheckInActivity extends Activity
{
	private String province;
	private String city;
	private String act_cost;
	private String car_type;
	private String car_state;
	private String car_no;
	private String parking_code;

	private EditText car_no_text;
	private EditText parking_code_text;

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
	private final String[] ACT_COST = new String[] { "5", "10", "20", "50", "100" };
	private final String[] CAR_TYPE = new String[] { "小型轿车", "小型货车", "大型货车", "执勤车辆", "特殊车辆", "其他" };
	private final String[] CAR_STATE = new String[] { "车况良好", "轻微擦伤", "严重损坏", "其他" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				show();
			}

		});
		
		form = (View) findViewById(R.id.form);
		progress = (View)findViewById(R.id.progress);

		car_no_text = (EditText) findViewById(R.id.car_no);
		parking_code_text = (EditText) findViewById(R.id.parking_code);

		province_sp = (Spinner) findViewById(R.id.province);
		city_sp = (Spinner) findViewById(R.id.city);
		act_cost_sp = (Spinner) findViewById(R.id.act_cost);
		car_type_sp = (Spinner) findViewById(R.id.car_type);
		car_state_sp = (Spinner) findViewById(R.id.car_state);

		ArrayAdapter<String> province_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, PROVINCE);
		ArrayAdapter<String> city_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, CITY);
		ArrayAdapter<String> act_cost_ada = new ArrayAdapter<String>(this,
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

	private void show()
	{
		// TODO Auto-generated method stub
//		Toast.makeText(this, province + city + act_cost + car_type + car_state, Toast.LENGTH_LONG).show();

		form.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);
		
		
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
				act_cost = ACT_COST[arg2];
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
