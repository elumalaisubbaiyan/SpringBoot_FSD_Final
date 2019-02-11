package com.cts.fsd.test.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cts.fsd.dao.ProjectDao;
import com.cts.fsd.dao.ProjectDaoImpl;
import com.cts.fsd.mvc.controller.ProjectsController;
import com.cts.fsd.service.ProjectService;
import com.cts.fsd.service.ProjectServiceImpl;

@Configuration
@ComponentScan(basePackages = { "com.cts.fsd.mvc.controller" }, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { ProjectsController.class }) })
@EnableWebMvc
public class ProjectControllerTestConfiguration extends BaseControllerTestConfiguration {

	@Bean(name = "projectDao")
	public ProjectDao projectDao() {
		ProjectDao projectDao = mock(ProjectDaoImpl.class);
		return projectDao;
	}

	@Bean(name = "projectService")
	public ProjectService projectService() {
		ProjectDao projectDao = mock(ProjectDaoImpl.class);
		ProjectService projectService = mock(ProjectServiceImpl.class);
		ReflectionTestUtils.setField(projectService, "projectDao", projectDao);
		return projectService;
	}

}
