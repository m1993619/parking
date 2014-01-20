package com.graduation.fragment.escape;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.graduation.adapter.EscapeRecordAdapter;
import com.graduation.parking.R;
import com.graduation.util.DbUtil;

public class EscapeRecordFragment extends Fragment
{

	private ListView listView;
	private ArrayList<HashMap<String, Object>> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.escape_record_activity, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		System.out.println("sadfasdfasdfasdf");
		listView = (ListView) (getActivity().findViewById(R.id.escapelist));

		Bundle bundle = getArguments();
		String f_car_no = bundle.getString("f_car_no");

		new ShowListTask().execute(f_car_no);
	}

	private class ShowListTask extends AsyncTask<String, Void, Boolean>
	{

		@Override
		protected Boolean doInBackground(String... params)
		{

			list = DbUtil.getEscapeRecord(params[0]);
			System.out.println("list"+list);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
			EscapeRecordAdapter era = new EscapeRecordAdapter(getActivity(), list);
			listView.setAdapter(era);

			System.out.println("I'm here");
		}

	}

}
