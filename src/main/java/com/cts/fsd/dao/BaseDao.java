package com.cts.fsd.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao {

	private Session currentSession;

	protected Transaction currentTransaction;

	@Autowired
	private SessionFactory sessionFactory;

	public Session openCurrentSession() {
		currentSession = sessionFactory.openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = sessionFactory.openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}

	public void closeCurrentSession() {
		currentSession.close();
	}

	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}

	public Session getCurrentSession() {
		return currentSession;
	}

}
