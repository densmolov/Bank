package com.dsmolov.bank.service;

import java.util.List;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

public interface TransactionService {
	
	public void createTransaction(Transaction bankTransaction, Account accountDetected);
	
	public List<Transaction> getTransactions(String value, int index);

	public Integer getTrCount(String valueTrCount);

	public User getUserByLogin(String name);
	
	public boolean doesAccountExist(String destAccValid);

	public boolean isAccountActive(String destAccValid);

	public boolean isAccountTheSame(String destAccValid, String areTheyTheSame);
	
}
