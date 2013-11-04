package com.dsmolov.bank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String userName;
	private String login;
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "ROLEID")
	private Role role;

	
	@JsonBackReference("user-account")
	@OneToOne
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;

	
	public User() {}
	
	public User(int userId, String userName, String login, String password, Role role, Account account) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.login = login;
		this.password = password;
		this.role = role;
		this.account = account;
	}
	
	@Override
	public String toString() {
		return ("User [UserId=" + userId + ", UserName=" + userName
				+ ", Login=" + login + ", Password=" + password + ", Role=" + role + ", Account=" + account +  "]");
	}
	

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
