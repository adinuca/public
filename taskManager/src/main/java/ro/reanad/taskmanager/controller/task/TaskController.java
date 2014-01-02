package ro.reanad.taskmanager.controller.task;

import javax.servlet.http.HttpServletRequest;

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
    private static final String GENERATED_ID = "generatedId";
    private static final String MANAGE_TASK_JSP = "WEB-INF/jsp/manageTask.jsp";
    private static final String USER = "user";
    private static final String PARENT_TASK = "parentTaskId";
    private static final String TASK = "task";
    private static final String REMOVE = "remove";
    private static final String EDIT = "edit";
    private static final String SUCCESS_JSP = "WEB-INF/jsp/success.jsp";
    public static final String ADD = "add";

    @Autowired
    private TaskService taskService;

    private void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = TASKS_HTM, method = RequestMethod.POST)
    protected ModelAndView manageTask(HttpServletRequest request) {
        String edit = request.getParameter(EDIT);
        String remove = request.getParameter(REMOVE);
        String add = request.getParameter(ADD);

        String parentId = request.getParameter(GENERATED_ID);
        ModelAndView mav = new ModelAndView(MANAGE_TASK_JSP);

        if (edit != null) {
            Task task = taskService.getTaskWithId(parentId);
            mav.addObject(TASK, task);
        } else if (remove != null) {
            return removeTask(request,parentId);
        } else if (add != null) {
            Task task = new Task((String) request.getSession().getAttribute(USER));
            if (parentId != null && !parentId.isEmpty()) {
                task.setParentTaskId(parentId);
            }
            mav.addObject(TASK, task);
        }
        return mav;
    }

    @RequestMapping(value = TASKS_HTM, method = RequestMethod.GET)
    protected ModelAndView showAddPage(HttpServletRequest request) {
        Task task = new Task((String) request
                .getSession().getAttribute(USER));
        ModelAndView mav = new ModelAndView(MANAGE_TASK_JSP);
        mav.addObject(TASK, task);
        return mav;
    }

    private ModelAndView removeTask(HttpServletRequest request,String generatedId) {
        taskService.removeTask(generatedId);
        request.setAttribute("removeMessage", "Task " + generatedId
                + " was removed\n");
        return new ModelAndView(SUCCESS_JSP);

    }
}
