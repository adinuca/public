package ro.reanad.taskmanager.controller.board;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.service.TaskService;

/**
 * Controller handles the request and returns the ModelAndView
 * 
 * @author adinuca
 * 
 */
@Controller
@RequestMapping("/tasksDisplay.htm")
public class TreeController {
    private static final String TASKS = "tasks";
    private static final String CATEGORY = "category";
    private static final String TASKS_JSP = "WEB-INF/jsp/tasks.jsp";
    private static final String USER = "user";
    @Autowired
	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView getTasks(HttpServletRequest request,
			HttpServletResponse response) {
	
		List<Task> tasks = taskService.getAllTasksForUser((String) request.getSession().getAttribute(USER));
		return new ModelAndView(TASKS_JSP, TASKS, tasks);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView setTasks(HttpServletRequest request,
			HttpServletResponse response) {
		String category = request.getParameter(CATEGORY);
		List<Task> tasks;
		if(category==null){
			tasks = taskService.getAllTasksForUser((String)request.getSession().getAttribute(USER));
		}else{
			tasks = taskService.getAllTasksFromCategoryForUser(((String)request.getSession().getAttribute(USER)),category);
		}
		return new ModelAndView(TASKS_JSP, TASKS, tasks);
	}

}