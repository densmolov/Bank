package com.dsmolov.bank.controllers;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsmolov.bank.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginPage(Locale locale, Model model, HttpServletResponse response, @RequestParam(required=false) Integer error) {
		if(error!=null) {
			response.setHeader("message", "Incorrect login or password!");
		}
		return "forward:/pages/login.html";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_EMPLOYEE")) {
			return "redirect:/employee";
		}
		if (request.isUserInRole("ROLE_CLIENT")) {
			return "redirect:/client";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String employee(Locale locale, Model model) {
		return "forward:/pages/employee.html";
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String client(Model model, Principal principal) {
		return "forward:/pages/client.html";
	}


}
