package com.dsmolov.bank.service;

import java.util.List;

import com.dsmolov.bank.entities.Account;

public interface AccountService {
	
	//public Account getAccountByNumber(String accountNumber);
	
	public List<Account> getAllAccounts (int index);
     
	public Integer getAccCount();
}
