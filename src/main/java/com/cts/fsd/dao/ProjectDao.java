package com.cts.fsd.dao;

import java.util.List;

import com.cts.fsd.domain.Project;

public interface ProjectDao {

	void addProject(Project project);

	void updateProject(Project project);

	Project searchProject(Integer projectId);

	//void deleteProject(Project project);

	List<Project> getAllProjects();

}
