package com.graduation.fragment.parking;

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
import android.widget.Toast;

import com.graduation.parking.MainActivity;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.graduation.util.ViewUtil;

public class ParkingCheckInEditActivity extends Activity
{
	private Spinner parking_code_sp;

	private EditText car_no_text;

	private Button save;

	public String province;
	public String city;
	private String car_no;
	private String parking_code;
	public String car_type;
	public String car_state;
	public Double act_cost;
	private String newCode;
	private int f_street_id;
	private String f_key;
	private int f_id;

	private Boolean code_changed = false;

	private ArrayList<String> code_list;
	private ArrayList<String> hasCar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin_edit);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
		f_street_id = sp.getInt("f_street_id", 0);

		Intent intent = getIntent();
		car_no = intent.getStringExtra("f_car_no");
		parking_code = intent.getStringExtra("f_code");
		car_type = intent.getStringExtra("f_car_type");
		car_state = intent.getStringExtra("f_car_state");
		act_cost = intent.getDoubleExtra("f_act_cost", 0);
		code_list = intent.getStringArrayListExtra("code_list");
		hasCar = intent.getStringArrayListExtra("hasCar");
		f_key = intent.getStringExtra("f_key");
		f_id = intent.getIntExtra("f_id", 0);

		province = car_no.substring(0, 1);
		city = car_no.substring(1, 2);

		ViewUtil.getProvinceSp(this, province);
		ViewUtil.getCitySp(this, city);
		ViewUtil.getCarTypeSp(this, car_type);
		ViewUtil.getCarStateSp(this, car_state);
		ViewUtil.getActCostSp(this, act_cost);
		parking_code_sp = (Spinner) findViewById(R.id.parking_code);

		ArrayAdapter<String> parking_code_ada = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, code_list);
		parking_code_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		parking_code_sp.setAdapter(parking_code_ada);
		parking_code_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				newCode = code_list.get(arg2);
				System.out.println(newCode);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		parking_code_sp.setSelection(code_list.indexOf(parking_code));

		car_no_text = (EditText) findViewById(R.id.car_no);
		car_no_text.setText(car_no.substring(3));

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				save();
			}
		});

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

		if (!newCode.equals(parking_code))
		{
			if (hasCar.contains(newCode))
			{
				Toast.makeText(this, newCode + "号车位占用中，请核对车位后重试！", Toast.LENGTH_LONG).show();
				focusView = parking_code_sp;
				cancel = true;
			}
			else
				code_changed = true;
		}

		if (cancel)
		{
			focusView.requestFocus();
		}
		else
		{
			new CheckInEditTask().execute();
		}

	}

	private class CheckInEditTask extends AsyncTask<Void, Void, Boolean>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			ParkingCheckInEditActivity.this.showDialog(DialogUtil.PROGRESS_DIALOG);
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			car_no = province + city + " " + car_no;

			ArrayList<Object> list = new ArrayList<Object>();
			list.add(car_no);
			list.add(car_type);
			list.add(car_state);
			list.add(act_cost);
			list.add(f_key);
			list.add(f_id);
			list.add(newCode);
			list.add(f_street_id);
			
			System.out.println("newCode:"+newCode+";parking_code:"+parking_code+code_changed);

			int i = DbUtil.chekinEdit(list, code_changed);

			return (i == 1 ? true : false);
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
			if (result)
			{

				Intent intent = new Intent(ParkingCheckInEditActivity.this, MainActivity.class);
				intent.putExtra("state", "check");
				startActivity(intent);
				Toast.makeText(ParkingCheckInEditActivity.this, "update success!",
						Toast.LENGTH_LONG).show();
				finish();
			}
			else
			{

				ParkingCheckInEditActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);
				Toast.makeText(ParkingCheckInEditActivity.this, "update failed!",
						Toast.LENGTH_LONG).show();
			}

		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		// TODO Auto-generated method stub
		return DialogUtil.showDialog(this, id);
	}

	@Override
	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}

}
