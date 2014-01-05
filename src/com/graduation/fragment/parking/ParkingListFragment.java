package com.graduation.fragment.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.graduation.adapter.ParkingListAdapter;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;

public class ParkingListFragment extends Fragment
{

	private ListView listView;
	private List<HashMap<String, Object>> list;
	private SearchView search;

	private SharedPreferences sp;
	private int id;
	private ParkingListAdapter pla;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.parking_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		listView = (ListView) (getActivity().findViewById(R.id.parking_list));
		search = (SearchView) (getActivity().findViewById(R.id.search));

		search.setOnQueryTextListener(new SearchListener());

		listView.setTextFilterEnabled(true);

		getActivity();
		sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
		id = sp.getInt("f_id", 0);
		new getListTask().execute();

	}

	private class getListTask extends AsyncTask<Void, Void, Boolean>
	{

		@Override
		protected Boolean doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			list = DbUtil.getParkingList(id);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub

			pla = new ParkingListAdapter(getActivity(), list);
			listView.setAdapter(pla);
			listView.setOnItemLongClickListener(new MyListener());

		}

	}

	private class SearchListener implements SearchView.OnQueryTextListener
	{

		@Override
		public boolean onQueryTextChange(String newText)
		{
			// TODO Auto-generated method stub
			if (newText.isEmpty())
			{
				listView.clearTextFilter();
				pla = new ParkingListAdapter(getActivity(), list);
				listView.setAdapter(pla);
			}
			else
			{
				listView.setFilterText(newText);
				List<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();

				for (int i = 0; i < list.size(); i++)
				{
					String name = (String) list.get(i).get("f_name");
					String car = (String) list.get(i).get("f_car_no");
					if (name.contains(newText))
						searchList.add(list.get(i));

					if (!"".equals(car) && !(null == car))
						if (car.contains(newText))
							searchList.add(list.get(i));
				}
				pla = new ParkingListAdapter(getActivity(), searchList);
				listView.setAdapter(pla);
			}

			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String query)
		{
			// TODO Auto-generated method stub
			return false;
		}

	}

	private class MyListener implements OnItemLongClickListener
	{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			String parking_code = (String) ((TextView) arg1.findViewById(R.id.f_code)).getText();
			int street_id = Integer
					.parseInt((String) ((TextView) arg1.findViewById(R.id.f_street_id))
							.getText());
			int f_id = Integer.parseInt((String) ((TextView) arg1.findViewById(R.id.f_id)).getText());
			int f_state = Integer.parseInt((String) ((TextView) arg1.findViewById(R.id.f_state))
					.getText());

			if (0 == f_state)
			{
				Intent intent = new Intent(getActivity(), ParkingCheckInActivity.class);
				intent.putExtra("parking_code", parking_code);
				intent.putExtra("f_street_id", street_id);
				intent.putExtra("f_id", f_id);
				startActivity(intent);
			}
			else
			{
				String f_street_name = (String) ((TextView) arg1
						.findViewById(R.id.f_street_name)).getText();
				String f_car_no = (String) ((TextView) arg1.findViewById(R.id.f_car_no))
						.getText();
				String f_parking_stamp = (String) ((TextView) arg1
						.findViewById(R.id.f_parking_stamp)).getText();
				String pre_pay = (String) ((TextView) arg1.findViewById(R.id.act_cost))
						.getText();

				String f_key = (String) ((TextView) arg1.findViewById(R.id.f_key))
						.getText();
				Intent intent = new Intent(getActivity(), ParkingCheckOutActivity.class);
				intent.putExtra("f_id", f_id);
				intent.putExtra("f_key", f_key);
				intent.putExtra("parking_code", parking_code);
				intent.putExtra("f_car_no", f_car_no);
				intent.putExtra("f_parking_stamp", f_parking_stamp);
				intent.putExtra("f_street_name", f_street_name);
				intent.putExtra("pre_pay", pre_pay);
				startActivity(intent);
			}
			return true;

		}
	}
}
