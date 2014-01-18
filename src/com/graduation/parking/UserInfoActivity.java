package com.graduation.parking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.graduation.util.ViewUtil;

@SuppressLint("CommitPrefEdits")
public class UserInfoActivity extends Activity
{

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private EditText f_name;
	private EditText f_account;
	private EditText f_password;
	private EditText f_phone;
	private EditText f_type;
	private EditText f_shift_name;
	private EditText f_street_name;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		ViewUtil.setUpActionBar(this);

		button = (Button) findViewById(R.id.up_user_info);

		f_name = (EditText) findViewById(R.id.f_name);
		f_account = (EditText) findViewById(R.id.f_account);
		f_password = (EditText) findViewById(R.id.f_password);
		f_phone = (EditText) findViewById(R.id.f_phone);
		f_type = (EditText) findViewById(R.id.f_type);
		f_shift_name = (EditText) findViewById(R.id.f_shift_name);
		f_street_name = (EditText) findViewById(R.id.f_street_name);

		sp = getSharedPreferences("user", MODE_PRIVATE);
		editor = sp.edit();

		showUserInfo();

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				new UpdateTask().execute();
			}
		});
	}

	private void showUserInfo()
	{
		f_name.setText(sp.getString("f_name", ""));
		f_account.setText(sp.getString("f_account", ""));
		f_password.setText(sp.getString("f_password", ""));
		f_phone.setText(sp.getString("f_phone", ""));
		f_type.setText(sp.getString("f_type", ""));
		f_shift_name.setText(sp.getString("f_shift_name", ""));
		f_street_name.setText(sp.getString("f_street_name", ""));
	}

	private class UpdateTask extends AsyncTask<Void, Void, Boolean>
	{

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
			UserInfoActivity.this.showDialog(DialogUtil.PROGRESS_DIALOG);

		}

		@Override
		protected Boolean doInBackground(Void... params)
		{

			int id = sp.getInt("f_id", 0);
			String password = f_password.getText().toString();
			String phone = f_phone.getText().toString();

			int status = DbUtil.updateUser(id, password, phone);

			if (1 == status)
			{
				System.out.println(password + phone);
				editor.putString("f_password", password);
				editor.putString("f_phone", phone);
				editor.commit();
				return true;
			}
			else
				return false;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Boolean result)
		{

			UserInfoActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);
			if (result)
			{
				System.out.println("success");
				f_password.setEnabled(false);
				f_phone.setEnabled(false);
				button.setVisibility(View.GONE);
				Toast.makeText(UserInfoActivity.this, "更新数据成功!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				System.out.println("fail");
				Toast.makeText(UserInfoActivity.this, "更新数据失败，请重试！", Toast.LENGTH_SHORT).show();
			}

		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		// TODO Auto-generated method stub
		return DialogUtil.showDialog(this, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.user_info, menu);
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case R.id.user_info_edit:
			f_password.setEnabled(true);
			f_phone.setEnabled(true);
			button.setVisibility(View.VISIBLE);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
