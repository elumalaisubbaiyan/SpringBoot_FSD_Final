package com.cts.fsd.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "task_details")
public class TaskDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int taskId;

	@Column(name = "project_id")
	private Integer projectId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "project_id", nullable = true, insertable = false, updatable = false)
	private Project projectName;

	@Column
	private String task;

	@Column(name = "parent_task_id", nullable = true)
	private Integer parentTaskId;

	@Column(name = "marked_parent")
	private boolean markedParent;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_task_id", nullable = true, insertable = false, updatable = false)
	@JsonBackReference
	private TaskDetails parentTaskRef;

	@Column(insertable = false, updatable = false)
	private String parentTask;

	@Column
	private Integer priority;

	@Column
	private String status;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@Column(name = "user_id", nullable = true)
	private Integer userId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = true, insertable = false, updatable = false)
	private User user;

	public TaskDetails() {
		super();
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Project getProjectName() {
		return projectName;
	}

	public void setProjectName(Project projectName) {
		this.projectName = projectName;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(Integer parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public boolean isMarkedParent() {
		return markedParent;
	}

	public void setMarkedParent(boolean markedParent) {
		this.markedParent = markedParent;
	}

	public TaskDetails getParentTaskRef() {
		return parentTaskRef;
	}

	public void setParentTaskRef(TaskDetails parentTaskRef) {
		this.parentTaskRef = parentTaskRef;
	}

	public String getParentTask() {
		if (parentTaskRef != null) {
			return parentTaskRef.getTask();
		}
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "TaskDetails [taskId=" + taskId + ", projectId=" + projectId + ", projectName=" + projectName + ", task="
				+ task + ", parentTaskId=" + parentTaskId + ", parentTask=" + parentTask + ", priority=" + priority
				+ ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", userId=" + userId
				+ ", user=" + user + "]";
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		TaskDetails newTask = (TaskDetails) obj;
		if (this.taskId == newTask.getTaskId()) {
			return true;
		}
		return super.equals(obj);
	}

}
