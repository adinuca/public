package ro.reanad.taskmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.UserService;
import ro.reanad.taskmanager.service.UserServiceImpl;
import ro.reanad.taskmanager.service.TaskService;

@Controller
public class TaskController {
	@Autowired
	TaskService taskService;
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/tasks.htm", method = RequestMethod.POST)
	protected ModelAndView preparePageForGet(HttpServletRequest request,
			HttpServletResponse response) {
		String submit = request.getParameter("submit");
		String generatedId = request.getParameter("generatedId");
		ModelAndView mav = new ModelAndView("WEB-INF/jsp/manageTask.jsp");
		
		if (submit.equals("Add subtask")) {
			Task task = new Task(userService.getUserWithUsername((String)request.getSession().getAttribute("user")));
			mav.addObject("parentTask",generatedId);
			mav.addObject("task", task);
		} else if (submit.equals("Remove")) {
			return removeTask(request, response, generatedId);
		} else if (submit.equals("Edit")) {
			Task task = taskService.getTaskWithId(generatedId);
			mav.addObject("task",task);
		} else if (submit.equals("Add task")) {
			Task task = new Task();
			mav.addObject("task",task);
		}
		return mav;
	}

	@RequestMapping(value = "/tasks.htm", method = RequestMethod.GET)
	protected ModelAndView showAddPage(HttpServletRequest request,
			HttpServletResponse response) {
		Task task = new Task(userService.getUserWithUsername((String) request
				.getSession().getAttribute("user")));
		ModelAndView mav = new ModelAndView("WEB-INF/jsp/manageTask.jsp");
		mav.addObject("task", task);
		return mav;
	}

	/*private ModelAndView editTask(HttpServletRequest request,
			HttpServletResponse response, String generatedId) {
		Task t = taskService.getTaskWithId(generatedId);
		ModelAndView mav = new ModelAndView()
		return "/WEB-INF/jsp/editTask.jsp";
	}

*/	private ModelAndView removeTask(HttpServletRequest request,
			HttpServletResponse response, String generatedId) {
		taskService.removeTask(generatedId);
		request.setAttribute("removeMessage", "Task " + generatedId
				+ " was removed\n");
		return new ModelAndView("WEB-INF/jsp/success.jsp");

	}
}
