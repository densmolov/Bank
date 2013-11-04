package com.dsmolov.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsmolov.bank.dao.TransactionDAO;
import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Transactional
	public void createTransaction(Transaction bankTransaction, Account accountDetected) {
		transactionDAO.createTransaction(bankTransaction, accountDetected);
	}

	@Transactional
	public List<Transaction> getTransactions(String value, int index) {
		return transactionDAO.getTransactions(value, index);
	}

	 @Transactional
	 public Integer getTrCount(String valueTrCount) {	
		  return transactionDAO.getTrCount(valueTrCount);
	 }

	 @Transactional
	 public User getUserByLogin(String login) {
		  return transactionDAO.getUserByLogin(login);
	 }
	 
	 @Transactional
	 public boolean doesAccountExist(String destAccValid) {
		return transactionDAO.doesAccountExist(destAccValid);
	 }

	@Transactional
	public boolean isAccountActive(String destAccValid) {
		 return transactionDAO.isAccountActive(destAccValid);
	}

	@Transactional
	public boolean isAccountTheSame(String destAccValid, String areTheyTheSame) {
		return transactionDAO.isAccountTheSame(destAccValid, areTheyTheSame);
	}

	@Transactional
	public boolean isAmountPositive(double amountValid) {
		return transactionDAO.isAmountPositive(amountValid);
	}

	@Transactional
	public boolean isAmountAvailable(double amountValid, double amountOnTheAcc) {
		return transactionDAO.isAmountAvailable(amountValid, amountOnTheAcc);
	}

}
