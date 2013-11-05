package com.dsmolov.bank.service;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;

import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

public interface TransactionService {
	
	//public void createTransaction(Transaction bankTransaction, Account accountDetected);
	public void createTransaction(Transaction bankTransaction, Model model,	Principal principal);
	
	//public List<Transaction> getTransactions(String value, int index);
	public List<Transaction> getTransactions(int index, Model model, Principal principal);

	//public Integer getTrCount(String valueTrCount);
	public Integer getTrCount(Model model, Principal principal);

	//public User getUserByLogin(String name);
	public User getCurrentUser(Model model, Principal principal);
	
	public boolean doesAccountExist(String destAccValid);

	public boolean isAccountActive(String destAccValid);

	public boolean isAccountTheSame(String destAccValid, String areTheyTheSame);

	public boolean isAmountPositive(double amountValid);

	public boolean isAmountAvailable(double amountValid, double amountOnTheAcc);
	
}
