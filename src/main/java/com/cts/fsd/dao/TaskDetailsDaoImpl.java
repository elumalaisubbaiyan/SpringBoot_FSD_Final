package com.cts.fsd.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cts.fsd.domain.TaskDetails;

@Repository
public class TaskDetailsDaoImpl extends BaseDao implements TaskDetailsDao {

	private Logger log = LoggerFactory.getLogger(TaskDetailsDaoImpl.class);

	public void addTask(TaskDetails task) {
		openCurrentSessionwithTransaction();
		int savedTaskId = (Integer) getCurrentSession().save(task);
		closeCurrentSessionwithTransaction();
		log.info("Retrieved task id " + savedTaskId + " after successfully added task " + task);
		// If the parent is self, then update the record again
		if (savedTaskId != 0 && ("Self").equalsIgnoreCase(task.getParentTask())) {
			log.info("Update parent task id for task id " + savedTaskId);
			task.setParentTaskId(savedTaskId);
			task.setParentTask(task.getTask());
			updateTask(task);
		}

	}

	public void updateTask(TaskDetails task) {
		openCurrentSessionwithTransaction();
		getCurrentSession().update(task);
		closeCurrentSessionwithTransaction();
	}

	public TaskDetails searchTask(long taskId) {
		Long longBookId = Long.valueOf(taskId);
		openCurrentSession();
		TaskDetails task = (TaskDetails) getCurrentSession().get(TaskDetails.class, longBookId);
		closeCurrentSession();
		return task;

	}

	public void deleteTask(TaskDetails task) {
		openCurrentSessionwithTransaction();
		getCurrentSession().delete(task);
		closeCurrentSessionwithTransaction();
	}

	@SuppressWarnings("unchecked")
	public List<TaskDetails> getAllTasks() {
		openCurrentSession();
		List<TaskDetails> tasks = (List<TaskDetails>) getCurrentSession().createCriteria(TaskDetails.class).list();
		closeCurrentSession();
		return tasks;
	}

}
