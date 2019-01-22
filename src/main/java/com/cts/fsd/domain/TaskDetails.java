package com.cts.fsd.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "task_details")
public class TaskDetails {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int taskId;

	@Column(name = "parent_task_id", nullable = true)
	private Integer parentTaskId;

	@ManyToOne(optional = true)
	@JoinColumn(name = "parent_task_id", insertable = false, updatable = false)
	@JsonBackReference
	private TaskDetails parentTaskRef;

	@Column(insertable = false, updatable = false)
	private String parentTask;

	@Column
	private String task;

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

	public TaskDetails() {

	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setParentTaskId(Integer parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public Integer getParentTaskId() {
		return parentTaskId;
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

	public String getTask() {
		return task;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setTask(String task) {
		this.task = task;
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

	@Override
	public String toString() {
		return "TaskDetails [taskId=" + taskId + ", parentTaskId=" + parentTaskId + ", task=" + task + ", status="
				+ status + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
