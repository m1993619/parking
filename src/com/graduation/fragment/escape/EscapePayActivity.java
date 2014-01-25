package com.graduation.fragment.escape;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.graduation.util.TimeUtil;
import com.graduation.util.ViewUtil;

public class EscapePayActivity extends Activity
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
	private String pay_type;
	private String act_pay;
	private int f_id;
	private int user_id;

	private Spinner pay_type_sp;
	private EditText act_pay_edit;
	private Button pay;

	private final String[] PAY_TYPE = new String[] { "全额缴费", "其他" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.escape_pay);
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

		pay_type_sp = (Spinner) findViewById(R.id.pay_type);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				PAY_TYPE);
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pay_type_sp.setAdapter(ada);

		act_pay_edit = (EditText) findViewById(R.id.act_pay);

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
		act_pay_edit.setText(String.valueOf(cost));

		((Button) findViewById(R.id.pay)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				save();
			}
		});
		user_id = getSharedPreferences("user", Context.MODE_PRIVATE).getInt("f_id", 0);
	}

	private void save()
	{
		act_pay = act_pay_edit.getText().toString();

		if (act_pay.isEmpty())
			act_pay_edit.setError("请输入实际缴费金额！");
		else
		{

			new SaveTask().execute();
		}
	};

	private class SaveTask extends AsyncTask<Void, Void, Boolean>
	{

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();

			showDialog(DialogUtil.PROGRESS_DIALOG);

		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			ArrayList list = new ArrayList();
			list.add(f_id);
			list.add(parking_name);
			list.add(pay_type);
			list.add(Double.valueOf(act_pay));
			list.add(TimeUtil.getTime());
			list.add(user_id);

			int i = DbUtil.escapePay(list);

			return (i == 1 ? true : false);
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result)
			{
//				Intent intent = new Intent(EscapePayActivity.this, EscapeRecordActivity.class);
//				intent.putExtra("f_car_no", car_no);
//				startActivity(intent);
				Toast.makeText(EscapePayActivity.this, "update success!", Toast.LENGTH_LONG)
						.show();
				finish();

			}
			else
			{
				dismissDialog(DialogUtil.PROGRESS_DIALOG);
				Toast.makeText(EscapePayActivity.this, "update failed!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		// TODO Auto-generated method stub
		return DialogUtil.showDialog(this, DialogUtil.PROGRESS_DIALOG);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}

}
