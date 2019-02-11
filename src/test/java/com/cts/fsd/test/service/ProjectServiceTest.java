package com.cts.fsd.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.cts.fsd.dao.ProjectDao;
import com.cts.fsd.domain.Project;
import com.cts.fsd.service.ProjectServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class ProjectServiceTest {

	@Mock
	private ProjectDao projectDao;

	@InjectMocks
	private ProjectServiceImpl projectService;

	@Before
	public void setupMock() {
		projectDao = mock(ProjectDao.class);
		projectService = mock(ProjectServiceImpl.class);
		ReflectionTestUtils.setField(projectService, "projectDao", projectDao);
	}

	@Test
	public void testAddProject() throws Exception {
		Project project = new Project();
		project.setProjectId(1);
		doNothing().when(projectDao).addProject(project);
		doCallRealMethod().when(projectService).addProject(project);
		projectService.addProject(project);
		verify(projectDao, times(1)).addProject(project);
	}

	@Test
	public void testUpdateProject() throws Exception {
		doNothing().when(projectDao).updateProject(any(Project.class));
		doCallRealMethod().when(projectService).updateProject(any(Project.class));
		projectService.updateProject(new Project());
		verify(projectDao, times(1)).updateProject(any(Project.class));
	}

	@Test
	public void testSearchProject() throws Exception {
		int projectIdToService = 10;
		Project project = new Project();
		project.setProjectId(projectIdToService);
		when(projectDao.searchProject(projectIdToService)).thenReturn(project);
		doCallRealMethod().when(projectService).searchProject(projectIdToService);
		Project searchedProject = projectService.searchProject(projectIdToService);
		assertEquals(projectIdToService, searchedProject.getProjectId());
	}

	@Test
	public void testGetAllProject() throws Exception {
		List<Project> mockedProjectList = new ArrayList<>();
		mockedProjectList.add(new Project());
		when(projectDao.getAllProjects()).thenReturn(mockedProjectList);
		doCallRealMethod().when(projectService).getAllProjects();
		List<Project> allProjects = projectService.getAllProjects();
		assertEquals(allProjects.size(), mockedProjectList.size());
		assertThat(allProjects, is(mockedProjectList));
	}

}