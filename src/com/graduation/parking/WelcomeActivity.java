package com.graduation.parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

public class WelcomeActivity extends Activity
{
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

		sp = getSharedPreferences("user", MODE_PRIVATE);
		
		
		
//		System.out.println(sp.getString("f_name", ""));
		new Handler().postDelayed(r, 3000);

	}
	
	
	

	Runnable r = new Runnable() {
		@Override
		public void run()
		{
			Intent intent = new Intent();

			if (sp.getInt("f_id", 0) == 0)
				intent.setClass(WelcomeActivity.this, LoginActivity.class);
			else
				intent.setClass(WelcomeActivity.this, MainActivity.class);

			startActivity(intent);

			finish();
		
//		getConn();
		
		}
	};
}
