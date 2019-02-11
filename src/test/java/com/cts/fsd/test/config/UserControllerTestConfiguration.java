package com.cts.fsd.test.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cts.fsd.dao.UserDao;
import com.cts.fsd.dao.UserDaoImpl;
import com.cts.fsd.mvc.controller.UsersController;
import com.cts.fsd.service.UserService;
import com.cts.fsd.service.UserServiceImpl;

@Configuration
@ComponentScan(basePackages = { "com.cts.fsd.mvc.controller" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { UsersController.class }) })
@EnableWebMvc
public class UserControllerTestConfiguration extends BaseControllerTestConfiguration {

	@Bean(name = "userDao")
	public UserDao userDao() {
		UserDao userDao = mock(UserDaoImpl.class);
		return userDao;
	}

	@Bean(name = "userService")
	public UserService userService() {
		UserDao userDao = mock(UserDaoImpl.class);
		UserService userService = mock(UserServiceImpl.class);
		ReflectionTestUtils.setField(userService, "userDao", userDao);
		return userService;
	}

}
