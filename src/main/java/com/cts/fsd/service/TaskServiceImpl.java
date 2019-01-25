package com.cts.fsd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.dao.TaskDetailsDao;
import com.cts.fsd.domain.TaskDetails;

import javassist.NotFoundException;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskDetailsDao taskDetailsDao;

	public void addTask(TaskDetails entity) {
		taskDetailsDao.addTask(entity);
	}

	public void updateTask(TaskDetails entity) {
		taskDetailsDao.updateTask(entity);
	}

	public TaskDetails searchTask(Integer taskId) {
		return taskDetailsDao.searchTask(taskId);
	}

	public void deleteTask(Integer taskId) throws NotFoundException {
		TaskDetails task = taskDetailsDao.searchTask(taskId);
		if (task == null) {
			throw new NotFoundException("Cannot find task with " + taskId);
		}
		taskDetailsDao.deleteTask(task);
	}

	public List<TaskDetails> getAllTasks() {
		return taskDetailsDao.getAllTasks();

	}

}
