package com.cts.fsd.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cts.fsd.domain.TaskDetails;
import com.cts.fsd.domain.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	// private Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	public void addUser(User user) {
		saveData(user);
	}

	public void updateUser(User user) {
		updateData(user);
	}

	public User searchUser(Integer userId) {
		User user = null;
		Object result = getDataById(userId, TaskDetails.class);
		if (result != null) {
			user = (User) result;
		}
		return user;
	}

	public void deleteUser(User user) {
		deleteData(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Session session = openSession();
		session.clear();
		List<User> users = (List<User>) session.createCriteria(User.class).list();
		closeSession(session);
		return users;
	}

}
