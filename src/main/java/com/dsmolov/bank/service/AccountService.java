package com.dsmolov.bank.service;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;

public interface AccountService {
	
	public List<Account> getAllAccounts (int index);
	
	public List<Transaction> getTransactions(Model model, Principal principal);
     
	public Integer getAccCount();

	public Account getFullInfoByAccountId(int accId);
	
}