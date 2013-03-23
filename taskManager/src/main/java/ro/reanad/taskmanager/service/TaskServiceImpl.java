package ro.reanad.taskmanager.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.reanad.taskmanager.dao.TaskDao;
import ro.reanad.taskmanager.dao.exception.DuplicateGeneratedIdException;
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
	public void addSubtask(String parentTaskId, Task task) throws DuplicateGeneratedIdException {
		Task parentTask = taskDao.getTask(parentTaskId);
		task.setParentTask(parentTask);
		taskDao.createTask(task);
	}

	private void addTask(Task task) {
		boolean taskAdded = false;
		while (!taskAdded) {
			try {
				taskDao.createTask(task);
				taskAdded = true;
				logger.debug("Subtask was added "+task.toString() );
				
			} catch (DuplicateGeneratedIdException ex) {
				logger.debug("Generated task id is already in db. Trying again.s "+task.toString() );
				mapTaskToNewId(task);
			}
		}
	}

	private void mapTaskToNewId(Task task) {
		Task newTask = new Task(task.getName(), task.getUser());
		newTask.setCategory(task.getCategory());
		newTask.setDescription(task.getDescription());
		newTask.setDueDate(task.getDueDate());
		newTask.setStatus(task.getStatus());
		newTask.setTimeSpent(task.getTimeSpent());
		newTask.setUrl(task.getUrl());
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
