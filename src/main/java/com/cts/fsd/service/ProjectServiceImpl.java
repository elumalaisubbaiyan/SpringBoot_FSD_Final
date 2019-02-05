package com.cts.fsd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.fsd.dao.ProjectDao;
import com.cts.fsd.domain.Project;

import javassist.NotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDao projectDao;

	public void addProject(Project project) {
		projectDao.addProject(project);
	}

	public void updateProject(Project project) {
		projectDao.updateProject(project);
	}

	public Project searchProject(Integer projectId) {
		return projectDao.searchProject(projectId);
	}

	public void deleteProject(Integer projectId) throws NotFoundException {
		Project task = projectDao.searchProject(projectId);
		if (task == null) {
			throw new NotFoundException("Cannot find project with " + projectId);
		}
		projectDao.deleteProject(task);
	}

	public List<Project> getAllProjects() {
		return projectDao.getAllProjects();
	}

}
