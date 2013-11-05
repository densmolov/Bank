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

	@Transactional
	public List<Transaction> getTransactions(@RequestParam int index, Model model, Principal principal) {
		String value = getCurrentUser(model, principal).getAccount().getAccountNumber();
		return transactionDAO.getTransactions(value, index);
	}
		 
	@Transactional
	public Integer getTrCount(Model model, Principal principal) {
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
	
	@Transactional
	public boolean isTransactionValid (Transaction bankTransaction, Model model, Principal principal) {
		String destAccValid = bankTransaction.getDestinationAccount();
		Account accountDetected = getCurrentUser(model, principal).getAccount();
		String areTheyTheSame = accountDetected.getAccountNumber();
		double AmountOnTheAcc = accountDetected.getMoneyLeft();
		double amountValid = bankTransaction.getAmountMoney();
		if (transactionDAO.doesAccountExist(destAccValid) &&
			transactionDAO.isAccountTheSame(destAccValid, areTheyTheSame) &&
			transactionDAO.isAccountActive(destAccValid) &&
			transactionDAO.isAmountPositive(amountValid) &&
			transactionDAO.isAmountAvailable(amountValid, AmountOnTheAcc) ) {
			return true;
		} else {
			return false;
		}
	}

}
