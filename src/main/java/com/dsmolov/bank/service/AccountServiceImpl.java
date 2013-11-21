package com.dsmolov.bank.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsmolov.bank.dao.AccountDAO;
import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

@Service
public class AccountServiceImpl implements AccountService {
	
	 @Autowired
	 private AccountDAO accountDAO;

	@Transactional
	public List<Account> getAllAccounts(int index) {
		return accountDAO.getAllAccounts(index);
	}
	
	@Transactional
    public List<Transaction> getTransactions(@RequestParam Model model, Principal principal) {
            String value = getCurrentUser(model, principal).getAccount().getAccountNumber();
            return accountDAO.getTransactions(value);
    }
	
	@Transactional
    public User getCurrentUser(Model model, Principal principal) {
            String name = principal.getName();
            User currentUser = accountDAO.getUserByLogin(name);
            return currentUser;
    }

	@Transactional
	public Integer getAccCount() {
		return accountDAO.getAccCount();
	}

	@Transactional
    public Account getFullInfoByAccountId(int accId) {
            return accountDAO.getFullInfoByAccountId(accId);
    }

}
