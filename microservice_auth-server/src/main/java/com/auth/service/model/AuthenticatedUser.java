package com.auth.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "AuthenticatedUser")
@Check(constraints = "status IN ('ACTIVE','DEAD')")
@SequenceGenerator(name = "authuser_seq", initialValue = 1)
public class AuthenticatedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authuser_seq")
	@Column(name = "authen_userid")
	private long authen_userid;
		
	@Column(name = "auth_key" , unique = true)
	private String auth_key;
	
	@Column(name = "valid_time_interval")
	private long valid_time_interval;
	
	@Column(name = "login_time")
	private String login_date_time;
	
	@Column(name = "status")
	private String status;
	
	public AuthenticatedUser(long authen_userid) {
		super();
		this.authen_userid = authen_userid;
	}

	public AuthenticatedUser() {
		super();
	}

	public AuthenticatedUser(String auth_key, long valid_time_interval, String login_date_time, String status) {
		super();
		this.auth_key = auth_key;
		this.valid_time_interval = valid_time_interval;
		this.login_date_time = login_date_time;
		this.status = status;
	}

	public long getAuthen_userid() {
		return authen_userid;
	}

	public void setAuthen_userid(long authen_userid) {
		this.authen_userid = authen_userid;
	}

	public String getAuth_key() {
		return auth_key;
	}

	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}

	public long getValid_time_interval() {
		return valid_time_interval;
	}

	public void setValid_time_interval(long valid_time_interval) {
		this.valid_time_interval = valid_time_interval;
	}

	public String getLogin_date_time() {
		return login_date_time;
	}

	public void setLogin_date_time(String login_date_time) {
		this.login_date_time = login_date_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
