package ro.reanad.taskmanager.controller.task;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.service.TaskService;

@Controller
public class AddTaskController {
    private static final String SUCCESS_JSP = "WEB-INF/jsp/success.jsp";
    private static final String PARENT_TASK_ID = "parentTaskId";
    private static final String USER = "user";
    private static final String TASK = "task";

    @Autowired
    private TaskService taskService;


    private void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/addTask.htm", method = RequestMethod.POST)
    protected String addTask(@ModelAttribute(TASK) Task task, HttpSession session) {
        String userEmail = (String) session.getAttribute(USER);
        task.setUser(userEmail);

        if (taskService.getTaskWithId(task.getGeneratedId()) != null) {
            modifyTask(task);
        } else {
            if (task.getParentTaskId() != null) {
                taskService.addSubtask(task);
            } else {
                taskService.createTask(task);
            }
        }

        return SUCCESS_JSP;
    }

    private void modifyTask(Task task) {
        Task originalTask = taskService.getTaskWithId(task.getGeneratedId());
        originalTask.setCategory(task.getCategory());
        originalTask.setName(task.getName());
        originalTask.setDescription(task.getDescription());
        originalTask.setUrl(task.getUrl());
        originalTask.setStatus(task.getStatus());
        taskService.modifyTask(originalTask);
    }
}
