package com.cts.fsd.service;

import java.util.List;

import com.cts.fsd.domain.TaskDetails;

import javassist.NotFoundException;

public interface TaskService {

	void addTask(TaskDetails task);

	void updateTask(TaskDetails task);

	TaskDetails searchTask(long taskId);

	void deleteTask(long taskId) throws NotFoundException;

	List<TaskDetails> getAllTasks();

}
