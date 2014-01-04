package com.graduation.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

	public static int checkin(ArrayList<Object> list)
	{
		int i = 0;

		String sql1 = "insert into t_parking_record (f_key,f_car_no,f_car_type,f_act_cost,f_creater_id,f_charger_id,f_car_state,f_parking_code,f_street_id,f_shift_id,f_parking_stamp) values (?,?,?,?,?,?,?,?,?,?,?)";
		String sql2 = "update t_parking set f_state = 1,f_key = ? where f_id = ?";
		PreparedStatement ps1 = getPStatement(sql1);

		PreparedStatement ps2 = getPStatement(sql2);

		try
		{
			ps1.setString(1, (String) list.get(0));
			ps1.setString(2, (String) list.get(1));
			ps1.setString(3, (String) list.get(2));
			ps1.setDouble(4, (Double) list.get(3));
			ps1.setInt(5, (Integer) list.get(4));
			ps1.setInt(6, (Integer) list.get(5));
			ps1.setString(7, (String) list.get(6));
			ps1.setString(8, (String) list.get(7));
			ps1.setInt(9, (Integer) list.get(8));
			ps1.setInt(10, (Integer) list.get(9));
			ps1.setTimestamp(11, (Timestamp) list.get(10));

			ps2.setString(1, (String) list.get(0));
			ps2.setInt(2, (Integer) list.get(11));

			i = ps1.executeUpdate();
			ps2.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;

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

	public static List<HashMap<String, Object>> getParkingList(int id)
	{
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "	select p.f_id,p.f_code,p.f_name,p.f_street_id,p.f_type,p.f_state,"
				+ "p.f_is_free,p.f_key,rd.f_car_type,rd.f_parking_stamp,"
				+ "rd.f_car_no,s.f_name as f_street_name,rd.f_act_cost from t_parking p "
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
				map.put("f_parking_stamp", rs.getTimestamp(10));
				map.put("f_car_no", rs.getString(11));
				map.put("f_act_cost", rs.getDouble(13));

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
