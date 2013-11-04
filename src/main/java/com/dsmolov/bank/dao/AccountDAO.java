package com.dsmolov.bank.dao;

import java.util.List;

import com.dsmolov.bank.entities.Account;

public interface AccountDAO {
	
	//public Account getAccountByNumber(String accountNumber);
	  
	public List<Account> getAllAccounts(int index);
	   
	public Integer getAccCount();

}
