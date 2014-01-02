package ro.reanad.taskmanager.service;

import java.util.List;

import ro.reanad.taskmanager.model.Task;

public interface TaskService {

	Task getTaskWithId(String id);

	List<Task> getAllTasksFromCategoryForUser(String username, String status);

	List<Task> getAllTasksWithStatusForUser(String username, String status);

	List<Task> getAllTasksForUser(String username);

	void addSubtask(Task task);

	void removeTask(String generatedId);

	void createTask(Task task);

	void modifyTask(Task task);

}