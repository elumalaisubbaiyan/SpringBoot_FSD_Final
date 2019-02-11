package com.cts.fsd.test.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
public class TaskControllerTestConfiguration extends BaseControllerTestConfiguration {

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

}
