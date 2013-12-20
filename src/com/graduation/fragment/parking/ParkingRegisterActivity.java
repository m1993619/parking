package com.graduation.fragment.parking;

import com.graduation.parking.R;
import com.graduation.parking.R.id;
import com.graduation.parking.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ParkingRegisterActivity extends Activity
{
	private Spinner province;
	private Spinner city;
	private EditText license;
	private final String[] PROVINCE = new String[] { "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "³", "ԥ", "��", "��", "��", "��", "��", "��", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��", "��", "̨" };
	private final String[] CITY = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		province = (Spinner) findViewById(R.id.province);
		city = (Spinner) findViewById(R.id.city);
		license = (EditText) findViewById(R.id.license);
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,PROVINCE);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,CITY);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province.setAdapter(adapter1);
		city.setAdapter(adapter2);

	}
	public boolean onNavigateUp()
	{
		finish();
		return super.onNavigateUp();
	}
}
