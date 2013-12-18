package com.graduation.parking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WelcomeActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

		new Handler().postDelayed(r, 3000);

	}

	Runnable r = new Runnable() {
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
			startActivity(intent);

			finish();
		}
	};
}
