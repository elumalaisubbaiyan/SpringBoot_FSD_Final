package com.cts.fsd.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
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

import com.cts.fsd.domain.Project;
import com.cts.fsd.service.ProjectService;
import com.cts.fsd.test.config.ProjectControllerTestConfiguration;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ProjectControllerTestConfiguration.class })
@WebAppConfiguration
public class ProjectControllerTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	private Project projectToBeAddedForException;

	public ProjectControllerTest() {
		projectToBeAddedForException = new Project();
		projectToBeAddedForException.setProjectId(-5);
		projectToBeAddedForException.setProjectName("Exception Validation");
		projectToBeAddedForException.setStartDate(new Date());
		projectToBeAddedForException.setEndDate(new Date());
		projectToBeAddedForException.setPriority(1);
	}

	@Before
	public void setup() throws NotFoundException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		List<Project> allProjects = new ArrayList<>();
		allProjects.add(new Project());
		ProjectService projectService = (ProjectService) wac.getBean("projectService");
		when(projectService.getAllProjects()).thenReturn(allProjects);
		when(projectService.searchProject(any(Integer.class))).thenReturn(new Project());
		when(projectService.searchProject(0)).thenReturn(null);
		doThrow(new RuntimeException()).when(projectService).addProject(projectToBeAddedForException);
		doThrow(new RuntimeException()).when(projectService).updateProject(projectToBeAddedForException);
		// doThrow(new NotFoundException("")).when(projectService).deleteProject(0);
	}

	@After
	public void resetProjectService() {

	}

	// Response Code: 200 - Valid response without validation errors for current
	// date
	@Test
	public void testgetAllProjectsOk() throws Exception {
		this.mockMvc.perform(get("/projects").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testgetAllProjectsNotFound() throws Exception {
		ProjectService projectService = (ProjectService) wac.getBean("projectService");
		when(projectService.getAllProjects()).thenReturn(null);
		this.mockMvc.perform(get("/projects").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testgetProjectOk() throws Exception {
		this.mockMvc.perform(get("/projects/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testgetProjectNotFound() throws Exception {
		this.mockMvc.perform(get("/projects/0").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testAddProject() throws Exception {
		this.mockMvc.perform(
				post("/projects").contentType(MediaType.APPLICATION_JSON_VALUE).content(addProjectRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddProjectInternalServerError() throws Exception {
		this.mockMvc
				.perform(post("/projects").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addProjectRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	public void testUpdateProjectOk() throws Exception {
		this.mockMvc.perform(
				put("/projects/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(addProjectRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateProjectInternalError() throws Exception {
		this.mockMvc
				.perform(put("/projects/-5").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addProjectRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	private String addProjectRequestJson() {
		return "{ \"projectId\": \"2\", \"projectName\":\"Project 1\"}";
	}

	private String addProjectRequestExceptionJson() {
		return "{ \"projectId\": \"-5\",  \"projectName\":\"Validate Exception\"}";
	}

}
