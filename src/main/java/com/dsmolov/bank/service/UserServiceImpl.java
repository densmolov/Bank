package com.dsmolov.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsmolov.bank.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {
	
	 @Autowired
	 private UserDAO userDAO;

	 /*@Transactional
 	 public User getUserById(int userId) {			
		  return userDAO.getUserById(userId);
	 }*/

	 /*@Transactional
	 public User getUserByP(String password) {
		  return userDAO.getUserByP(password);
	 }*/
	 
	 /*@Transactional
	 public User getUserByLogin(String login) {
		  return userDAO.getUserByLogin(login);
	 }*/
}
