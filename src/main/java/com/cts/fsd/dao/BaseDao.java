package com.cts.fsd.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session openSession() {
		return openSessionwithTransaction();
	}

	public Session openSessionwithTransaction() {
		Session newSession = sessionFactory.openSession();
		newSession.beginTransaction();
		return newSession;
	}

	public void closeSession(Session session) {
		session.flush();
		session.clear();
		session.close();
	}

	public void closeSessionwithTransaction(Session session) {
		session.getTransaction().commit();
		session.close();
	}

}
