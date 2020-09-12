package com.uom.idecide.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 实体类
 * @author Administrator
 *
 */
/*@Entity
@Table(name="tb_researcher")*/
public class Researcher implements Serializable{

	@Id
	private String researcherId;//ID
	private String username;
	private String email;
	private String password;
	private Integer state;

	@Override
	public String toString() {
		return "Researcher{" +
				"researcherId='" + researcherId + '\'' +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", state=" + state +
				'}';
	}

	public String getResearcherId() {
		return researcherId;
	}

	public void setResearcherId(String researcherId) {
		this.researcherId = researcherId;
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
