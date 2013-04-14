package ro.reanad.taskmanager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.reanad.taskmanager.dao.TaskDao;
import ro.reanad.taskmanager.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
	private Logger logger = Logger.getLogger(TaskServiceImpl.class);
	private TaskDao taskDao;

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public void removeTask(String generatedId) {
		taskDao.removeTask(generatedId);
	}

	@Override
	public void addSubtask(String parentTaskId, Task task){
		Task parentTask = taskDao.getTask(parentTaskId);
		task.setParentTask(parentTask);
		taskDao.createTask(task);
	}

	private void addTask(Task task) {
		taskDao.createTask(task);
		logger.debug("Subtask was added " + task.toString());
	}

	@Override
	public void modifyTask(Task task) {
		taskDao.modifyTask(task);
	}

	
	@Override
	public void createTask(Task task) {
		addTask(task);
	}

	@Override
	public List<Task> getAllTasksForUser(String username) {
		List<Task> tasks = taskDao.getTasksForUsername(username);
		return tasks;
	}

	@Override
	public List<Task> getAllTasksWithStatusForUser(String username,
			String status) {
		List<Task> tasks = taskDao.getTasksWithStatusForUsername(username,
				status);
		return tasks;
	}

	@Override
	public List<Task> getAllTasksFromCategoryForUser(String username,
			String category) {
		List<Task> tasks = taskDao.getTasksWithCategoryForUsername(username,
				category);
		return tasks;
	}

	@Override
	public Task getTaskWithId(String id) {
		return taskDao.getTask(id);
	}
}
