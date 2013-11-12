package com.dsmolov.bank.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	
	private String sourceAccount;
	private String destinationAccount;
	private String transactionDate;
	private Double amountMoney;

	@ManyToOne
	@JoinColumn(name = "acc_id")
	private Account account;

	
	public Transaction() { }
	
	public Transaction (int transactionId, String sourceAccount, String destinationAccount, String transactionDate, Double amountMoney) {	
		super();
		this.transactionId = transactionId;
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.transactionDate = transactionDate;
		this.amountMoney = amountMoney;
	}
	
	@Override
	public String toString() {
		return ("[TransactionId=" + transactionId + ", SourceAccount=" + sourceAccount
				+ ", DestinationAccount=" + destinationAccount + ", TransactionDate="
				+ transactionDate + ", AmountMoney=" + amountMoney + "]");
	}

	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(Double amountMoney) {
		this.amountMoney = amountMoney;
	}

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

}
