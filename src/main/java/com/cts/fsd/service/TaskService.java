package com.cts.fsd.service;

import java.util.List;

import com.cts.fsd.domain.TaskDetails;

public interface TaskService {

	void addTask(TaskDetails task);

	void updateTask(TaskDetails task);

	TaskDetails searchTask(Integer taskId);

	// void deleteTask(Integer taskId) throws NotFoundException;

	List<TaskDetails> getAllTasks();

	//List<TaskDetails> getParentTasks();

	List<TaskDetails> getTasksByProject(int projectId);

}
