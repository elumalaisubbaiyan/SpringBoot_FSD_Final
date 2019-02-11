package com.cts.fsd.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.fsd.dao.UserDao;
import com.cts.fsd.domain.User;
import com.cts.fsd.service.UserServiceImpl;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class UserServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	@Before
	public void setupMock() {
		userDao = mock(UserDao.class);
		userService = mock(UserServiceImpl.class);
		ReflectionTestUtils.setField(userService, "userDao", userDao);
	}

	@Test
	public void testAddUser() throws Exception {
		User user = new User();
		user.setUserId(1);
		doNothing().when(userDao).addUser(user);
		doCallRealMethod().when(userService).addUser(user);
		userService.addUser(user);
		verify(userDao, times(1)).addUser(user);
	}

	@Test
	public void testUpdateUser() throws Exception {
		doNothing().when(userDao).updateUser(any(User.class));
		doCallRealMethod().when(userService).updateUser(any(User.class));
		userService.updateUser(new User());
		verify(userDao, times(1)).updateUser(any(User.class));
	}

	@Test
	public void testDeleteUser() throws Exception {
		int userIdToDelete = 100;
		User user = new User();
		user.setUserId(userIdToDelete);
		when(userDao.searchUser(userIdToDelete)).thenReturn(user);
		doNothing().when(userDao).deleteUser(user);
		doCallRealMethod().when(userService).deleteUser(userIdToDelete);
		userService.deleteUser(userIdToDelete);
		verify(userDao, times(1)).deleteUser(user);
	}

	@Test(expected = NotFoundException.class)
	public void testDeleteUserNotFoundException() throws Exception {
		int userIdToDelete = 100;
		when(userDao.searchUser(userIdToDelete)).thenReturn(null);
		doNothing().when(userDao).deleteUser(any(User.class));
		doCallRealMethod().when(userService).deleteUser(userIdToDelete);
		userService.deleteUser(userIdToDelete);
	}

	@Test
	public void testSearchUser() throws Exception {
		int userIdToService = 10;
		User user = new User();
		user.setUserId(userIdToService);
		when(userDao.searchUser(userIdToService)).thenReturn(user);
		doCallRealMethod().when(userService).searchUser(userIdToService);
		User searchedUser = userService.searchUser(userIdToService);
		assertEquals(userIdToService, searchedUser.getUserId());
	}

	@Test
	public void testGetAllUser() throws Exception {
		List<User> mockedUserList = new ArrayList<>();
		mockedUserList.add(new User());
		when(userDao.getAllUsers()).thenReturn(mockedUserList);
		doCallRealMethod().when(userService).getAllUsers();
		List<User> allUsers = userService.getAllUsers();
		assertEquals(allUsers.size(), mockedUserList.size());
		assertThat(allUsers, is(mockedUserList));
	}

}