package com.graduation.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemLongClickListener;


public class ParkingListFragment extends Fragment
{

	private final String CARPORT = "carport";
	private final String STATE = "state";
	private final String LICENSE_PLATE = "license_plate";
	private ListView listView;
	private List<HashMap<String, String>> list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.parking_list,container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		listView = (ListView) (getActivity().findViewById(R.id.parking_list));

		list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put(CARPORT, "111");
		map1.put(STATE, "ø’œ–");
		map1.put(LICENSE_PLATE, "");
		list.add(map1);

		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put(CARPORT, "222");
		map2.put(STATE, "’º”√");
		map2.put(LICENSE_PLATE, "‘∆A88888");
		list.add(map2);

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.parking_list_item,
				new String[] { CARPORT, STATE, LICENSE_PLATE }, new int[] { R.id.carport,
						R.id.state, R.id.license_plate });

		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new MyListener());
	}
	

	
	public class MyListener implements OnItemLongClickListener
	{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{

			if ("ø’œ–".equals(list.get(arg2).get(STATE)))
			{
				System.out.println("1111111111111111111");
				startActivity(new Intent(getActivity(),
						ParkingRegisterActivity.class));
				return true;
			}
			else
			{
				System.out.println("2222222222222222222");
				startActivity(new Intent(getActivity(), ParkingPayActivity.class));
				return true;
			}

		}
	}
}
