package com.cts.fsd.dao;

import java.util.List;

import com.cts.fsd.domain.TaskDetails;

public interface TaskDetailsDao {

	void addTask(TaskDetails task);

	void updateTask(TaskDetails task);

	TaskDetails searchTask(Integer taskId);

	//void deleteTask(TaskDetails task);

	List<TaskDetails> getAllTasks();

	List<TaskDetails> getParentTasks();

	List<TaskDetails> getTasksByProject(int projectId);

}
