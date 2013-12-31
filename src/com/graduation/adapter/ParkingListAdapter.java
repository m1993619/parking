package com.graduation.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graduation.parking.R;

public class ParkingListAdapter extends BaseAdapter
{
	Context context;
	LayoutInflater inflater;
	List<HashMap<String, Object>> list;

	public ParkingListAdapter(Context context, List<HashMap<String, Object>> list)
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
		HashMap<String,Object> map = list.get(position);
		int state = (Integer) map.get("f_state");
		View view = null;

		if (0 == state)
		{

			view = View.inflate(context, R.layout.parking_no_car, null);

			((TextView) view.findViewById(R.id.f_name)).setText((String) list.get(position).get(
					"f_name"));
			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString((Integer) (list
					.get(position).get("f_id"))));
			((TextView) view.findViewById(R.id.f_code)).setText((String) list.get(position).get(
					"f_code"));
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString((Integer) (list
					.get(position).get("f_street_id"))));

			((TextView) view.findViewById(R.id.f_type)).setText((String) list.get(position).get(
					"f_type"));

			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString((Integer) (list
					.get(position).get("f_state"))));

			((TextView) view.findViewById(R.id.f_is_free)).setText(Integer.toString((Integer) (list
					.get(position).get("f_is_free"))));

		}
		else
		{

			view = View.inflate(context, R.layout.pakring_has_car, null);

			((TextView) view.findViewById(R.id.f_name)).setText((String) list.get(position).get(
					"f_name"));
			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString((Integer) (list
					.get(position).get("f_id"))));
			((TextView) view.findViewById(R.id.f_code)).setText((String) list.get(position).get(
					"f_code"));
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString((Integer) (list
					.get(position).get("f_street_id"))));

			((TextView) view.findViewById(R.id.f_type)).setText((String) list.get(position).get(
					"f_type"));

			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString((Integer) (list
					.get(position).get("f_state"))));

			((TextView) view.findViewById(R.id.f_is_free)).setText(Integer.toString((Integer) (list
					.get(position).get("f_is_free"))));

			((TextView) view.findViewById(R.id.f_key)).setText((String) list.get(position).get(
					"f_key"));

			((TextView) view.findViewById(R.id.f_car_type)).setText((String) list.get(position).get(
					"f_car_type"));
			((TextView) view.findViewById(R.id.f_parking_stamp)).setText((String) list.get(position)
					.get("f_parking_stamp"));
			((TextView) view.findViewById(R.id.f_car_no)).setText((String) list.get(position).get(
					"f_car_no"));

		}

		return view;
	}

}
