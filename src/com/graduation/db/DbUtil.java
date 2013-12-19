package com.graduation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gratuation.model.Parking;
import com.gratuation.model.User;

public class DbUtil
{
	private static String URL = "jdbc:postgresql://192.168.1.119:5432/parking";
	private static String DB_USERNAME = "parking";
	private static String DB_PASSWORD = "parking";

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

	public static User login(String username, String password)
	{
		String sql = "select * from t_user where f_account = ? and f_password = ?";
		PreparedStatement pSta = getPStatement(sql);
		User user = null;

		try
		{
			pSta.setString(1, username);
			pSta.setString(2, password);

			ResultSet rs = pSta.executeQuery();
			while (rs.next())
			{
				user = new User();
				user.setF_id(rs.getInt("f_id"));
				user.setF_name(rs.getString("f_name"));
				user.setF_account(rs.getString("f_account"));
				user.setF_password(rs.getString("f_password"));
				user.setF_phone(rs.getString("f_phone"));
				user.setF_type(rs.getString("f_type"));
				user.setF_region_id(rs.getInt("f_region_id"));
				user.setF_shift_id(rs.getInt("f_shift_id"));
				user.setF_street_id(rs.getInt("f_street_id"));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	public static ArrayList<User> getUser()
	{
		String sql = "select * from t_user";
		ResultSet rs = getResult(sql);
		ArrayList<User> list = new ArrayList<User>();

		try
		{
			while (rs.next())
			{
				User user = new User();
				user.setF_id(rs.getInt("f_id"));
				user.setF_name(rs.getString("f_name"));
				user.setF_account(rs.getString("f_account"));
				user.setF_password(rs.getString("f_password"));
				user.setF_phone(rs.getString("f_phone"));
				user.setF_type(rs.getString("f_type"));
				user.setF_region_id(rs.getInt("f_region_id"));
				user.setF_shift_id(rs.getInt("f_shift_id"));
				user.setF_street_id(rs.getInt("f_street_id"));

				list.add(user);
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
			conn = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
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

	private static PreparedStatement getPStatement(String sql)
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
		PreparedStatement pStatement = null;

		try
		{
			conn = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
			pStatement = conn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pStatement;

	}
}
