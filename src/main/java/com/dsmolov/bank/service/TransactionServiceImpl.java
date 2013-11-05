package com.dsmolov.bank.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsmolov.bank.dao.TransactionDAO;
import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Transactional
	public void createTransaction(Transaction bankTransaction, Model model, Principal principal) {
		Account accountDetected = getCurrentUser(model, principal).getAccount();
		if (isTransactionValid(bankTransaction, model, principal)) {
			transactionDAO.createTransaction(bankTransaction, accountDetected);
		}		
	}
	//public String createTransaction(@RequestBody Transaction bankTransaction, Model model, Principal principal) {
		//User currentUser = getCurrentUser(model, principal);
		//Account accountDetected = currentUser.getAccount();
		/*if (isTransactionValid(bankTransaction, model, principal)) {
			transactionService.createTransaction(bankTransaction, accountDetected);
			return "redirect:/client";
		} else {
			return "redirect:/client";
		}
	}*/

	@Transactional
	public List<Transaction> getTransactions(@RequestParam int index, Model model, Principal principal) {
		//User currentUser = getCurrentUser(model, principal);
		String value = getCurrentUser(model, principal).getAccount().getAccountNumber();
		return transactionDAO.getTransactions(value, index);
	}
	/*public List<Transaction> list(@RequestParam int index, Model model, Principal principal) {
		User currentUser = getCurrentUser(model, principal);
		String value = currentUser.getAccount().getAccountNumber();
		return transactionService.getTransactions(value, index);
	}*/
	 
/*	 public Integer getTrCount(String valueTrCount) {	
		  return transactionDAO.getTrCount(valueTrCount);
	 }*/
	@Transactional
	public Integer getTrCount(Model model, Principal principal) {
			//User currentUser = getCurrentUser(model, principal);
			String valueTrCount = getCurrentUser(model, principal).getAccount().getAccountNumber();
			return transactionDAO.getTrCount(valueTrCount);
	}

	@Transactional
	public User getCurrentUser(Model model, Principal principal) {
			String name = principal.getName();
			model.addAttribute("userName", name);
			User currentUser = transactionDAO.getUserByLogin(name);
			return currentUser;
	}

	 /*@Transactional
	 public User getUserByLogin(String login) {
		  return transactionDAO.getUserByLogin(login);
	 }*/
	
	public boolean isTransactionValid (Transaction bankTransaction, Model model, Principal principal) {
		String destAccValid = bankTransaction.getDestinationAccount();
			User currentUser = getCurrentUser(model, principal);
			Account accountDetected = currentUser.getAccount();
			String areTheyTheSame = accountDetected.getAccountNumber();
			double AmountOnTheAcc = accountDetected.getMoneyLeft();
		double amountValid = bankTransaction.getAmountMoney();
		if (doesAccountExist(destAccValid) &&
			isAccountTheSame(destAccValid, areTheyTheSame) &&
			isAccountActive(destAccValid) &&
			isAmountPositive(amountValid) &&
			isAmountAvailable(amountValid, AmountOnTheAcc) ) {
			return true;
		} else {
			return false;
		}
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
