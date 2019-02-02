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

	protected void saveData(Object data) {
		Session session = openSessionwithTransaction();
		session.flush();
		session.save(data);
		closeSessionwithTransaction(session);
	}

	protected void updateData(Object data) {
		Session session = openSessionwithTransaction();
		session.flush();
		session.update(data);
		closeSessionwithTransaction(session);
	}

	protected Object getDataById(Integer id, Class<?> outputClass) {
		Session session = openSession();
		session.clear();
		Object resultObject = session.get(outputClass, id);
		closeSession(session);
		return resultObject;
	}

	protected void deleteData(Object data) {
		Session session = openSessionwithTransaction();
		session.delete(data);
		session.flush();
		closeSessionwithTransaction(session);
	}

}
