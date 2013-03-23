package ro.reanad.taskmanager.service;

import java.util.List;

import ro.reanad.taskmanager.dao.exception.DuplicateGeneratedIdException;
import ro.reanad.taskmanager.model.Task;

public interface TaskService {

	Task getTaskWithId(String id);

	List<Task> getAllTasksFromCategoryForUser(String username, String status);

	List<Task> getAllTasksWithStatusForUser(String username, String status);

	List<Task> getAllTasksForUser(String username);

	void addSubtask(String parentTaskId, Task task) throws DuplicateGeneratedIdException;

	void removeTask(String generatedId);

	void createTask(Task task) throws DuplicateGeneratedIdException;

	void modifyTask(Task task) throws DuplicateGeneratedIdException;

}