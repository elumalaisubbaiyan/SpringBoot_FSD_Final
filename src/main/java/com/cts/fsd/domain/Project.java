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

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int projectId;

	@Column(name = "project_name", nullable = false)
	private String projectName;

	@Column
	private Integer priority;

	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;

	@Column(name = "manager_id", nullable = true)
	private Integer managerId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "manager_id", nullable = true, insertable = false, updatable = false)
	private User manager;

	@Formula("(SELECT count(*) FROM task_details t WHERE t.project_id = project_id)")
	private int numberOfTasks;

	@Formula("(SELECT count(*) FROM task_details t WHERE t.project_id = project_id and t.status='Closed')")
	private int completedTasks;

	public Project() {
		super();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public int getNumberOfTasks() {
		return numberOfTasks;
	}

	public void setNumberOfTasks(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}

	public int getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(int completedTasks) {
		this.completedTasks = completedTasks;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		Project newProject = (Project) obj;
		if (this.projectId == newProject.getProjectId()) {
			return true;
		}
		return super.equals(obj);
	}

}
