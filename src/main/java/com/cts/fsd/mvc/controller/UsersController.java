package com.cts.fsd.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.fsd.domain.ErrorMessage;
import com.cts.fsd.domain.User;
import com.cts.fsd.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
	private static final Logger log = LoggerFactory.getLogger(UsersController.class);

	private static final String ERROR_MSG = "Exception occured processing your request. Please try again. ";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllUsers(HttpServletResponse response) {
		List<User> allUsers = userService.getAllUsers();
		if (allUsers == null || allUsers.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage("No Users are available in the system");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
		return ResponseEntity.ok(allUsers);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getUser(@PathVariable Integer userId, HttpServletResponse response) {
		User searchedTask = userService.searchUser(userId);
		if (searchedTask == null) {
			ErrorMessage errorMessage = new ErrorMessage("Could not find user with id " + userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

		}
		return ResponseEntity.ok(searchedTask);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTask(@RequestBody User user, HttpServletRequest request) {
		try {
			userService.addUser(user);
			log.info("Successfully added user {} ", user);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			log.error("Cannot add user " + user + "Exception in adding new user " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTask(@PathVariable int userId, @RequestBody User user,
			HttpServletRequest request) {
		try {
			log.info("User to be updated {} ", user);
			userService.updateUser(user);
			log.info("Successfully updated details for user id {}", user.getUserId());
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			log.error("Cannot update user " + user + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{userIdToDelete}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTask(@PathVariable Integer userIdToDelete, HttpServletRequest request) {
		try {
			userService.deleteUser(userIdToDelete);
		} catch (Exception e) {
			log.error("Cannot delete user with id " + userIdToDelete + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return ResponseEntity.ok("Successfully Deleted user with user id " + userIdToDelete);
	}

}