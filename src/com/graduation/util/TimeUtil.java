package com.graduation.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

	public static HashMap<String, String> getParkingFee(String timestamp)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		String time = null;
		String fee = null;

		Long deltaTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
				.getTime() - Timestamp.valueOf(timestamp).getTime();

		int deltamin = (int) (deltaTime / 1000 / 60);

		if (deltamin <= 20)
		{
			time = deltamin + "分";
			fee = "0";
		}
		else if (deltamin < 60)
		{
			time = deltamin + "分";
			fee = "3";
		}
		else if (deltamin < 1440)
		{
			time = deltamin / 60 + "小时" + deltamin % 60 + "分";
			fee = String.valueOf(((deltamin - 20) % 60 == 0 ? (deltamin - 20) / 60 * 3
					: ((deltamin - 20) / 60 + 1) * 3));
		}
		else
		{
			time = deltamin / 1440 + "天" + deltamin % 1440 / 60 + "小时";
			fee = String.valueOf(((deltamin / 1440) * 42 + deltamin % 1440 / 60 * 3));
		}

		map.put("time", time);
		map.put("fee", fee);

		return map;
	}

}
