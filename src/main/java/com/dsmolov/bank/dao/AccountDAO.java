package com.dsmolov.bank.dao;

import java.util.List;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

public interface AccountDAO {
	  
	public List<Account> getAllAccounts(int index);
	
	public List<Transaction> getTransactions(String value);
	
	public User getUserByLogin(String login);
	   
	public Integer getAccCount();

	public Account getFullInfoByAccountId(int accId);

}