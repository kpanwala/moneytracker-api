package com.moneymanager.dto;

import lombok.Data;

@Data
public class UserData {
	
	public String username;
	public String password;
	String f_name;
	String l_name;
	
	public UserData(String username, String password, String f_name, String l_name) {
		super();
		this.username = username;
		this.password = password;
		this.f_name = f_name;
		this.l_name = l_name;
	}
	
	public UserData() {
		super();
	}
}
