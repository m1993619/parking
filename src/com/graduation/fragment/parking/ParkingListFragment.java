package com.graduation.fragment.parking;

import java.util.HashMap;
import java.util.List;

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

import com.graduation.adapter.ParkingListAdapter;
import com.graduation.db.DbUtil;
import com.graduation.parking.R;

public class ParkingListFragment extends Fragment
{

	private final String CARPORT = "carport";
	private final String STATE = "state";
	private final String LICENSE_PLATE = "license_plate";
	private ListView listView;
	private List<HashMap<String, Object>> list;

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
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

		sp = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
		id = sp.getInt("f_id", 0);
		System.out.println(id + "___________________________");
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

	public class MyListener implements OnItemLongClickListener
	{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{

			if (0 == (Integer)(list.get(arg2).get("f_state")))
			{
				System.out.println("1111111111111111111");
				startActivity(new Intent(getActivity(), ParkingRegisterActivity.class));
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
