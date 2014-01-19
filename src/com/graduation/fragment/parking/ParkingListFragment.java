package com.graduation.fragment.parking;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.adapter.ParkingListAdapter;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;

public class ParkingListFragment<popupWindow> extends Fragment
{

	private ListView listView;
	private ArrayList<HashMap<String, Object>> list;
	private SearchView search;

	private SharedPreferences sp;
	private int id;
	private ParkingListAdapter pla;
	private PopupWindow popupWindow;
	private ProgressDialog dialog;

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
			list = DbUtil.getParkingList(id);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub
			pla = new ParkingListAdapter(getActivity(), list);
			listView.setAdapter(pla);
			listView.setOnItemLongClickListener(new MyLongClickListener());
			listView.setOnItemClickListener(new MyClickListener());
			// getActivity().dismissDialog(DialogUtil.PROGRESS_DIALOG);

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
				pla = new ParkingListAdapter(getActivity(), list);
				listView.setAdapter(pla);
			}
			else
			{
				listView.setFilterText(newText);
				ArrayList<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();

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
				HashMap<String, Object> map = getNoCarItem(arg1);

				Intent intent = new Intent(getActivity(), ParkingCheckInActivity.class);
				intent.putExtra("parking_code", (String) map.get("f_code"));
				intent.putExtra("f_street_id", (Integer) map.get("f_street_id"));
				intent.putExtra("f_id", (Integer) map.get("f_id"));
				startActivity(intent);
			}
			else
			{
				// HashMap<String, Object> map = getHasCarItem(arg1);
				//
				// Intent intent = new Intent(getActivity(), ParkingCheckOutActivity.class);
				// intent.putExtra("f_id", (Integer) map.get("f_id"));
				// intent.putExtra("f_key", (String) map.get("f_key"));
				// intent.putExtra("parking_code", (String) map.get("f_code"));
				// intent.putExtra("f_car_no", (String) map.get("f_car_no"));
				// intent.putExtra("f_parking_stamp", (String) map.get("f_parking_stamp"));
				// intent.putExtra("f_street_name", (String) map.get("f_street_name"));
				// intent.putExtra("pre_pay", (String) map.get("pre_pay"));
				// startActivity(intent);
				initPopWin(arg1);
			}
			return true;
		}

	}

	@SuppressLint("NewApi")
	private void initPopWin(final View view)
	{

		View menu = getLayoutInflater(new Bundle()).inflate(R.layout.menu_has_car, null);
		popupWindow = new PopupWindow(menu, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);

		popupWindow.setOutsideTouchable(true);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		System.out.println(view.getHeight());
		popupWindow.showAsDropDown(view, 0, -view.getHeight());

		menu.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				popupWindow.dismiss();
				editRecord(view);
			}
		});
		menu.findViewById(R.id.delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				popupWindow.dismiss();
				deleteRecord(view);
			}
		});
		menu.findViewById(R.id.checkout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				popupWindow.dismiss();
				checkout(view);
			}
		});
		menu.findViewById(R.id.escape).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				escape(view);
			}
		});

	}

	private void editRecord(View view)
	{

		Intent intent = new Intent(getActivity(), ParkingCheckInEditActivity.class);
		HashMap<String, Object> map = getHasCarItem(view);
		intent.putExtra("f_car_no", (String) map.get("f_car_no"));
		intent.putExtra("f_code", (String) map.get("f_code"));
		intent.putExtra("f_car_type", (String) map.get("f_car_type"));
		intent.putExtra("f_car_state", (String) map.get("f_car_state"));
		intent.putExtra("f_act_cost", (String) map.get("pre_pay"));
		intent.putExtra("f_key", (String) map.get("f_key"));
		intent.putExtra("f_id", (Integer) map.get("f_id"));
		intent.putStringArrayListExtra("code_list", pla.getCode());
		intent.putStringArrayListExtra("hasCar", pla.getHasCar());
		startActivity(intent);

	}

	private void deleteRecord(View view)
	{
		final String f_key = (String) ((TextView) view.findViewById(R.id.f_key)).getText();
		final int f_id = Integer.parseInt((String) ((TextView) view.findViewById(R.id.f_id)).getText());
		new AlertDialog.Builder(getActivity()).setTitle("").setMessage(" 确定删除此记录? ")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)
					{
						getActivity().showDialog(DialogUtil.PROGRESS_DIALOG);
						new DeleteRecordTask().execute(f_key, String.valueOf(f_id));
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();
					}
				}).show();

	}

	private void checkout(View view)
	{
		HashMap<String, Object> map = getHasCarItem(view);

		Intent intent = new Intent(getActivity(), ParkingCheckOutActivity.class);
		intent.putExtra("f_id", (Integer) map.get("f_id"));
		intent.putExtra("f_key", (String) map.get("f_key"));
		intent.putExtra("parking_code", (String) map.get("f_code"));
		intent.putExtra("f_car_no", (String) map.get("f_car_no"));
		intent.putExtra("f_parking_stamp", (String) map.get("f_parking_stamp"));
		intent.putExtra("f_street_name", (String) map.get("f_street_name"));
		intent.putExtra("pre_pay", (String) map.get("pre_pay"));
		startActivity(intent);
	}
	
	private void escape(View view)
	{
		String f_car_no  = (String) ((TextView) view.findViewById(R.id.f_car_no)).getText();
	}
	
	private class DeleteRecordTask extends AsyncTask<String, Void, Boolean>
	{

		protected void onPreExecute()
		{
			super.onPreExecute();
			getActivity().showDialog(DialogUtil.PROGRESS_DIALOG);
		}

		@Override
		protected Boolean doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			String f_key = params[0];
			int f_id = Integer.valueOf(params[1]);

			int i = DbUtil.deleteRecord(f_key, f_id);
			return (i == 1 ? true : false);
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			getActivity().dismissDialog(DialogUtil.PROGRESS_DIALOG);
			if (result)
			{
				new getListTask().execute();
				Toast.makeText(getActivity(), "update success!", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getActivity(), "update failed!", Toast.LENGTH_LONG).show();
			}
		}
	}

	private class MyClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub
			// initPopWin(arg1);
		}

	}

	private HashMap<String, Object> getNoCarItem(View view)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		String f_code = (String) ((TextView) view.findViewById(R.id.f_code)).getText();
		int f_street_id = Integer.parseInt((String) ((TextView) view.findViewById(R.id.f_street_id))
				.getText());
		int f_id = Integer.parseInt((String) ((TextView) view.findViewById(R.id.f_id)).getText());

		map.put("f_id", f_id);
		map.put("f_street_id", f_street_id);
		map.put("f_code", f_code);

		return map;

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
