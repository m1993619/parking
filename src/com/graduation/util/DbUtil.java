package com.graduation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gratuation.model.Parking;
import com.gratuation.model.User;

public class DbUtil
{
	private static String URL = "jdbc:postgresql://172.16.139.19:5432/parking";
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

				parking.setF_index(rs.getInt("f_index"));
				parking.setF_is_free(rs.getInt("f_is_free"));

				parking.setF_key(rs.getString("f_key"));
				parking.setF_name(rs.getString("f_name"));

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
		String sql = "select u.*,sh.f_name as f_shift_name,st.f_name as f_street_name from t_user u left join t_shift sh on u.f_shift_id = sh.f_id left join t_street st on u.f_street_id = st.f_id  where u.f_account = ? and u.f_password = ?";
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
				user.setF_shift_id(rs.getInt("f_shift_id"));
				user.setF_street_id(rs.getInt("f_street_id"));
				user.setF_shift_name(rs.getString("f_shift_name"));
				user.setF_street_name(rs.getString("f_street_name"));
				System.out.println(rs.getString("f_shift_name"));
				System.out.println(rs.getString("f_street_name"));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	public static int updateUser(int id, String password, String phone)
	{
		int i = 0;
		String sql = "update t_user set f_password = ?,f_phone = ? where f_id = ? ";

		PreparedStatement pSta = getPStatement(sql);

		try
		{
			pSta.setString(1, password);
			pSta.setString(2, phone);
			pSta.setInt(3, id);

			i = pSta.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return i;
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
				user.setF_shift_name(rs.getString("f_shift_name"));
				user.setF_street_name(rs.getString("f_street_name"));
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

	public static List<HashMap<String, Object>> getParkingList(int id)
	{
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "	select p.f_id,p.f_code,p.f_name,p.f_street_id,p.f_type,p.f_state,"
				+ "p.f_is_free,p.f_key,rd.f_car_type,rd.f_parking_stamp,"
				+ "rd.f_car_no,s.f_name as f_street_name from t_parking p "
				+ "left join t_parking_record rd on rd.f_key = p.f_key "
				+ "left join t_street s on p.f_street_id = s.f_id "
				+ "left join t_parking_image tpi on tpi.f_key = rd.f_key "
				+ "left join t_user_parking tup on tup.f_parking_id = p.f_id "
				+ "where tup.f_user_id = ? order by p.f_id";
		PreparedStatement ps = getPStatement(sql);

		try
		{
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("f_id", rs.getInt(1));
				map.put("f_code", rs.getString(2));
				map.put("f_name", rs.getString(3));
				map.put("f_street_id", rs.getInt(4));
				map.put("f_type", rs.getString(5));
				map.put("f_state", rs.getInt(6));
				map.put("f_is_free", rs.getInt(7));
				map.put("f_key", rs.getString(8));
				map.put("f_car_type", rs.getString(9));
				map.put("f_parking_stamp", rs.getString(10));
				map.put("f_car_no", rs.getString(11));

				list.add(map);
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
