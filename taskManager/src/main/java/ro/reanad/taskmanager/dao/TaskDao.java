package ro.reanad.taskmanager.dao;

import java.util.List;

import ro.reanad.taskmanager.model.Task;

public interface TaskDao {

	void modifyTask(Task t);

	void removeTask(String generatedName);

	void createTask(Task t);

	Task getTask(String generatedId);

	List<Task> getTasksForUsername(String username);

	List<Task> getTasksWithStatusForUsername(String username, String status);

	List<Task> getTasksWithCategoryForUsername(String username, String category);

}