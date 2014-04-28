package com.graduation.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.fragment.parking.ParkingCheckInEditActivity;
import com.graduation.parking.R;

public class ParkingRecordListAdapter extends BaseAdapter
{
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> list;

	public ParkingRecordListAdapter(Context context, ArrayList<HashMap<String, Object>> list)
	{
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		inflater = LayoutInflater.from(context);
		View view = null;

		HashMap<String, Object> map = list.get(position);


		final int f_id = (Integer) map.get("f_id");
		final String f_key = (String) map.get("f_key");
		final String f_car_type = (String) map.get("f_car_type");
		final String f_car_no = (String) map.get("f_car_no");
		Timestamp f_leave_stamp = (Timestamp) map.get("f_leave_stamp");
		Double f_cost = (Double) map.get("f_cost");
		final Double f_act_cost = (Double) map.get("f_act_cost");
		String f_cost_type = (String) map.get("f_cost_type");
		String f_reason = (String) map.get("f_reason");
		int f_shift_id = (Integer) map.get("f_shift_id");
		int f_coster_id = (Integer) map.get("f_coster_id");
		int f_creater_id = (Integer) map.get("f_creater_id");
		String f_parking_code = (String) map.get("f_parking_code");
		Timestamp f_parking_stamp = (Timestamp) map.get("f_parking_stamp");
		final String f_car_state = (String) map.get("f_car_state");
		
		int f_street_id = (Integer) map.get("f_street_id");
		int f_escape_state = (Integer) map.get("f_escape_state");
		int f_state = (Integer) map.get("f_state");
		String f_street_name = (String) map.get("f_street_name");
		
		String f_parking_name = f_street_name + f_parking_code + "号车位";
		
		
		
		if (0 == f_state)
		{

			view = View.inflate(context, R.layout.record_leave_item, null);
			
			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString(f_id));
			((TextView) view.findViewById(R.id.f_key)).setText(f_key);
			((TextView) view.findViewById(R.id.f_car_type)).setText(f_car_type);
			((TextView) view.findViewById(R.id.f_car_no)).setText(f_car_no);
			((TextView) view.findViewById(R.id.f_leave_stamp)).setText("离开时间：" + f_leave_stamp);
			((TextView) view.findViewById(R.id.f_parking_stamp)).setText("停车时间：" + f_parking_stamp);
			((TextView) view.findViewById(R.id.f_cost)).setText(String.valueOf(f_cost));
			((TextView) view.findViewById(R.id.act_cost)).setText("实收金额："+ String.valueOf(f_act_cost) + "元");
			((TextView) view.findViewById(R.id.f_cost_type)).setText(f_cost_type);
			((TextView) view.findViewById(R.id.f_reason)).setText(f_reason);
			((TextView) view.findViewById(R.id.f_shift_id)).setText(Integer.toString(f_shift_id));
			((TextView) view.findViewById(R.id.f_coster_id)).setText(Integer.toString(f_coster_id));
			((TextView) view.findViewById(R.id.f_creater_id)).setText(Integer.toString(f_creater_id));
			((TextView) view.findViewById(R.id.f_parking_code)).setText(f_parking_code);
			((TextView) view.findViewById(R.id.f_car_state)).setText(f_car_state);
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString(f_street_id));
			((TextView) view.findViewById(R.id.f_parking_name)).setText(f_parking_name);
			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString(f_state));
		
		}
		else
		{

			view = View.inflate(context, R.layout.record_parking_item, null);

			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString(f_id));
			((TextView) view.findViewById(R.id.f_key)).setText(f_key);
			((TextView) view.findViewById(R.id.f_car_type)).setText(f_car_type);
			((TextView) view.findViewById(R.id.f_car_no)).setText(f_car_no);
			((TextView) view.findViewById(R.id.f_parking_stamp)).setText("停车时间：" + f_parking_stamp);
			((TextView) view.findViewById(R.id.act_cost)).setText("实收金额："+ String.valueOf(f_act_cost) + "元");
			((TextView) view.findViewById(R.id.f_shift_id)).setText(Integer.toString(f_shift_id));
			((TextView) view.findViewById(R.id.f_creater_id)).setText(Integer.toString(f_creater_id));
			((TextView) view.findViewById(R.id.f_parking_code)).setText(f_parking_code);
			((TextView) view.findViewById(R.id.f_car_state)).setText(f_car_state);
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString(f_street_id));
			((TextView) view.findViewById(R.id.f_parking_name)).setText(f_parking_name);
			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString(f_state));

		}

		return view;
	}
}
