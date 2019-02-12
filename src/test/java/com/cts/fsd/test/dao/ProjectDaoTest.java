package com.cts.fsd.test.dao;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.fsd.dao.ProjectDao;
import com.cts.fsd.domain.Project;

public class ProjectDaoTest extends EntityDaoTest {
	@Autowired
	private ProjectDao projectDao;

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Task.xml"));
		return dataSet;
	}

	@Test
	public void testSearchProject() {
		Project searchedProject = projectDao.searchProject(2);
		Assert.assertNotNull(searchedProject);

		Assert.assertTrue(searchedProject.getProjectName().equalsIgnoreCase("Final FSD Assesssment"));
		Assert.assertNull(projectDao.searchProject(4));
	}

	@Test
	public void testAddProject() {
		Project project = new Project();
		project.setProjectName("New Project");
		project.setPriority(1);
		projectDao.addProject(project);
		assertThat(projectDao.getAllProjects().size(), is(4));
	}

	@Test
	public void testUpdateProject() {
		Project projectToBeUpdated = projectDao.searchProject(3);
		projectToBeUpdated.setProjectName("Updated Project");
		projectToBeUpdated.setPriority(2);
		projectDao.updateProject(projectToBeUpdated);
		Project updatedProject = projectDao.searchProject(3);

		Assert.assertTrue(updatedProject.getProjectName().equalsIgnoreCase("Updated Project"));
		Assert.assertTrue(updatedProject.getPriority() == 2);
	}

	@Test
	public void testAllProjects() {
		List<Project> allProjects = projectDao.getAllProjects();
		Assert.assertNotNull(allProjects);
		assertThat(allProjects.size(), is(3));
		Project searchedProject = projectDao.searchProject(2);
		assertThat(allProjects, hasItems(searchedProject));
	}

	@Test(expected = StaleStateException.class)
	public void testExceptionForUpdateTask() {
		Project project = new Project();
		project.setProjectId(4);
		project.setProjectName("");
		projectDao.updateProject(project);
	}

}
