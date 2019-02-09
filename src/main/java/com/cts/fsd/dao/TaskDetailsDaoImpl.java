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
		log.info("Retrieved task id {} after adding the task successfully {} ", savedTaskId, task);
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

	@SuppressWarnings("unchecked")
	public List<TaskDetails> getParentTasks() {
		Session session = openSession();
		session.clear();
		List<TaskDetails> tasks = session.createSQLQuery("select * from task_details where marked_parent=?")
				.addEntity(TaskDetails.class).setBoolean(0, true).list();
		closeSession(session);
		return tasks;
	}

	@SuppressWarnings("unchecked")
	public List<TaskDetails> getTasksByProject(int projectId) {
		Session session = openSession();
		session.clear();
		List<TaskDetails> tasks = session.createSQLQuery("select * from task_details where project_id=?")
				.addEntity(TaskDetails.class).setInteger(0, projectId).list();
		closeSession(session);
		return tasks;
	}

}
