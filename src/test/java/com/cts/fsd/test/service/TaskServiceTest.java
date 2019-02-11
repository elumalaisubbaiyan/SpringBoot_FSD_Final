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

import com.cts.fsd.dao.TaskDetailsDao;
import com.cts.fsd.domain.TaskDetails;
import com.cts.fsd.service.TaskServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TaskServiceTest {

	@Mock
	private TaskDetailsDao taskDetailsDao;

	@InjectMocks
	private TaskServiceImpl taskService;

	@Before
	public void setupMock() {
		taskDetailsDao = mock(TaskDetailsDao.class);
		taskService = mock(TaskServiceImpl.class);
		ReflectionTestUtils.setField(taskService, "taskDetailsDao", taskDetailsDao);
	}

	@Test
	public void testAddTask() throws Exception {
		TaskDetails task = new TaskDetails();
		task.setTaskId(1);
		doNothing().when(taskDetailsDao).addTask(task);
		doCallRealMethod().when(taskService).addTask(task);
		taskService.addTask(task);
		verify(taskDetailsDao, times(1)).addTask(task);
	}

	@Test
	public void testUpdateTask() throws Exception {
		doNothing().when(taskDetailsDao).updateTask(any(TaskDetails.class));
		doCallRealMethod().when(taskService).updateTask(any(TaskDetails.class));
		taskService.updateTask(new TaskDetails());
		verify(taskDetailsDao, times(1)).updateTask(any(TaskDetails.class));
	}

//	@Test
//	public void testDeleteTask() throws Exception {
//		int taskIdToDelete = 100;
//		TaskDetails task = new TaskDetails();
//		task.setTaskId(taskIdToDelete);
//		when(taskDetailsDao.searchTask(taskIdToDelete)).thenReturn(task);
//		doNothing().when(taskDetailsDao).deleteTask(task);
//		doCallRealMethod().when(taskService).deleteTask(taskIdToDelete);
//		taskService.deleteTask(taskIdToDelete);
//		verify(taskDetailsDao, times(1)).deleteTask(task);
//	}
//
//	@Test(expected = NotFoundException.class)
//	public void testDeleteTaskNotFoundException() throws Exception {
//		int taskIdToDelete = 100;
//		when(taskDetailsDao.searchTask(taskIdToDelete)).thenReturn(null);
//		doNothing().when(taskDetailsDao).deleteTask(any(TaskDetails.class));
//		doCallRealMethod().when(taskService).deleteTask(taskIdToDelete);
//		taskService.deleteTask(taskIdToDelete);
//	}

	@Test
	public void testSearchTask() throws Exception {
		int taskIdToService = 10;
		TaskDetails task = new TaskDetails();
		task.setTaskId(taskIdToService);
		when(taskDetailsDao.searchTask(taskIdToService)).thenReturn(task);
		doCallRealMethod().when(taskService).searchTask(taskIdToService);
		TaskDetails searchedTask = taskService.searchTask(taskIdToService);
		assertEquals(taskIdToService, searchedTask.getTaskId());
	}

	@Test
	public void testGetAllTask() throws Exception {
		List<TaskDetails> mockedTaskList = new ArrayList<>();
		mockedTaskList.add(new TaskDetails());
		when(taskDetailsDao.getAllTasks()).thenReturn(mockedTaskList);
		doCallRealMethod().when(taskService).getAllTasks();
		List<TaskDetails> allTasks = taskService.getAllTasks();
		assertEquals(allTasks.size(), mockedTaskList.size());
		assertThat(allTasks, is(mockedTaskList));
	}

	@Test
	public void testGetTasksByProject() {
		List<TaskDetails> mockedTaskList = new ArrayList<>();
		mockedTaskList.add(new TaskDetails());
		when(taskDetailsDao.getTasksByProject(1)).thenReturn(mockedTaskList);
		doCallRealMethod().when(taskService).getTasksByProject(1);
		List<TaskDetails> tasksByProject = taskService.getTasksByProject(1);
		assertEquals(tasksByProject.size(), mockedTaskList.size());
		assertThat(tasksByProject, is(mockedTaskList));
	}

}