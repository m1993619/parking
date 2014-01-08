package com.graduation.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.fragment.parking.ParkingCheckInEditActivity;
import com.graduation.parking.R;

public class ParkingListAdapter extends BaseAdapter
{
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, Object>> list;

	public ParkingListAdapter(Context context, ArrayList<HashMap<String, Object>> list)
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

	private ArrayList<String> getCode()
	{
		ArrayList<String> code = new ArrayList<String>();
		for (HashMap map : list)
		{
			code.add((String) map.get("f_code"));
		}
		return code;
	}
	private ArrayList<String> getHasCar()
	{
		ArrayList<String> hasCar = new ArrayList<String>();
		for(HashMap map :list)
		{
			if((Integer)map.get("f_state") == 1)
				hasCar.add((String)map.get("f_code"));
		}
		return hasCar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		inflater = LayoutInflater.from(context);
		View view = null;

		HashMap<String, Object> map = list.get(position);

		int f_state = (Integer) map.get("f_state");
		final int f_id = (Integer) map.get("f_id");
		int f_street_id = (Integer) map.get("f_street_id");
		final String f_code = (String) map.get("f_code");
		String f_name = (String) map.get("f_name");
		String f_type = (String) map.get("f_type");
		final String f_key = (String) map.get("f_key");
		final String f_car_type = (String) map.get("f_car_type");
		final String f_car_no = (String) map.get("f_car_no");
		String f_street_name = (String) map.get("f_street_name");
		Timestamp f_parking_stamp = (Timestamp) map.get("f_parking_stamp");
		final Double f_act_cost = (Double) map.get("f_act_cost");
		final String f_car_state = (String) map.get("f_car_state");

		if (0 == f_state)
		{

			view = View.inflate(context, R.layout.parking_no_car, null);

			((TextView) view.findViewById(R.id.f_name)).setText(f_name);
			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString(f_id));
			((TextView) view.findViewById(R.id.f_code)).setText(f_code);
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString(f_street_id));
			((TextView) view.findViewById(R.id.f_type)).setText(f_type);
			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString(f_state));
			((TextView) view.findViewById(R.id.f_street_name)).setText(f_street_name);
		}
		else
		{

			view = View.inflate(context, R.layout.pakring_has_car, null);

			ImageView button = (ImageView) view.findViewById(R.id.has_car);
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub

					Intent intent = new Intent(context, ParkingCheckInEditActivity.class);
					intent.putExtra("f_car_no", f_car_no);
					intent.putExtra("f_code", f_code);
					intent.putExtra("f_car_type", f_car_type);
					intent.putExtra("f_car_state", f_car_state);
					intent.putExtra("f_act_cost", f_act_cost);
					intent.putExtra("f_key", f_key);
					intent.putExtra("f_id", f_id);
					intent.putStringArrayListExtra("code_list", getCode());
					intent.putStringArrayListExtra("hasCar", getHasCar());
					context.startActivity(intent);

				}
			});

			((TextView) view.findViewById(R.id.f_name)).setText(f_name);
			((TextView) view.findViewById(R.id.f_id)).setText(Integer.toString(f_id));
			((TextView) view.findViewById(R.id.f_code)).setText(f_code);
			((TextView) view.findViewById(R.id.f_street_id)).setText(Integer.toString(f_street_id));
			((TextView) view.findViewById(R.id.f_type)).setText(f_type);
			((TextView) view.findViewById(R.id.f_state)).setText(Integer.toString(f_state));
			((TextView) view.findViewById(R.id.f_key)).setText(f_key);
			((TextView) view.findViewById(R.id.f_car_type)).setText(f_car_type);
			((TextView) view.findViewById(R.id.f_parking_stamp)).setText("停车时间：" + f_parking_stamp);
			((TextView) view.findViewById(R.id.f_car_no)).setText(f_car_no);
			((TextView) view.findViewById(R.id.act_cost)).setText("预收金额："
					+ String.valueOf(f_act_cost) + "元");
			((TextView) view.findViewById(R.id.f_street_name)).setText(f_street_name);
			((TextView) view.findViewById(R.id.f_car_state)).setText(f_car_state);

		}

		return view;
	}
}
