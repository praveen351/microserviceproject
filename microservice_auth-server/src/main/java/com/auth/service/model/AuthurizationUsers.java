package com.auth.service.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "authuser")
@Check(constraints = "user_type IN ('ADMIN','DEVELOPER','TESTER','ENDUSER') AND status IN ('ACTIVE','DEAD')")
@SequenceGenerator(name = "auth_seq", initialValue = 1)
public class AuthurizationUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_seq")
	@Column(name = "authuserid")
	private long authuserid;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "authxid")
	private List<AuthenticatedUser> authenticated_user;
	
	@Column(name = "user_name" , unique = true)
	private String username;
	
	@Column(name = "user_passwd")
	private String password;
	
	@Column(name = "user_role")
	private String userrole;
	
	@Column(name = "user_type")
	private String usertype;
	
	@Column(name = "status")
	private String status;
	
	public AuthurizationUsers(long authuserid) {
		super();
		this.authuserid = authuserid;
	}

	public AuthurizationUsers() {
		super();
	}

	public AuthurizationUsers(List<AuthenticatedUser> authenticated_user, String username, String password,
			String userrole, String usertype, String status) {
		super();
		this.authenticated_user = authenticated_user;
		this.username = username;
		this.password = password;
		this.userrole = userrole;
		this.usertype = usertype;
		this.status = status;
	}

	public long getAuthuserid() {
		return authuserid;
	}

	public void setAuthuserid(long authuserid) {
		this.authuserid = authuserid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AuthenticatedUser> getAuthenticated_user() {
		return authenticated_user;
	}

	public void setAuthenticated_user(List<AuthenticatedUser> authenticated_user) {
		this.authenticated_user = authenticated_user;
	}
	
}
