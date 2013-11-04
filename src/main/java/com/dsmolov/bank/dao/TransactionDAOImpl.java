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
import com.dsmolov.bank.entities.Status;
import com.dsmolov.bank.entities.Transaction;
import com.dsmolov.bank.entities.User;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createTransaction(Transaction bankTransaction, Account accountDetected) {
		bankTransaction.setAccount(accountDetected);
		bankTransaction.setSourceAccount(accountDetected.getAccountNumber());
		sessionFactory.getCurrentSession().save(bankTransaction);
	}

	@Override
	public List<Transaction> getTransactions(String value, int index) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Transaction.class);
		//String field = "sourceAccount";
		//criteria.add( Restrictions.eq(field, value) );	//value is currentUser.getAccount().getAccountNumber();
				criteria.add( Restrictions.eq("sourceAccount", value) );
		criteria.addOrder(Order.desc("transactionId") );
		criteria.setFirstResult((index - 1) * 10);
		criteria.setMaxResults(10);
		@SuppressWarnings("unchecked")
		List<Transaction> trs = (List<Transaction>) criteria.list(); 
		return trs;
	}

	@Override
	@Transactional
	public Integer getTrCount(String valueTrCount) {		// it is currentUser.getAccount().getAccountNumber();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Transaction.class);
		//String fieldTrCount = "sourceAccount";//rewrite
		criteria.add( Restrictions.eq("sourceAccount", valueTrCount) );
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public User getUserByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class).add(
									Restrictions.eq("login", login));
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) criteria.list();
		if(users!=null && users.size()!=0) {
			return users.get(0);
		}
		return null;
	}
	
	//	VALIDATION
	/*public Account getAccountForFurtherValidation(String doesAccountExistString) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountNumber", doesAccountExistString));
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list();
		if(accounts!=null && accounts.size()!=0) {
			System.out.println("Acc found!!!");
			//session.close();
			return accounts.get(0);
		}
		System.out.println("Nope. Nothings was found.");
		session.close();
		return null;
	}*/
	
	@Override
	public boolean doesAccountExist(String destAccValid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountNumber", destAccValid));
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list();
		if(accounts!=null && accounts.size()!=0) {
			System.out.println("Acc found!!!");
			return true;
		}
		System.out.println("Acc NOT found!!!");
		return false;
	}

	@Override
	public boolean isAccountActive(String destAccValid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountNumber", destAccValid));
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list();
		if(accounts!=null && accounts.size()!=0) {
			if (accounts.get(0).getStatus() == Status.ACTIVE) {
				System.out.println("It is ACTIVE!");
				return true;
			}
		}
		System.out.println("Nope. It is not ACTIVE.");
		return false;
	}

	@Override
	public boolean isAccountTheSame(String destAccValid, String areTheyTheSame) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("accountNumber", destAccValid));
		@SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>) criteria.list();
		if(accounts!=null && accounts.size()!=0) {
			if (accounts.get(0).getAccountNumber().equals(areTheyTheSame)) {
				System.out.println("THE ACCOUNTS' NUMBERS ARE THE SAME !!!");
				return false;
			}
		}
		System.out.println("Bingo !");
		return true;
	}

}
