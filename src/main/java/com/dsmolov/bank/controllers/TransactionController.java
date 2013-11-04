package com.dsmolov.bank.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;
import com.dsmolov.bank.service.TransactionService;

@Controller
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/client/transactions", method = RequestMethod.GET)
	public @ResponseBody
	List<Transaction> list(@RequestParam int index, Model model, Principal principal) {
		User currentUser = getCurrentUser(model, principal);
		String value = currentUser.getAccount().getAccountNumber();
		return transactionService.getTransactions(value, index);
	}
	
	public User getCurrentUser(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("userName", name);
		User currentUser = transactionService.getUserByLogin(name);
		return currentUser;
	}
						
	@RequestMapping(value = "/client/create", method = RequestMethod.POST)
	public String createTransaction(@RequestBody Transaction bankTransaction, Model model, Principal principal) {
		User currentUser = getCurrentUser(model, principal);
		Account accountDetected = currentUser.getAccount();
		if (isTransactionValid(bankTransaction, model, principal)) {
			transactionService.createTransaction(bankTransaction, accountDetected);
			return "redirect:/client";
		} else {
			return "redirect:/client";
		}

	}
	
	public boolean isTransactionValid (Transaction bankTransaction, Model model, Principal principal) {
		String destAccValid = bankTransaction.getDestinationAccount();
			User currentUser = getCurrentUser(model, principal);
			Account accountDetected = currentUser.getAccount();
			String areTheyTheSame = accountDetected.getAccountNumber();
		double amountValid = bankTransaction.getAmountMoney();
		if (transactionService.doesAccountExist(destAccValid) &&
				transactionService.isAccountTheSame(destAccValid, areTheyTheSame) &&
				transactionService.isAccountActive(destAccValid)
				 ) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/client/getTrCount", method = RequestMethod.GET)
	public @ResponseBody
	Integer getTrCount(Model model, Principal principal) {
		User currentUser = getCurrentUser(model, principal);
		String valueTrCount = currentUser.getAccount().getAccountNumber();
		return transactionService.getTrCount(valueTrCount);
	}
	
}
