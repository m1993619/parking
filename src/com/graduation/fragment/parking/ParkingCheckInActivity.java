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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.parking.MainActivity;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.graduation.util.TimeUtil;
import com.graduation.util.ViewUtil;

public class ParkingCheckInActivity extends Activity
{
	private SharedPreferences sp;

	public String province;
	public String city;
	public Double act_cost;
	public String car_type;
	public String car_state;
	private String car_no;
	private String parking_code;
	private int street_id;
	private String key;
	private int user_id;
	private int shift_id;
	private int f_id;

	private EditText car_no_text;
	private TextView parking_code_text;

	private Button save;

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
				save();
			}

		});

		car_no_text = (EditText) findViewById(R.id.car_no);
		parking_code_text = (TextView) findViewById(R.id.parking_code);

		parking_code_text.setText(parking_code + "号");

		ViewUtil.getProvinceSp(this);
		ViewUtil.getCitySp(this);
		ViewUtil.getActCostSp(this);
		ViewUtil.getCarTypeSp(this);
		ViewUtil.getCarStateSp(this);

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
			// showProgress(true);
			new CheckInTask().execute();
		}

	}

	private class CheckInTask extends AsyncTask<Void, Void, Boolean>
	{

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute()
		{
			ParkingCheckInActivity.this.showDialog(DialogUtil.PROGRESS_DIALOG);

		}

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

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Boolean result)
		{
			if (result)
			{

				Intent intent = new Intent(ParkingCheckInActivity.this, MainActivity.class);
				intent.putExtra("state", "check");
				startActivity(intent);
				Toast.makeText(ParkingCheckInActivity.this, "update success!",
						Toast.LENGTH_LONG).show();
				finish();

			}
			else
			{
				// showProgress(false);
				ParkingCheckInActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);

				Toast.makeText(ParkingCheckInActivity.this, "update failed!",
						Toast.LENGTH_SHORT).show();
			}
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
