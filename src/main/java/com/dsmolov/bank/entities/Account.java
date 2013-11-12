package com.dsmolov.bank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;
	private Status status;
	private String accountNumber;
	private Double moneyLeft;
	
	
	@JsonManagedReference("user-account")
	@OneToOne(mappedBy="account")
	private User user;

	public Account() {	}
	
	public Account (int accountId, Status status, String accountNumber, Double moneyLeft, User user) {	
		super();
		this.accountId = accountId;
		this.status = status;
		this.accountNumber = accountNumber;
		this.moneyLeft = moneyLeft;
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", status=" + status + ", accountNumber=" + accountNumber
				+ ", moneyLeft=" + moneyLeft + ", user=" + user + "]";
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getMoneyLeft() {
		return moneyLeft;
	}
	public void setMoneyLeft(Double moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
