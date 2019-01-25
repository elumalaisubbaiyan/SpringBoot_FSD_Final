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

import com.cts.fsd.dao.TaskDetailsDao;
import com.cts.fsd.domain.TaskDetails;

public class TaskDaoTest extends EntityDaoTest {
	@Autowired
	private TaskDetailsDao taskDao;

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Task.xml"));
		return dataSet;
	}

	@Test
	public void testSearchTask() {
		TaskDetails searchedTask = taskDao.searchTask(1);
		Assert.assertNotNull(searchedTask);
		Assert.assertTrue(searchedTask.getTask().equalsIgnoreCase("Junit Testing for Controller"));
		Assert.assertNull(taskDao.searchTask(4));
	}

	@Test
	public void testAddTask() {
		TaskDetails taskToBeAdded = new TaskDetails();
		taskToBeAdded.setTaskId(4);
		taskToBeAdded.setTask("New Task");
		taskToBeAdded.setParentTaskId(3);
		taskDao.addTask(taskToBeAdded);
		TaskDetails searchedTask = taskDao.searchTask(4);
		Assert.assertNotNull(searchedTask);
		Assert.assertEquals(taskToBeAdded.getTaskId(), searchedTask.getTaskId());
		Assert.assertEquals(taskToBeAdded.getTask(), searchedTask.getTask());
		Assert.assertEquals(taskToBeAdded.getParentTaskId(), searchedTask.getParentTaskId());
		assertThat(taskDao.getAllTasks().size(), is(4));
	}

	@Test
	public void testAddTaskWithSameParent() {
		TaskDetails taskToBeAdded = new TaskDetails();
		taskToBeAdded.setTaskId(5);
		taskToBeAdded.setTask("New Task");
		taskToBeAdded.setParentTask("Self");
		taskDao.addTask(taskToBeAdded);
		TaskDetails searchedTask = taskDao.searchTask(5);
		Assert.assertEquals(taskToBeAdded.getTaskId(), searchedTask.getTaskId());
		Assert.assertEquals(taskToBeAdded.getTask(), searchedTask.getTask());
		Assert.assertEquals(Integer.valueOf(taskToBeAdded.getTaskId()), searchedTask.getParentTaskId());
		Assert.assertEquals(taskToBeAdded.getTask(), searchedTask.getParentTask());
		assertThat(taskDao.getAllTasks().size(), is(4));
	}

	@Test
	public void testDeleteTask() {
		int taskIdToBeDeleted = 3;
		TaskDetails taskToBeDeleted = taskDao.searchTask(taskIdToBeDeleted);
		taskDao.deleteTask(taskToBeDeleted);
		Assert.assertNull(taskDao.searchTask(taskIdToBeDeleted));
		assertThat(taskDao.getAllTasks().size(), is(2));
	}

	@Test
	public void testAllTask() {
		List<TaskDetails> allTasks = taskDao.getAllTasks();
		Assert.assertNotNull(allTasks);
		assertThat(allTasks.size(), is(3));
		TaskDetails searchedTask = taskDao.searchTask(2);
		assertThat(allTasks, hasItems(searchedTask));
	}

	@Test
	public void testUpdateTask() {
		TaskDetails searchedTask = taskDao.searchTask(2);
		searchedTask.setTask("Updated Junit Testing");
		taskDao.updateTask(searchedTask);
		TaskDetails updatedsearchedTask = taskDao.searchTask(2);
		assertEquals("Updated Junit Testing", updatedsearchedTask.getTask());
	}

	@Test(expected = StaleStateException.class)
	public void testExceptionForUpdateTask() {
		TaskDetails newTask = new TaskDetails();
		newTask.setTaskId(4);
		taskDao.updateTask(newTask);
	}

}
