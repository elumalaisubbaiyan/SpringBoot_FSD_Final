package com.cts.fsd.service;

import java.util.List;

import com.cts.fsd.domain.Project;

import javassist.NotFoundException;

public interface ProjectService {

	void addProject(Project project);

	void updateProject(Project project);

	Project searchProject(Integer projectId);

	void deleteProject(Integer projectId) throws NotFoundException;

	List<Project> getAllProjects();

}
