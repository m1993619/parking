package com.graduation.fragment.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.parking.R;
import com.graduation.util.DbUtil;

public class DailyReportFragment extends Fragment
{
	private SharedPreferences sp;

	private TextView date_text;
	private TextView user_name_text;
	private TextView street_text;
	private TextView cost_times_text;
	private TextView parking_range_text;
	private TextView act_cost_text;
	private TextView uncost_times_text;
	private TextView free_times_text;
	private TextView free_range_text;
	private TextView pre_cost_text;
	private TextView escape_pay_text;

	private int user_id;
	private String user_name;
	private String street;
	private String date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.daily_report, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		date_text = (TextView) getActivity().findViewById(R.id.date);
		user_name_text = (TextView) getActivity().findViewById(R.id.user_name);
		street_text = (TextView) getActivity().findViewById(R.id.street);
		cost_times_text = (TextView) getActivity().findViewById(R.id.cost_times);
		parking_range_text = (TextView) getActivity().findViewById(R.id.parking_range);
		act_cost_text = (TextView) getActivity().findViewById(R.id.act_cost);
		uncost_times_text = (TextView) getActivity().findViewById(R.id.uncost_times);
		free_times_text = (TextView) getActivity().findViewById(R.id.free_times);
		free_range_text = (TextView) getActivity().findViewById(R.id.free_range);
		pre_cost_text = (TextView) getActivity().findViewById(R.id.pre_cost);
		escape_pay_text = (TextView) getActivity().findViewById(R.id.escape_pay);

		sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

		user_id = sp.getInt("f_id", 0);

		user_name = sp.getString("f_name", "");
		street = sp.getString("f_street_name", "");
		date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		new getReport().execute();

	}

	private class getReport extends AsyncTask<Void, Void, Boolean>
	{

		HashMap<String, Object> map = new HashMap<String, Object>();

		@Override
		protected Boolean doInBackground(Void... params)
		{
			// TODO Auto-generated method stub

			map = DbUtil.getReport(user_id);

			return (map.isEmpty() ? false : true);
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result)
			{
				date_text.setText(date);
				user_name_text.setText(user_name);
				street_text.setText(street);
				cost_times_text.setText(String.valueOf(map.get("cost_times")));
				parking_range_text.setText((String) map.get("parking_range"));
				act_cost_text.setText(String.valueOf(map.get("act_cost")) + "元");
				uncost_times_text.setText(String.valueOf(map.get("uncost_times")));
				free_times_text.setText(String.valueOf(map.get("free_times")));
				free_range_text.setText((String) map.get("free_range"));
				pre_cost_text.setText(String.valueOf(map.get("pre_cost")) + "元");
				escape_pay_text.setText(String.valueOf(map.get("escape_pay")) + "元");

			}

		}

	}
}
