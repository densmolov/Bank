package com.dsmolov.bank.service;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;

import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

public interface TransactionService {
	
	public void createTransaction(Transaction bankTransaction, Model model,	Principal principal);
	
	public List<Transaction> getTransactions(int index, Model model, Principal principal);

	public Integer getTrCount(Model model, Principal principal);

	public User getCurrentUser(Model model, Principal principal);
	
}
