package com.graduation.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil
{

	public static String createParkingRecordKey()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	public static Timestamp getTime()
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String time = sdf.format(new Date());

		Timestamp ts = Timestamp.valueOf(time);
		return ts;

	}

}
