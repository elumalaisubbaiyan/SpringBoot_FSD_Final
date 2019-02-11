package com.cts.fsd.test.dao;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.fsd.dao.UserDao;
import com.cts.fsd.domain.User;

public class UserDaoTest extends EntityDaoTest {
	@Autowired
	private UserDao userDao;

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Task.xml"));
		return dataSet;
	}

	@Test
	public void testSearchUser() {
		User searchedUser = userDao.searchUser(1);
		Assert.assertNotNull(searchedUser);
		Assert.assertTrue(searchedUser.getFirstName().equalsIgnoreCase("Elumalai"));
		Assert.assertTrue(searchedUser.getLastName().equalsIgnoreCase("Subbaiyan"));
		Assert.assertTrue(searchedUser.getEmployeeId() == 165002);
		Assert.assertNull(userDao.searchUser(4));
	}

	@Test
	public void testAddUser() {
		User userToBeAdded = new User();
		userToBeAdded.setFirstName("DaoFirst");
		userToBeAdded.setLastName("DaoLast");
		userToBeAdded.setEmployeeId(123);
		userDao.addUser(userToBeAdded);
		User searchedUser = userDao.searchUser(4);
		Assert.assertNotNull(searchedUser);
		Assert.assertTrue(searchedUser.getFirstName().equalsIgnoreCase("DaoFirst"));
		assertThat(userDao.getAllUsers().size(), is(4));
	}

	@Test
	public void testAllUser() {
		List<User> allUsers = userDao.getAllUsers();
		Assert.assertNotNull(allUsers);
		assertThat(allUsers.size(), is(3));
		User searchedUser = userDao.searchUser(2);
		assertThat(allUsers, hasItems(searchedUser));
	}

	@Test
	public void testUpdateUser() {
		User searchedUser = userDao.searchUser(2);
		searchedUser.setFirstName("Updated FirstName");
		searchedUser.setLastName("Updated LastName");
		searchedUser.setEmployeeId(111111);
		userDao.updateUser(searchedUser);
		User updatedsearchedUser = userDao.searchUser(2);
		assertEquals("Updated FirstName", updatedsearchedUser.getFirstName());
		assertEquals("Updated LastName", updatedsearchedUser.getLastName());
		Assert.assertTrue(updatedsearchedUser.getEmployeeId() == 111111);
	}

	@Test(expected = StaleStateException.class)
	public void testExceptionForUpdateUser() {
		User newUser = new User();
		newUser.setUserId(4);
		userDao.updateUser(newUser);
	}

	@Test
	public void testDeleteUser() {
		User userToBeDeleted = userDao.searchUser(3);
		userDao.deleteUser(userToBeDeleted);

	}

}
