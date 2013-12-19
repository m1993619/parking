package com.gratuation.model;

public class User
{
	private int f_id; // 主键
	private String f_name; // 姓名
	private String f_account; // 帐号
	private String f_password; // 密码
	private String f_phone; // 手机号码
	private String f_type; // 人员类别
	private int f_region_id; // 所属区域
	private int f_shift_id; // 所在班组ID
	private int f_street_id; // 所属路段

	public int getF_id()
	{
		return f_id;
	}

	public void setF_id(int f_id)
	{
		this.f_id = f_id;
	}

	public String getF_name()
	{
		return f_name;
	}

	public void setF_name(String f_name)
	{
		this.f_name = f_name;
	}

	public String getF_account()
	{
		return f_account;
	}

	public void setF_account(String f_account)
	{
		this.f_account = f_account;
	}

	public String getF_password()
	{
		return f_password;
	}

	public void setF_password(String password)
	{
		this.f_password = password;
	}

	public String getF_phone()
	{
		return f_phone;
	}

	public void setF_phone(String f_phone)
	{
		this.f_phone = f_phone;
	}

	public String getF_type()
	{
		return f_type;
	}

	public void setF_type(String f_type)
	{
		this.f_type = f_type;
	}

	public int getF_region_id()
	{
		return f_region_id;
	}

	public void setF_region_id(int f_region_id)
	{
		this.f_region_id = f_region_id;
	}

	public int getF_shift_id()
	{
		return f_shift_id;
	}

	public void setF_shift_id(int f_shift_id)
	{
		this.f_shift_id = f_shift_id;
	}

	public int getF_street_id()
	{
		return f_street_id;
	}

	public void setF_street_id(int f_street_id)
	{
		this.f_street_id = f_street_id;
	}

}
