package com.uom.idecide.pojo;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Admin implements Serializable{

	@Id
	private String adminId;//ID
	private String username;
	private String email;
	private String password;
	private Integer state;

	@Override
	public String toString() {
		return "Admin{" +
				"adminId='" + adminId + '\'' +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", state=" + state +
				'}';
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
