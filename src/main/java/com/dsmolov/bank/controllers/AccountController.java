package com.dsmolov.bank.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.service.AccountService;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/employee/accounts", method = RequestMethod.GET)
	public @ResponseBody
	List<Account> list(@RequestParam int index) {
		return accountService.getAllAccounts(index);
	}
	
	@ResponseBody
	@RequestMapping(value = "/employee/transactions", method = RequestMethod.GET)
    public List<Transaction> list(@RequestParam Model model, Principal principal) {
            return accountService.getTransactions(model, principal);
    }
		
	@RequestMapping(value = "/employee/getAccCount", method = RequestMethod.GET)
	public @ResponseBody
	Integer getAccCount() {
		return accountService.getAccCount();
	}
	
	@RequestMapping(value = "/employee/accounts/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Account getFullInfoByAccountId(@PathVariable("id") int accId) {
            return accountService.getFullInfoByAccountId(accId);
    }

}