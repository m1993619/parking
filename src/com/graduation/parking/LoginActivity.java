package com.graduation.parking;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.graduation.fragment.parking.ParkingCheckInEditActivity;
import com.graduation.util.DbUtil;
import com.graduation.util.DialogUtil;
import com.gratuation.model.User;

/**
 * Activity which displays a login screen to the user, offering registration as well.
 */
public class LoginActivity extends Activity
{

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		// Set up the login form.

		mUsernameView = (EditText) findViewById(R.id.username);

		mPasswordView = (EditText) findViewById(R.id.password);

		findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				attemptLogin();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form. If there are form errors (invalid
	 * email, missing fields, etc.), the errors are presented and no actual login attempt is made.
	 */
	private void attemptLogin()
	{
		if (mAuthTask != null)
		{
			return;
		}

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword))
		{
			mPasswordView.setError("密码不能为空！");
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mUsername))
		{
			mUsernameView.setError("用户名不能为空！");
			focusView = mUsernameView;
			cancel = true;
		}

		if (cancel)
		{
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else
		{

			mAuthTask = new UserLoginTask();
			mAuthTask.execute(mUsername, mPassword);
		}
	}

	// /**
	// * Shows the progress UI and hides the login form.
	// */
	// @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	// private void showProgress(final boolean show)
	// {
	// // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
	// // for very easy animations. If available, use these APIs to fade-in
	// // the progress spinner.
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
	// {
	// int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
	//
	// mLoginStatusView.setVisibility(View.VISIBLE);
	// mLoginStatusView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
	// .setListener(new AnimatorListenerAdapter() {
	// @Override
	// public void onAnimationEnd(Animator animation)
	// {
	// mLoginStatusView.setVisibility(show ? View.VISIBLE
	// : View.GONE);
	// }
	// });
	//
	// mLoginFormView.setVisibility(View.VISIBLE);
	// mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
	// .setListener(new AnimatorListenerAdapter() {
	// @Override
	// public void onAnimationEnd(Animator animation)
	// {
	// mLoginFormView.setVisibility(show ? View.GONE
	// : View.VISIBLE);
	// }
	// });
	// }
	// else
	// {
	// // The ViewPropertyAnimator APIs are not available, so simply show
	// // and hide the relevant UI components.
	// mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
	// mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	// }
	// }

	/**
	 * Represents an asynchronous login/registration task used to authenticate the user.
	 */
	public class UserLoginTask extends AsyncTask<String, Void, Boolean>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			// LoginActivity.this.showDialog(DialogUtil.PROGRESS_DIALOG);
		}

		@Override
		protected Boolean doInBackground(String... params)
		{
			// TODO: attempt authentication against a network service.

			String username = params[0];
			String password = params[1];

			User user = DbUtil.login(username, password);
			if (user == null)
				return false;
			else
			{
				SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("f_id", user.getF_id());
				editor.putString("f_name", user.getF_name());
				editor.putString("f_account", user.getF_account());
				editor.putString("f_password", user.getF_password());
				editor.putString("f_phone", user.getF_phone());
				editor.putString("f_type", user.getF_type());
				editor.putInt("f_shift_id", user.getF_shift_id());
				editor.putInt("f_street_id", user.getF_street_id());
				editor.putString("f_shift_name", user.getF_shift_name());
				editor.putString("f_street_name", user.getF_street_name());
				editor.commit();
				return true;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success)
		{
			mAuthTask = null;
			// LoginActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);

			if (success)
			{
				Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				mUsernameView.setError("用户名错误!");
				mPasswordView.setError("密码错误!");
				mUsernameView.requestFocus();
			}
		}

		@Override
		protected void onCancelled()
		{
			mAuthTask = null;
			LoginActivity.this.dismissDialog(DialogUtil.PROGRESS_DIALOG);
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id)
	{
		// TODO Auto-generated method stub
		return DialogUtil.showDialog(this, id);
	}
}
