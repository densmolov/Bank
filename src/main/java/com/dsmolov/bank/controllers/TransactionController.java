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
                return transactionService.getTransactions(index, model, principal);
        }
        
        public User getCurrentUser(Model model, Principal principal) {
                return transactionService.getCurrentUser(model, principal);
        }
                                                
        @RequestMapping(value = "/client/create", method = RequestMethod.POST)
        public String createTransaction(@RequestBody Transaction bankTransaction, Model model, Principal principal) {
                transactionService.createTransaction(bankTransaction, model, principal);
                return "redirect:/client";
        }

        @RequestMapping(value = "/client/getTrCount", method = RequestMethod.GET)
        public @ResponseBody
        Integer getTrCount(Model model, Principal principal) {
                return transactionService.getTrCount(model, principal);
        }
        
        @RequestMapping(value = "/client/getUser", method = RequestMethod.GET)
        public @ResponseBody
        User getUser(Model model, Principal principal) {
                return getCurrentUser(model, principal);
        }
        @RequestMapping(value = "/client/getName", method = RequestMethod.GET)	// it's better to return an object - currentUser
        //and then convert it to JSON to parse on the client-side
        public @ResponseBody
        String getName(Model model, Principal principal) {
                String stringName = getCurrentUser(model, principal).getUserName();
                return stringName;
        }
        @RequestMapping(value = "/client/getSum", method = RequestMethod.GET)
        public @ResponseBody
        Double getSum(Model model, Principal principal) {
                return getCurrentUser(model, principal).getAccount().getMoneyLeft();
        }
        
}