package com.graduation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import com.gratuation.model.User;

public class DbUtil
{

	private static final String URL = "jdbc:postgresql://m1993619.xicp.net:5432/parking";
	private static final String DB_USERNAME = "parking";
	private static final String DB_PASSWORD = "parking";

	public static int deleteRecord(String key, int id)
	{
		int i = 0;
		String sql1 = "delete from t_parking_record where f_key = ?";
		String sql2 = "update t_parking set f_state = 0 where f_id = ?";

		PreparedStatement ps1 = getPStatement(sql1);
		PreparedStatement ps2 = getPStatement(sql2);

		try
		{
			ps1.setString(1, key);
			ps2.setInt(1, id);

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

	public static int chekinEdit(ArrayList<Object> list, Boolean changed)
	{
		int i = 0;
		String sql1 = "update t_parking_record set f_car_no = ? ,f_car_type = ?, f_car_state = ?,f_act_cost = ? where f_key = ? ";
		String sql2 = "update t_parking_record set f_car_no = ? ,f_car_type = ?, f_car_state = ?,f_act_cost = ? ,f_parking_code = ? where f_key = ?";
		String sql3 = "update t_parking set f_state = 0 where f_id = ?";
		String sql4 = "update t_parking set f_state = 1 ,f_key = ? where f_code = ? and f_street_id = ?";

		PreparedStatement ps1 = getPStatement(sql1);
		PreparedStatement ps2 = getPStatement(sql2);
		PreparedStatement ps3 = getPStatement(sql3);
		PreparedStatement ps4 = getPStatement(sql4);

		try
		{
			if (!changed)
			{
				ps1.setString(1, (String) list.get(0));
				ps1.setString(2, (String) list.get(1));
				ps1.setString(3, (String) list.get(2));
				ps1.setDouble(4, (Double) list.get(3));
				ps1.setString(5, (String) list.get(4));

				i = ps1.executeUpdate();
			}
			else
			{
				ps2.setString(1, (String) list.get(0));
				ps2.setString(2, (String) list.get(1));
				ps2.setString(3, (String) list.get(2));
				ps2.setDouble(4, (Double) list.get(3));
				ps2.setString(5, (String) list.get(6));
				ps2.setString(6, (String) list.get(4));
				ps3.setInt(1, (Integer) list.get(5));
				ps4.setString(1, (String) list.get(4));
				ps4.setString(2, (String) list.get(6));
				ps4.setInt(3, (Integer) list.get(7));

				i = ps2.executeUpdate();
				ps3.executeUpdate();
				ps4.executeUpdate();

			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return i;
	}

	public static int checkin(ArrayList<Object> list)
	{
		int i = 0;

		String sql1 = "insert into t_parking_record (f_key,f_car_no,f_car_type,f_act_cost,f_creater_id,f_car_state,f_parking_code,f_street_id,f_shift_id,f_parking_stamp) values (?,?,?,?,?,?,?,?,?,?)";
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
			ps1.setString(6, (String) list.get(5));
			ps1.setString(7, (String) list.get(6));
			ps1.setInt(8, (Integer) list.get(7));
			ps1.setInt(9, (Integer) list.get(8));
			ps1.setTimestamp(10, (Timestamp) list.get(9));

			ps2.setString(1, (String) list.get(0));
			ps2.setInt(2, (Integer) list.get(10));

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

	public static int checkout(ArrayList<Object> list)
	{
		int i = 0;

		String sql1 = "update t_parking_record set f_leave_stamp = ?,f_cost = ?, f_act_cost = ?,f_cost_type = ?,f_reason = ?,f_coster_id = ? where f_key = ?";
		String sql2 = "update t_parking set f_state = 0 where f_id = ?";

		PreparedStatement ps1 = getPStatement(sql1);
		PreparedStatement ps2 = getPStatement(sql2);

		try
		{
			ps1.setTimestamp(1, (Timestamp) list.get(0));
			ps1.setDouble(2, (Double) list.get(1));
			ps1.setDouble(3, (Double) list.get(2));
			ps1.setString(4, (String) list.get(3));
			ps1.setString(5, (String) list.get(4));
			ps1.setInt(6, (Integer) list.get(5));
			ps1.setString(7, (String) list.get(6));

			ps2.setInt(1, (Integer) list.get(7));

			i = ps1.executeUpdate();
			ps2.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO: handle exception
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

	public static int escapePay(ArrayList<Object> list)
	{
		int i = 0;
		String sql1 = "insert into t_escape_pay_record (f_parking_record_id,f_parking_name,f_pay_type,f_act_pay,f_pay_stamp,f_user_id )values (?,?,?,?,?,?)";
		String sql2 = "update t_parking_record set f_escape_state = 1 where f_id = ?";

		PreparedStatement pSta1 = getPStatement(sql1);
		PreparedStatement pSta2 = getPStatement(sql2);

		try
		{
			pSta1.setInt(1, (Integer) list.get(0));
			pSta1.setString(2, (String) list.get(1));
			pSta1.setString(3, (String) list.get(2));
			pSta1.setDouble(4, (Double) list.get(3));
			pSta1.setTimestamp(5, (Timestamp) list.get(4));
			pSta1.setInt(6, (Integer) list.get(5));

			pSta2.setInt(1, (Integer) list.get(0));

			i = pSta1.executeUpdate();
			pSta2.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return i;
	}

	public static ArrayList<HashMap<String, Object>> getParkingList(int id)
	{
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "select p.f_id,p.f_code,p.f_name,p.f_street_id,p.f_type,p.f_state,p.f_is_free,p.f_key,rd.f_car_type,rd.f_parking_stamp,rd.f_car_no,s.f_name as f_street_name,rd.f_act_cost,rd.f_car_state, (select count(*) from t_parking_record where f_car_no =rd.f_car_no and f_cost_type='车辆逃逸' and f_escape_state = 0)as f_escape_count from t_parking p left join t_parking_record rd on rd.f_key = p.f_key left join t_street s on p.f_street_id = s.f_id left join t_parking_image tpi on tpi.f_key = rd.f_key left join t_user_parking tup on tup.f_parking_id = p.f_id where tup.f_user_id = ? order by p.f_id";
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
				map.put("f_street_name", rs.getString(12));
				map.put("f_act_cost", rs.getDouble(13));
				map.put("f_car_state", rs.getString(14));
				map.put("f_escape_count", rs.getInt(15));

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

	public static ArrayList<HashMap<String, Object>> getEscapeRecord(String car_no)
	{
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = " select tpr.*,(select f_name from t_street  where f_id = tpr.f_street_id ) as f_street_name ,round(abs(extract(epoch from f_leave_stamp - f_parking_stamp)/60)::numeric,0) || '分钟' as f_range_stamp ,(select f_name from t_user  where f_id = tpr.f_coster_id ) as f_coster_name ,(select f_name from t_user  where f_id = tpr.f_creater_id ) as f_creater_name ,epr.f_act_pay as act_pay,epr.f_pay_stamp as pay_stamp ,(select f_name from t_user  where f_id = epr.f_user_id ) as user_name  from t_parking_record tpr left join t_escape_pay_record epr on tpr.f_id = epr.f_parking_record_id where f_cost_type = '车辆逃逸'and f_car_no= ? order by f_escape_state";
		PreparedStatement ps = getPStatement(sql);

		try
		{
			ps.setString(1, car_no);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("f_id", rs.getInt(1));
				map.put("f_key", rs.getString(2));
				map.put("f_car_no", rs.getString(3));
				map.put("f_car_type", rs.getString(4));
				map.put("f_leave_stamp", rs.getTimestamp(5));
				map.put("f_cost", rs.getDouble(6));
				map.put("f_act_cost", rs.getDouble(7));
				map.put("f_cost_type", rs.getString(8));
				map.put("f_reason", rs.getString(9));
				map.put("f_shift_id", rs.getInt(10));
				map.put("f_coster_id", rs.getInt(11));
				map.put("f_creater_id", rs.getInt(12));
				map.put("f_parking_code", rs.getString(13));
				map.put("f_parking_stamp", rs.getTimestamp(14));
				map.put("f_car_state", rs.getString(15));
				map.put("f_street_id", rs.getInt(16));
				map.put("f_escape_state", rs.getInt(17));
				map.put("f_street_name", rs.getString(18));
				map.put("f_range_stamp", rs.getString(19));
				map.put("f_coster_name", rs.getString(20));
				map.put("f_creater_name", rs.getString(21));
				map.put("act_pay", rs.getDouble(22));
				map.put("pay_stamp", rs.getTimestamp(23));
				map.put("user_name", rs.getString(24));
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
