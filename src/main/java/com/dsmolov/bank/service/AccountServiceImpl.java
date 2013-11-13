package com.dsmolov.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsmolov.bank.dao.AccountDAO;
import com.dsmolov.bank.entities.Account;

@Service
public class AccountServiceImpl implements AccountService {
	
	 @Autowired
	 private AccountDAO accountDAO;

	@Transactional
	public List<Account> getAllAccounts(int index) {
		return accountDAO.getAllAccounts(index);
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
