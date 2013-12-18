package com.graduation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gratuation.model.Parking;

public class DbUtil
{
	public static ArrayList<Parking> getParking()
	{
		String sql = "select * from t_parking";
		ResultSet rs = getResult(sql);
		ArrayList<Parking> list = new ArrayList<Parking>();

		try
		{
			while (rs.next())
			{
				Parking parking = new Parking();
				parking.setF_id(rs.getInt("f_id"));
				parking.setF_code(rs.getString("f_code"));
				parking.setF_has_device(rs.getInt("f_has_device"));
				parking.setF_index(rs.getInt("f_index"));
				parking.setF_is_free(rs.getInt("f_is_free"));
				parking.setF_is_private(rs.getInt("f_is_private"));
				parking.setF_key(rs.getString("f_key"));
				parking.setF_name(rs.getString("f_name"));
				parking.setF_region_id(rs.getInt("f_region_id"));
				parking.setF_remark(rs.getString("f_remark"));
				parking.setF_state(rs.getInt("f_state"));

				list.add(parking);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	private static ResultSet getResult(String sql)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement sta = null;
		ResultSet rs = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.119:5432/parking",
					"parking", "parking");
			sta = conn.createStatement();
			rs = sta.executeQuery(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}
}
