package com.dsmolov.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsmolov.bank.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	
	 @Autowired
	 private UserDAO userDAO;
	 
}
