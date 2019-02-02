package com.cts.fsd.service;

import java.util.List;

import com.cts.fsd.domain.User;

import javassist.NotFoundException;

public interface UserService {

	void addUser(User user);

	void updateUser(User user);

	User searchUser(Integer userId);

	void deleteUser(Integer userId) throws NotFoundException;

	List<User> getAllUsers();

}
