package com.dsmolov.bank.dao;

import java.util.List;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

public interface TransactionDAO {
	
	public void createTransaction(Transaction bankTransaction, Account accountDetected);
	  
	public List<Transaction> getTransactions(String value, int index);

	public User getUserByLogin(String login);

	public Integer getTrCount(String valueTrCount);

	public boolean doesAccountExist(String destAccValid);

	public boolean isAccountActive(String destAccValid);

	public boolean isAccountTheSame(String destAccValid, String areTheyTheSame);

	public boolean isAmountAvailable(double amountValid, double amountOnTheAcc);

	public boolean isAmountPositive(double amountValid);
	
}