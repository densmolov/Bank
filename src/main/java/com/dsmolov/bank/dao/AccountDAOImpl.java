package com.dsmolov.bank.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dsmolov.bank.entities.Account;
import com.dsmolov.bank.entities.Transaction;

@Repository
public class AccountDAOImpl implements AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

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

	@Override
    public Account getFullInfoByAccountId(int accId) {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Account.class).add( Restrictions.eq("accountId", accId) );
            @SuppressWarnings("unchecked")
            List<Account> accounts = (List<Account>) criteria.list();
            if(accounts!=null && accounts.size()!=0) {
            	/*        REMOVE        BELOW */
                    System.out.println("                        Status : " + accounts.get(0).getStatus());
                    System.out.println("                        Number : " + accounts.get(0).getAccountNumber());
                    System.out.println("                        Owner full name : " + accounts.get(0).getUser().getUserName());
                    System.out.println("                        Amount of money available : " + accounts.get(0).getMoneyLeft());
                    Criteria criteria2 = session.createCriteria(Transaction.class).add( Restrictions.eq("sourceAccount", accounts.get(0).getAccountNumber()) );
                    criteria2.addOrder(Order.desc("transactionId") );
                    criteria2.setMaxResults(5);
                    @SuppressWarnings("unchecked")
                    List<Transaction> trs = (List<Transaction>) criteria2.list();
                    for (int i=0; i<trs.size() ; i++) {
                            System.out.println("                        " + (i+1) + ". " + trs.get(i) + ";");
                    }
               /*        REMOVE        ABOVE */
                    return accounts.get(0);
            }
            return null;
    }

}
