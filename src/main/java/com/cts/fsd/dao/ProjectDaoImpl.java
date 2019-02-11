package com.cts.fsd.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cts.fsd.domain.Project;

@Repository
public class ProjectDaoImpl extends BaseDao implements ProjectDao {

	// private Logger log = LoggerFactory.getLogger(ProjectDaoImpl.class);

	public void addProject(Project project) {
		saveData(project);
	}

	public void updateProject(Project project) {
		updateData(project);
	}

	public Project searchProject(Integer projectId) {
		Project project = null;
		Object result = getDataById(projectId, Project.class);
		if (result != null) {
			project = (Project) result;
		}
		return project;
	}

	/*public void deleteProject(Project project) {
		deleteData(project);
	}*/

	@SuppressWarnings("unchecked")
	public List<Project> getAllProjects() {
		Session session = openSession();
		session.clear();
		List<Project> projects = (List<Project>) session.createCriteria(Project.class).list();
		closeSession(session);
		return projects;
	}

}
