package com.dsmolov.bank.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	/*@Override
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
	}*/
	
}