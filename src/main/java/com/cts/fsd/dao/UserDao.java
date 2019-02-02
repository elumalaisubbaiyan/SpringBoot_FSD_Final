package com.cts.fsd.dao;

import java.util.List;

import com.cts.fsd.domain.User;

public interface UserDao {

	void addUser(User user);

	void updateUser(User user);

	User searchUser(Integer taskId);

	void deleteUser(User user);

	List<User> getAllUsers();

}
