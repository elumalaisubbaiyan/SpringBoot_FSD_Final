package com.cts.fsd.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cts.fsd.domain.User;
import com.cts.fsd.service.UserService;
import com.cts.fsd.test.config.UserControllerTestConfiguration;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserControllerTestConfiguration.class })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	private User userToBeAddedForException;

	public UserControllerTest() {
		userToBeAddedForException = new User();
		userToBeAddedForException.setUserId(-5);
		userToBeAddedForException.setFirstName("FNException");
		userToBeAddedForException.setLastName("LNException");
		userToBeAddedForException.setEmployeeId(11111);
	}

	@Before
	public void setup() throws NotFoundException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		List<User> allUsers = new ArrayList<>();
		allUsers.add(new User());
		UserService userService = (UserService) wac.getBean("userService");
		when(userService.getAllUsers()).thenReturn(allUsers);
		when(userService.searchUser(any(Integer.class))).thenReturn(new User());
		when(userService.searchUser(0)).thenReturn(null);
		doThrow(new RuntimeException()).when(userService).addUser(userToBeAddedForException);
		doThrow(new RuntimeException()).when(userService).updateUser(userToBeAddedForException);
		doThrow(new NotFoundException("")).when(userService).deleteUser(0);
	}

	@After
	public void resetUserService() {

	}

	// Response Code: 200 - Valid response without validation errors for current
	// date
	@Test
	public void testgetAllUsersOk() throws Exception {
		this.mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testgetAllUsersNotFound() throws Exception {
		UserService userService = (UserService) wac.getBean("userService");
		when(userService.getAllUsers()).thenReturn(null);
		this.mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testgetUserOk() throws Exception {
		this.mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testgetUserNotFound() throws Exception {
		this.mockMvc.perform(get("/users/0").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testAddUser() throws Exception {
		this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE).content(addUserRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddUserInternalServerError() throws Exception {
		this.mockMvc
				.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addUserRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	public void testUpdateUserOk() throws Exception {
		this.mockMvc
				.perform(put("/users/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(addUserRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateUserInternalError() throws Exception {
		this.mockMvc
				.perform(put("/users/-5").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addUserRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	public void testDeleteUserok() throws Exception {
		this.mockMvc.perform(delete("/users/15").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testDeleteException() throws Exception {
		this.mockMvc.perform(delete("/users/0").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	private String addUserRequestJson() {
		return "{ \"userId\": \"2\", \"firstName\":\"FN 1\", \"lastName\": \"LN\"}";
	}

	private String addUserRequestExceptionJson() {
		return "{ \"userId\": \"-5\", \"firstName\":\"FN 1\", \"lastName\": \"LN\"}";
	}

}
