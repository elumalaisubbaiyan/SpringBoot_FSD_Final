package com.cts.fsd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.dao.TaskDetailsDao;
import com.cts.fsd.domain.TaskDetails;

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

	public List<TaskDetails> getAllTasks() {
		return taskDetailsDao.getAllTasks();
	}

	@Override
	public List<TaskDetails> getTasksByProject(int projectId) {
		return taskDetailsDao.getTasksByProject(projectId);
	}

}
