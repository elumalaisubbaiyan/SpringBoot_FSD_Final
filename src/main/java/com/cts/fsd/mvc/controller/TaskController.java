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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.fsd.domain.ErrorMessage;
import com.cts.fsd.domain.TaskDetails;
import com.cts.fsd.service.TaskService;

import javassist.NotFoundException;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllTasks(HttpServletResponse response) throws NotFoundException {
		List<TaskDetails> allTasks = taskService.getAllTasks();
		if (allTasks == null || allTasks.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage("No Tasks are available");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

		}
		return ResponseEntity.ok(allTasks);
	}

	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getTask(@PathVariable Integer taskId, HttpServletResponse response)
			throws NotFoundException {
		TaskDetails searchedTask = taskService.searchTask(taskId);
		if (searchedTask == null) {
			ErrorMessage errorMessage = new ErrorMessage("Could not find task with id " + taskId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

		}
		return ResponseEntity.ok(searchedTask);
	}

	private void initializeTaskStatus(TaskDetails taskDetails) {
		if (StringUtils.isEmpty(taskDetails.getStatus())) {
			taskDetails.setStatus("Open");
		}
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTask(@RequestBody TaskDetails taskDetails, HttpServletRequest request) {
		try {

			if (taskDetails.getParentTaskId() != null
					&& (taskDetails.getParentTaskId() == 0 || taskDetails.getParentTaskId() == -1)) {
				if (taskDetails.getParentTaskId() == -1) {
					taskDetails.setParentTask("Self");
				}
				taskDetails.setParentTaskId(null);
			}
			initializeTaskStatus(taskDetails);
			taskService.addTask(taskDetails);
			log.info("Successfully added task >> " + taskDetails);
			return ResponseEntity.ok(taskDetails);
		} catch (Exception e) {
			log.error("Cannot add task " + taskDetails + "Exception in adding new task " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(
					"Exception occured proessing your request. Please try again. " + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{taskId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTask(@PathVariable int taskId, @RequestBody TaskDetails task,
			HttpServletRequest request) {
		try {
			log.info("Task to be updated >> " + task);
			initializeTaskStatus(task);
			if (task.getParentTaskId() != null) {
				if (task.getParentTaskId() == 0) {
					task.setParentTaskId(null);
				} else if (task.getParentTaskId() == -1) {
					task.setParentTaskId(task.getTaskId());
				}
			}

			task.setTaskId(taskId);
			taskService.updateTask(task);
			log.info("Successfully updated details for task id " + task.getTaskId());
			return ResponseEntity.ok(task);
		} catch (

		Exception e) {
			log.error("Cannot update task " + task + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(
					"Exception occured processing your request. Please try again. " + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{taskIdToDelete}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTask(@PathVariable Integer taskIdToDelete, HttpServletRequest request) {
		try {
			taskService.deleteTask(taskIdToDelete);
		} catch (Exception e) {
			log.error("Cannot delete task with id " + taskIdToDelete + ". Exception occured " + e.getMessage(), e);
			ErrorMessage errorMessage = new ErrorMessage(
					"Exception occured proessing your request. Please try again. " + e.getMessage());
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return ResponseEntity.ok("Successfully Deleted task with task id " + taskIdToDelete);
	}

}