package ro.reanad.taskmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.service.TaskService;
import ro.reanad.taskmanager.service.UserService;

@Controller
public class AddTaskController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/addTask.htm", method = RequestMethod.POST)
	protected String addTask(@ModelAttribute("task") Task task,
			BindingResult result, HttpSession session,
			HttpServletRequest request) {
	task.setUser((String)session.getAttribute("user"));
		String parentTaskId = request.getParameter("parentTaskId");
		if (taskService.getTaskWithId(task.getGeneratedId()) != null) {
			Task originalTask = taskService.getTaskWithId(task.getGeneratedId());
			originalTask.setCategory(task.getCategory());
			originalTask.setName(task.getName());
			originalTask.setDescription(task.getDescription());
			originalTask.setUrl(task.getUrl());
			originalTask.setStatus(task.getStatus());
			taskService.modifyTask(originalTask);
		} else if ((parentTaskId == null ) || ( parentTaskId.equals(""))) {
			taskService.createTask(task);
		} else {
			taskService.addSubtask(parentTaskId, task);
		}
		return "WEB-INF/jsp/success.jsp";
	}
}
