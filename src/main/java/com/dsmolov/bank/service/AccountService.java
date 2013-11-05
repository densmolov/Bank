package com.dsmolov.bank.service;

import java.util.List;

import com.dsmolov.bank.entities.Account;

public interface AccountService {
	
	public List<Account> getAllAccounts (int index);
     
	public Integer getAccCount();

	public Account getFullInfoByAccountNumber(String accountNumber);

}