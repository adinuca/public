package ro.reanad.taskmanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.LoginService;
import ro.reanad.taskmanager.service.TaskService;

/**
 * Controller handles the request and returnes the ModelAndView
 * 
 * @author adinuca
 * 
 */
@Controller
@RequestMapping("/tasksDisplay.htm")
@SessionAttributes("user")
public class TreeController {
	@Autowired
	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView getTasks(HttpServletRequest request,
			HttpServletResponse response) {
	
		List<Task> tasks = taskService.getAllTasksForUser(((User)request.getSession().getAttribute("user")).getUsername());
		return new ModelAndView("WEB-INF/jsp/tasks.jsp", "tasks", tasks);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView setTasks(HttpServletRequest request,
			HttpServletResponse response) {
		String category = request.getParameter("category");
		List<Task> tasks=new ArrayList<Task>();
		if(category==null){
			tasks = taskService.getAllTasksForUser(((User)request.getSession().getAttribute("user")).getUsername());
		}else{
			tasks = taskService.getAllTasksFromCategoryForUser(((User)request.getSession().getAttribute("user")).getUsername(),category);
		}
		return new ModelAndView("WEB-INF/jsp/tasks.jsp", "tasks", tasks);
	}

}