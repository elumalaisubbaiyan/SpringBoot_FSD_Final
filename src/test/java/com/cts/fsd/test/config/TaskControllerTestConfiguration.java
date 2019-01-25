package com.cts.fsd.test.config;

import static org.mockito.Mockito.mock;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cts.fsd.dao.TaskDetailsDao;
import com.cts.fsd.dao.TaskDetailsDaoImpl;
import com.cts.fsd.mvc.controller.TaskController;
import com.cts.fsd.service.TaskService;
import com.cts.fsd.service.TaskServiceImpl;

@Configuration
@ComponentScan(basePackages = { "com.cts.fsd.mvc.controller" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { TaskController.class }) })
@EnableWebMvc
public class TaskControllerTestConfiguration {

	@Bean(name = "taskDetailsDao")
	public TaskDetailsDao taskDetailsDao() {
		TaskDetailsDao taskDetailsDao = mock(TaskDetailsDaoImpl.class);
		return taskDetailsDao;
	}

	@Bean(name = "taskService")
	public TaskService taskService() {
		TaskDetailsDao taskDetailsDao = mock(TaskDetailsDaoImpl.class);
		TaskService taskService = mock(TaskServiceImpl.class);
		ReflectionTestUtils.setField(taskService, "taskDetailsDao", taskDetailsDao);
		return taskService;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.cts.fsd.domain" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		properties.put("configLocation", "dbconfig/hibernate.cfg.xml");
		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}
