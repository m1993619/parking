package com.graduation.fragment.escape;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.graduation.adapter.EscapeRecordAdapter;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.ViewUtil;

public class EscapeRecordActivity extends Activity
{

	private ListView listView;
	private ArrayList<HashMap<String, Object>> list;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.escape_record_activity);
		ViewUtil.setUpActionBar(this);

		listView = (ListView) findViewById(R.id.escapelist);

		String f_car_no = getIntent().getStringExtra("f_car_no");

		title = (TextView) findViewById(R.id.title);
		title.setText(f_car_no + " 的逃逸记录");

		new ShowListTask().execute(f_car_no);

	}

	private class ShowListTask extends AsyncTask<String, Void, Boolean>
	{

		@Override
		protected Boolean doInBackground(String... params)
		{

			list = DbUtil.getEscapeRecord(params[0]);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
			EscapeRecordAdapter era = new EscapeRecordAdapter(EscapeRecordActivity.this, list);
			listView.setAdapter(era);
			listView.setOnItemClickListener(new MyOnclickListener());
		}
	}

	private class MyOnclickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3)
		{
			// TODO Auto-generated method stub
			HashMap<String, Object> map = list.get(position);

			final int f_id = (Integer) map.get("f_id");
			final String f_car_no = (String) map.get("f_car_no");
			final String f_car_type = (String) map.get("f_car_type");
			final Timestamp f_leave_stamp = (Timestamp) map.get("f_leave_stamp");
			final Double f_cost = (Double) map.get("f_cost");
			final Double f_act_cost = (Double) map.get("f_act_cost");
			final String f_parking_code = (String) map.get("f_parking_code");
			final Timestamp f_parking_stamp = (Timestamp) map.get("f_parking_stamp");
			final String f_car_state = (String) map.get("f_car_state");
			final String f_street_name = (String) map.get("f_street_name");
			final int f_escape_state = (Integer) map.get("f_escape_state");
			final String parking_time = (String) map.get("f_range_stamp");
			final String f_coster_name = (String) map.get("f_coster_name");
			final String f_creater_name = (String) map.get("f_creater_name");
			final String parking_name = f_street_name + f_parking_code + "号车位";
			Intent intent = null;

			System.out.println("escape_state+"+f_escape_state);
			
			if (f_escape_state == 0)
				intent = new Intent(EscapeRecordActivity.this, EscapePayActivity.class);
			else
				intent = new Intent(EscapeRecordActivity.this, EscapeDetailActivity.class);

			intent.putExtra("f_id", f_id);
			intent.putExtra("f_car_no", f_car_no);
			intent.putExtra("f_leave_stamp", f_leave_stamp.toString());
			intent.putExtra("f_cost", f_cost);
			intent.putExtra("f_act_cost", f_act_cost);
			intent.putExtra("f_parking_stamp", f_parking_stamp.toString());
			intent.putExtra("f_car_state", f_car_state);
			intent.putExtra("f_escape_state", f_escape_state);
			intent.putExtra("parking_time", parking_time);
			intent.putExtra("f_coster_name", f_coster_name);
			intent.putExtra("f_creater_name", f_creater_name);
			intent.putExtra("f_car_type", f_car_type);
			intent.putExtra("parking_name", parking_name);

			startActivity(intent);
		}

	}

	@SuppressLint("NewApi")
	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
