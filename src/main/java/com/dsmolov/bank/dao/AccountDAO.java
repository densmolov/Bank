package com.dsmolov.bank.dao;

import java.util.List;

import com.dsmolov.bank.entities.Account;

public interface AccountDAO {
	  
	public List<Account> getAllAccounts(int index);
	   
	public Integer getAccCount();

	public Account getFullInfoByAccountNumber(String accountNumber);

}