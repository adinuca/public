package ro.reanad.taskmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.service.TaskService;

@Controller
public class TaskController {

    private static final String TASKS_HTM = "/tasks.htm";
    private static final String SUBMIT = "submit";
    private static final String GENERATED_ID = "generatedId";
    private static final String MANAGE_TASK_JSP = "WEB-INF/jsp/manageTask.jsp";
    private static final String ADD_SUBTASK = "Add subtask";
    private static final String USER = "user";
    private static final String PARENT_TASK = "parentTask";
    private static final String TASK = "task";
    private static final String REMOVE = "Remove";
    private static final String EDIT = "Edit";
    private static final String ADD_TASK = "Add task";
    private static final String SUCCESS_JSP = "WEB-INF/jsp/success.jsp";

    @Autowired
    private TaskService taskService;

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = TASKS_HTM, method = RequestMethod.POST)
    protected ModelAndView preparePageForGet(HttpServletRequest request,
                                             HttpServletResponse response) {
        String submit = request.getParameter(SUBMIT);
        String parentId = request.getParameter(GENERATED_ID);
        ModelAndView mav = new ModelAndView(MANAGE_TASK_JSP);

        if (submit.equals(REMOVE)) {
            return removeTask(request, response, parentId);
        } else if (submit.equals(EDIT)) {
            Task task = taskService.getTaskWithId(parentId);
            mav.addObject(TASK, task);
        } else if (submit.equals(ADD_TASK)) {
            if (parentId != null && !parentId.isEmpty()) {
                System.out.println("Parent id " + parentId);

                mav.addObject(PARENT_TASK, parentId);
            }
            Task task = new Task((String) request.getSession().getAttribute(USER));
            mav.addObject(TASK, task);
        }
        return mav;
    }

    @RequestMapping(value = TASKS_HTM, method = RequestMethod.GET)
    protected ModelAndView showAddPage(HttpServletRequest request,
                                       HttpServletResponse response) {
        Task task = new Task((String) request
                .getSession().getAttribute(USER));
        ModelAndView mav = new ModelAndView(MANAGE_TASK_JSP);
        mav.addObject(TASK, task);
        return mav;
    }

    /*private ModelAndView editTask(HttpServletRequest request,
            HttpServletResponse response, String generatedId) {
        Task t = taskService.getTaskWithId(generatedId);
        ModelAndView mav = new ModelAndView()
        return "/WEB-INF/jsp/editTask.jsp";
    }

*/
    private ModelAndView removeTask(HttpServletRequest request,
                                    HttpServletResponse response, String generatedId) {
        taskService.removeTask(generatedId);
        request.setAttribute("removeMessage", "Task " + generatedId
                + " was removed\n");
        return new ModelAndView(SUCCESS_JSP);

    }
}
