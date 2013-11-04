package com.dsmolov.bank.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dsmolov.bank.entities.Account;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*-@Override
	public Account getAccountByNumber(String accountNumber) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class).add( Restrictions.eq("accountNumber", accountNumber) );
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list();
		if(accounts!=null && accounts.size()!=0) {
			return accounts.get(0);
		}
		return null;
	}*/

	@Override
	public List<Account> getAllAccounts(int index) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class).addOrder(Order.asc("accountId") );
		criteria.setFirstResult((index - 1) * 10);
		criteria.setMaxResults(10);	
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list(); 
		return accounts;
	}

	@Override
	@Transactional
	public Integer getAccCount() {	
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.createCriteria(Account.class).setProjection(Projections.rowCount()).uniqueResult();
	}

}
