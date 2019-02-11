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

import com.cts.fsd.domain.TaskDetails;
import com.cts.fsd.service.TaskService;
import com.cts.fsd.test.config.TaskControllerTestConfiguration;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TaskControllerTestConfiguration.class })
@WebAppConfiguration
public class TaskControllerTest {

	private MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	private TaskDetails taskToBeAddedForException;

	public TaskControllerTest() {
		taskToBeAddedForException = new TaskDetails();
		taskToBeAddedForException.setTaskId(-5);
		taskToBeAddedForException.setTask("Validate Exception");
		taskToBeAddedForException.setStartDate(new Date());
		taskToBeAddedForException.setEndDate(new Date());
		taskToBeAddedForException.setPriority(1);
	}

	@Before
	public void setup() throws NotFoundException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		List<TaskDetails> allTasks = new ArrayList<>();
		allTasks.add(new TaskDetails());
		TaskService taskService = (TaskService) wac.getBean("taskService");
		when(taskService.getAllTasks()).thenReturn(allTasks);
		when(taskService.getTasksByProject(1)).thenReturn(allTasks);
		when(taskService.searchTask(any(Integer.class))).thenReturn(new TaskDetails());
		when(taskService.searchTask(0)).thenReturn(null);
		doThrow(new RuntimeException()).when(taskService).addTask(taskToBeAddedForException);
		doThrow(new RuntimeException()).when(taskService).updateTask(taskToBeAddedForException);

		// doThrow(new NotFoundException("")).when(taskService).deleteTask(0);

	}

	@After
	public void resetTaskService() {

	}

	// Response Code: 200 - Valid response without validation errors for current
	// date
	@Test
	public void testgetAllTasksOk() throws Exception {
		this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testgetAllTasksNotFound() throws Exception {
		TaskService taskService = (TaskService) wac.getBean("taskService");
		when(taskService.getAllTasks()).thenReturn(null);
		this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testGetTasksByProjectId() throws Exception {
		this.mockMvc.perform(get("/tasks/projects/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testGetTasksByProjectIdNotFound() throws Exception {
		TaskService taskService = (TaskService) wac.getBean("taskService");
		when(taskService.getTasksByProject(2)).thenReturn(null);
		this.mockMvc.perform(get("/tasks/projects/2").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testgetTaskOk() throws Exception {
		this.mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testgetTaskNotFound() throws Exception {
		this.mockMvc.perform(get("/tasks/0").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testAddTask() throws Exception {
		this.mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddTaskSameParent() throws Exception {
		this.mockMvc.perform(
				post("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJsonSameParent()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddTaskNoParent() throws Exception {
		this.mockMvc.perform(
				post("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJsonNoParent()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testAddTaskInternalServerError() throws Exception {
		this.mockMvc
				.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addTaskRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	public void testUpdateTaskOk() throws Exception {
		this.mockMvc
				.perform(put("/tasks/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJson()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateTaskSameParent() throws Exception {
		this.mockMvc.perform(
				put("/tasks/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJsonSameParent()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateTaskNoParent() throws Exception {
		this.mockMvc.perform(
				put("/tasks/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(addTaskRequestJsonNoParent()))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testUpdateTaskInternalError() throws Exception {
		this.mockMvc
				.perform(put("/tasks/-5").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(addTaskRequestExceptionJson()))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	public void testDeleteTaskOk() throws Exception {
		this.mockMvc.perform(delete("/tasks/15").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
	}

	private String addTaskRequestJson() {
		return "{ \"taskId\": \"2\", \"task\":\"Task 1\", \"projectId\": \"2\"}";
	}

	private String addTaskRequestJsonNoParent() {
		return "{ \"taskId\": \"2\", \"task\":\"Task 1\" , \"projectId\": \"1\"}";
	}

	private String addTaskRequestJsonSameParent() {
		return "{ \"taskId\": \"2\", \"task\":\"Task 1\" ,  \"projectId\": \"1\"}";
	}

	private String addTaskRequestExceptionJson() {
		return "{ \"taskId\": \"-5\",  \"projectId\": \"1\", \"task\":\"Validate Exception\"}";
	}

}
