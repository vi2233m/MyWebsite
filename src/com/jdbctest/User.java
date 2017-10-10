package com.jdbctest;

public class User {


	public int user_id = 0;
	public String user_password = null;
	public String user_name = null;
	public String user_email = null;
	public String user_company = null;
	public String user_school = null;
	public String created = null;
	
	public int getId() {
		return user_id;
	}
	public void setId(int id) {
		this.user_id = id;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_username) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_company() {
		return user_company;
	}
	public void setUser_company(String user_company) {
		this.user_company = user_company;
	}
	public String getUser_school() {
		return user_school;
	}
	public void setUser_school(String user_school) {
		this.user_school = user_school;
	}
	public String getUser_created() {
		return created;
	}
	public void setUser_created(String user_created) {
		this.created = created;
	}
}
