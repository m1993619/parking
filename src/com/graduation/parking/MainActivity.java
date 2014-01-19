package com.graduation.parking;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.graduation.fragment.parking.ParkingListFragment;
import com.graduation.util.DialogUtil;
import com.graduation.util.ViewUtil;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity
{
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取登陆用户信息
		sp = getSharedPreferences("user", MODE_PRIVATE);
		editor = sp.edit();
		// System.out.println(sp.getString("f_name", ""));

		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.items_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the
		// drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav
		// drawer
		ViewUtil.setUpActionBar(this);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
			mDrawerLayout, /* DrawerLayout object */
			R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
			R.string.drawer_open, /* "open drawer" description for accessibility */
			R.string.drawer_close /* "close drawer" description for accessibility */
			) {
				@TargetApi(Build.VERSION_CODES.HONEYCOMB)
				@SuppressLint("NewApi")
				public void onDrawerClosed(View view)
				{

					getActionBar().setTitle(mTitle);
					invalidateOptionsMenu(); // creates call to

					// onPrepareOptionsMenu()
				}

				public void onDrawerOpened(View drawerView)
				{

					getActionBar().setTitle(mDrawerTitle);
					invalidateOptionsMenu(); // creates call to
								// onPrepareOptionsMenu()

				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);

		}

		if (savedInstanceState == null)
		{
			selectItem(0);
		}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		String state = intent.getStringExtra("state");
		if ("check".equals(state))
		{
			selectItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			if (mDrawerToggle.onOptionsItemSelected(item))
			{
				return true;
			}
		}

		// Handle action buttons
		switch (item.getItemId())
		{
		case R.id.logout:
			editor.clear();
			editor.commit();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			finish();
			break;
		case R.id.exit:
			finish();
			break;
		case R.id.user_infomation:
			startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			selectItem(position);
		}
	}

	private void selectItem(int position)
	{
		switch (position)
		{
		case 0:
			ParkingListFragment fragment = new ParkingListFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

		}
		mDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setTitle(mTitle);
		}

	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during onPostCreate() and
	 * onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			mDrawerToggle.syncState();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this).setTitle("")// 设置对话框的标题
					.setMessage(" 确定退出程序? ")// 设置对话框的内容
					.setPositiveButton("确定",// 设置对话框的确认按钮
							new DialogInterface.OnClickListener() {// 设置确认按钮的事件
								public void onClick(DialogInterface dialog,
										int which)
								{
									// 退出程序
									android.os.Process.killProcess(android.os.Process
											.myPid());
								}
							}).setNegativeButton("取消",// 设置对话框的取消按钮
							new DialogInterface.OnClickListener() {// 设置取消按钮的事件
								public void onClick(DialogInterface dialog,
										int which)
								{
									// 如果你什么操作都不做，可以选择不写入任何代码
									dialog.cancel();
								}
							}).show();

			return true;
		}
		else
		{
			return super.onKeyDown(keyCode, event);
		}
	}

	protected Dialog onCreateDialog(int id)
	{
		// TODO Auto-generated method stub
		return DialogUtil.showDialog(this, id);
	}
}
