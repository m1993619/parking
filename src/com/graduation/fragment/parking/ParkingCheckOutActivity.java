package com.graduation.fragment.parking;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.graduation.parking.R;
import com.graduation.util.DbUtil;
import com.gratuation.model.Parking;

public class ParkingCheckOutActivity extends Activity
{
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		textView = (TextView) findViewById(R.id.text);

		new Thread(runnable).start();

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			textView.setText(val);

			Log.i("mylog", "请求结果-->" + val);
		}
	};

	Runnable runnable = new Runnable() {
		@Override
		public void run()
		{
			// String path = "http://192.168.1.119:8000/mobile/main";
			// try
			// {
			// String str = NetUtil.getData(path);
			// Message msg = new Message();
			// Bundle data = new Bundle();
			// data.putString("value", str);
			// msg.setData(data);
			// handler.sendMessage(msg);
			// }
			// catch (IOException e)
			// {
			// Log.e("getData", "error");
			// e.printStackTrace();
			// }
			StringBuffer buffer = new StringBuffer();
			ArrayList<Parking> list = DbUtil.getParking();
			for (Parking parking : list)
			{
				String str = "name:" + parking.getF_name() + "   ;password:"
						+ parking.getF_code() + "\n";
				buffer.append(str);
			}

			String strBuffer = new String(buffer);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("value", strBuffer);
			msg.setData(data);
			handler.sendMessage(msg);

		}
	};

	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
