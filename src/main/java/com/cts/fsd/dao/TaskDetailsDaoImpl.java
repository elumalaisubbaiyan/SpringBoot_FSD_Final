package com.cts.fsd.dao;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cts.fsd.domain.TaskDetails;

@Repository
public class TaskDetailsDaoImpl extends BaseDao implements TaskDetailsDao {

	private Logger log = LoggerFactory.getLogger(TaskDetailsDaoImpl.class);

	public void addTask(TaskDetails task) {
		Session session = openSessionwithTransaction();
		int savedTaskId = (Integer) session.save(task);
		session.flush();
		closeSessionwithTransaction(session);
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
		Session session = openSessionwithTransaction();
		session.update(task);
		session.flush();
		closeSessionwithTransaction(session);
	}

	public TaskDetails searchTask(Integer taskId) {
		Session session = openSession();
		session.clear();
		TaskDetails task = (TaskDetails) session.get(TaskDetails.class, taskId);
		closeSession(session);
		return task;
	}

	public void deleteTask(TaskDetails task) {
		Session session = openSessionwithTransaction();
		session.delete(task);
		session.flush();
		closeSessionwithTransaction(session);
	}

	@SuppressWarnings("unchecked")
	public List<TaskDetails> getAllTasks() {
		Session session = openSession();
		session.clear();
		List<TaskDetails> tasks = (List<TaskDetails>) session.createCriteria(TaskDetails.class).list();
		closeSession(session);
		return tasks;
	}

}
