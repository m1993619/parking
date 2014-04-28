package com.graduation.fragment.record;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import com.graduation.adapter.ParkingRecordListAdapter;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;

public class ParkingRecordListFragment extends Fragment
{

	private ListView listView;
	private ArrayList<HashMap<String, Object>> list;
	private SearchView search;

	private SharedPreferences sp;
	private int id;
	private ParkingRecordListAdapter pla;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.record_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		listView = (ListView) (getActivity().findViewById(R.id.record_list));
		search = (SearchView) (getActivity().findViewById(R.id.search));

		search.setOnQueryTextListener(new SearchListener());

		listView.setTextFilterEnabled(true);

		sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
		id = sp.getInt("f_id", 0);

		new getListTask().execute();

	}

	private class getListTask extends AsyncTask<Void, Void, Boolean>
	{

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			// getActivity().showDialog(DialogUtil.PROGRESS_DIALOG);
		}

		@Override
		protected Boolean doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			
			list = DbUtil.getParkingRecord(id);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub
			pla = new ParkingRecordListAdapter(getActivity(), list);
			listView.setAdapter(pla);
			listView.setOnItemLongClickListener(new MyLongClickListener());
		}

	}

	@SuppressLint("NewApi")
	private class SearchListener implements SearchView.OnQueryTextListener
	{

		@SuppressLint("NewApi")
		@Override
		public boolean onQueryTextChange(String newText)
		{
			// TODO Auto-generated method stub
			if (newText.isEmpty())
			{
				listView.clearTextFilter();
				pla = new ParkingRecordListAdapter(getActivity(), list);
				listView.setAdapter(pla);
			}
			else
			{
				listView.setFilterText(newText);
				ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();

				for (int i = 0; i < list.size(); i++)
				{
					String street_name = (String) list.get(i).get("f_street_name");
					String parking_code = (String) list.get(i).get("f_parking_code");
					String name = street_name + parking_code + "号车位";
					String car = (String) list.get(i).get("f_car_no");
					if (name.contains(newText))
						searchList.add(list.get(i));

					if (!"".equals(car) && !(null == car))
						if (car.contains(newText))
							searchList.add(list.get(i));
				}
				pla = new ParkingRecordListAdapter(getActivity(), searchList);
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

	private class MyLongClickListener implements OnItemLongClickListener
	{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			int f_state = Integer.parseInt((String) ((TextView) arg1.findViewById(R.id.f_state))
					.getText());
			// initPopWin(arg1);
			if (0 == f_state)
			{

			}
			else
			{


			}
			return true;
		}

	}

	

	private HashMap<String, Object> getHasCarItem(View view)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();

		String f_street_name = (String) ((TextView) view.findViewById(R.id.f_street_name)).getText();
		String f_car_no = (String) ((TextView) view.findViewById(R.id.f_car_no)).getText();
		String f_parking_stamp = (String) ((TextView) view.findViewById(R.id.f_parking_stamp)).getText();
		String pre_pay = (String) ((TextView) view.findViewById(R.id.act_cost)).getText();
		String f_key = (String) ((TextView) view.findViewById(R.id.f_key)).getText();
		int f_id = Integer.parseInt((String) ((TextView) view.findViewById(R.id.f_id)).getText());
		String f_code = (String) ((TextView) view.findViewById(R.id.f_code)).getText();
		String f_car_type = (String) ((TextView) view.findViewById(R.id.f_car_type)).getText();
		String f_car_state = (String) ((TextView) view.findViewById(R.id.f_car_state)).getText();

		map.put("f_street_name", f_street_name);
		map.put("f_car_no", f_car_no);
		map.put("f_parking_stamp", f_parking_stamp);
		map.put("pre_pay", pre_pay);
		map.put("f_key", f_key);
		map.put("f_id", f_id);
		map.put("f_code", f_code);
		map.put("f_car_type", f_car_type);
		map.put("f_car_state", f_car_state);

		return map;

	}

}
