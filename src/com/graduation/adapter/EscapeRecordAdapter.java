package com.graduation.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.parking.R;

public class EscapeRecordAdapter extends BaseAdapter
{

	Context context;
	ArrayList<HashMap<String, Object>> list;

	public EscapeRecordAdapter(Context context, ArrayList<HashMap<String, Object>> list)
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

		View view = View.inflate(context, R.layout.escape_list_item, null);

		HashMap<String, Object> map = list.get(position);

		int f_id = (Integer) map.get("f_id");
		String f_car_no = (String) map.get("f_car_no");
		String f_car_type = (String) map.get("f_car_type");
		Timestamp f_leave_stamp = (Timestamp) map.get("f_leave_stamp");
		Double f_cost = (Double) map.get("f_cost");
		Double f_act_cost = (Double) map.get("f_act_cost");
		String f_parking_code = (String) map.get("f_parking_code");
		Timestamp f_parking_stamp = (Timestamp) map.get("f_parking_stamp");
		String f_car_state = (String) map.get("f_car_state");
		String f_street_name = (String) map.get("f_street_name");
		int f_escape_state = (Integer) map.get("f_escape_state");
		String parking_time = (String) map.get("f_range_stamp");
		String f_coster_name = (String) map.get("f_coster_name");
		String f_creater_name = (String) map.get("f_creater_name");

		String parking_name = f_street_name + f_parking_code + "号车位";
		if (f_escape_state == 1)
			((ImageView) view.findViewById(R.id.pay_sign)).setImageResource(R.drawable.ic_true);

		((TextView) view.findViewById(R.id.car_no)).setText(f_car_no);
		((TextView) view.findViewById(R.id.parking_name)).setText(parking_name);
		((TextView) view.findViewById(R.id.parking_stamp)).setText("停车时间："+f_parking_stamp.toString());
		((TextView) view.findViewById(R.id.parking_time)).setText("时长:"+parking_time);
		((TextView) view.findViewById(R.id.cost)).setText("费用："+String.valueOf(f_cost)+"元");
		((TextView) view.findViewById(R.id.f_id)).setText(String.valueOf(f_id));
		((TextView) view.findViewById(R.id.f_car_type)).setText(f_car_type);
		((TextView) view.findViewById(R.id.f_leave_stamp)).setText(f_leave_stamp.toString());
		((TextView) view.findViewById(R.id.f_act_cost)).setText(String.valueOf(f_act_cost));
		((TextView) view.findViewById(R.id.f_car_state)).setText(f_car_state);
		((TextView) view.findViewById(R.id.f_escape_state)).setText(String.valueOf(f_escape_state));
		((TextView) view.findViewById(R.id.f_coster_name)).setText(f_coster_name);
		((TextView) view.findViewById(R.id.f_creater_name)).setText(f_creater_name);

		return view;
	}

}
