package com.graduation.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.fragment.escape.EscapeDetailActivity;
import com.graduation.fragment.escape.EscapePayActivity;
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
		if (f_escape_state == 1)
			((ImageView) view.findViewById(R.id.pay_sign)).setImageResource(R.drawable.ic_true);

		((TextView) view.findViewById(R.id.car_no)).setText(f_car_no);
		((TextView) view.findViewById(R.id.parking_name)).setText(parking_name);
		((TextView) view.findViewById(R.id.parking_stamp)).setText("停车时间：" + f_parking_stamp.toString());
		((TextView) view.findViewById(R.id.parking_time)).setText("时长:" + parking_time);
		((TextView) view.findViewById(R.id.cost)).setText("费用：" + String.valueOf(f_cost) + "元");
		((TextView) view.findViewById(R.id.f_id)).setText(String.valueOf(f_id));
		((TextView) view.findViewById(R.id.f_car_type)).setText(f_car_type);
		((TextView) view.findViewById(R.id.f_leave_stamp)).setText(f_leave_stamp.toString());
		((TextView) view.findViewById(R.id.f_act_cost)).setText(String.valueOf(f_act_cost));
		((TextView) view.findViewById(R.id.f_car_state)).setText(f_car_state);
		((TextView) view.findViewById(R.id.f_escape_state)).setText(String.valueOf(f_escape_state));
		((TextView) view.findViewById(R.id.f_coster_name)).setText(f_coster_name);
		((TextView) view.findViewById(R.id.f_creater_name)).setText(f_creater_name);

		// view.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v)
		// {
		// Intent intent = null;
		//
		// if (f_escape_state == 0)
		// intent = new Intent(context, EscapePayActivity.class);
		// else
		// intent = new Intent(context, EscapeDetailActivity.class);
		//
		// intent.putExtra("f_id", f_id);
		// intent.putExtra("f_car_no", f_car_no);
		// intent.putExtra("f_leave_stamp", f_leave_stamp);
		// intent.putExtra("f_cost", f_cost);
		// intent.putExtra("f_act_cost", f_act_cost);
		// intent.putExtra("f_parking_stamp", f_parking_stamp);
		// intent.putExtra("f_car_state", f_car_state);
		// intent.putExtra("f_escape_state", f_escape_state);
		// intent.putExtra("parking_time", parking_time);
		// intent.putExtra("f_coster_name", f_coster_name);
		// intent.putExtra("f_creater_name", f_creater_name);
		// intent.putExtra("f_car_type", f_car_type);
		// intent.putExtra("parking_name", parking_name);
		// context.startActivity(intent);
		// }
		// });
		return view;
	}
}
