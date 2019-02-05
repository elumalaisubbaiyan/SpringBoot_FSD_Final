package com.cts.fsd.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.fsd.domain.ErrorMessage;
import com.cts.fsd.domain.ResponseData;
import com.cts.fsd.domain.Project;
import com.cts.fsd.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
	private static final Logger log = LoggerFactory.getLogger(ProjectsController.class);

	private static final String ERROR_MSG = "Exception occured processing your request. Please try again. ";

	private static final String NO_USERS_ERROR_MSG = "No Projects are available in the system";
	
	private static final String USER_NOT_FOUND = "Could not find project with id ";
	
	private static final String ADD_USER_ERR = "Exception in adding new project ";

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllProjects(HttpServletResponse response) {
		List<Project> allProjects = projectService.getAllProjects();
		if (allProjects == null || allProjects.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage(NO_USERS_ERROR_MSG);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
		return ResponseEntity.ok(allProjects);
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getProject(@PathVariable Integer projectId, HttpServletResponse response) {
		Project searchedTask = projectService.searchProject(projectId);
		if (searchedTask == null) {
			ErrorMessage errorMessage = new ErrorMessage(USER_NOT_FOUND + projectId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

		}
		return ResponseEntity.ok(searchedTask);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addProject(@RequestBody Project project, HttpServletRequest request) {
		try {
			projectService.addProject(project);
			log.info("Successfully added project {} ", project);
			return ResponseEntity.ok(project);
		} catch (Exception e) {
			log.error("Cannot add project " + project + ADD_USER_ERR + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{projectId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateProject(@PathVariable int projectId, @RequestBody Project project,
			HttpServletRequest request) {
		try {
			log.info("Project to be updated {} ", project);
			projectService.updateProject(project);
			log.info("Successfully updated details for project id {}", project.getProjectId());
			return ResponseEntity.ok(project);
		} catch (Exception e) {
			log.error("Cannot update project " + project + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{projectIdToDelete}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProject(@PathVariable Integer projectIdToDelete, HttpServletRequest request) {
		try {
			log.info("Project id to be deleted {} ", projectIdToDelete);
			projectService.deleteProject(projectIdToDelete);
			log.info("Successfully deleted project with project id {} ", projectIdToDelete);
		} catch (Exception e) {
			log.error("Cannot delete project with id " + projectIdToDelete + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(ERROR_MSG + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ResponseData responseData = new ResponseData();
		responseData.setStatusMsg("Successfully Deleted project with project id " + projectIdToDelete);
		return ResponseEntity.ok(responseData);
	}

}