package com.cts.fsd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.dao.UserDao;
import com.cts.fsd.domain.User;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public void addUser(User entity) {
		userDao.addUser(entity);
	}

	public void updateUser(User entity) {
		userDao.updateUser(entity);
	}

	public User searchUser(Integer taskId) {
		return userDao.searchUser(taskId);
	}

	public void deleteUser(Integer taskId) throws NotFoundException {
		User task = userDao.searchUser(taskId);
		if (task == null) {
			throw new NotFoundException("Cannot find task with " + taskId);
		}
		userDao.deleteUser(task);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

}
