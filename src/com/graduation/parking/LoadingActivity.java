package com.graduation.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run()
			{
				Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
				startActivity(intent);
				LoadingActivity.this.finish();
			}
		}, 1200);
	}
}
