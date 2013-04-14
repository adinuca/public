package ro.reanad.taskmanager.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import ro.reanad.taskmanager.dao.TaskDao;
import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.User;

public class TaskServiceImplTest {
	private static TaskServiceImpl taskService;
	private static TaskDao mockTaskDao;
	private static Task parentTask;
	private static User user =Mockito.mock(User.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockTaskDao = Mockito.mock(TaskDao.class);
		taskService = new TaskServiceImpl();
		taskService.setTaskDao(mockTaskDao);
		parentTask = new Task("ParentTask", user);
	}

	@Test
	public void testRemoveTask() {
		taskService.removeTask("TASK");
		Mockito.verify(mockTaskDao).removeTask("TASK");
	}

	@Test
	public void testAddSubtask(){
		Task subtask = new Task("SubTask", user);
		taskService.addSubtask(parentTask.getGeneratedId(), subtask);
		Mockito.verify(mockTaskDao).createTask(subtask);
	}

	@Test
	public void testCreateTask(){
		User u = Mockito.mock(User.class);
		Task task = new Task("Task", u);
		taskService.createTask(task);
		Mockito.verify(mockTaskDao).createTask(task);
	}

	
	@Test
	public void testGetAllTasksForUser() {
		List<Task> tasks = Mockito.mock(List.class);
		Mockito.when(
				mockTaskDao.getTasksWithStatusForUsername("username", "status"))
				.thenReturn(tasks);
		List<Task> returnedTasks = taskService.getAllTasksWithStatusForUser(
				"username", "status");
		Mockito.verify(mockTaskDao).getTasksWithStatusForUsername("username",
				"status");
		assertEquals(tasks, returnedTasks);
	}

	@Test
	public void testGetAllTasksWithStatusForUser() {
		List<Task> tasks = Mockito.mock(List.class);
		Mockito.when(
				mockTaskDao.getTasksWithCategoryForUsername("", "category"))
				.thenReturn(tasks);
		List<Task> returnedTasks = taskService.getAllTasksFromCategoryForUser(
				"", "category");
		Mockito.verify(mockTaskDao).getTasksWithCategoryForUsername("",
				"category");
		assertEquals(tasks, returnedTasks);
	}

	@Test
	public void testGetAllTasksFromCategoryForUser() {
		List<Task> tasks = Mockito.mock(List.class);
		Mockito.when(mockTaskDao.getTasksForUsername("")).thenReturn(tasks);
		List<Task> returnedTasks = taskService.getAllTasksForUser("");
		Mockito.verify(mockTaskDao).getTasksForUsername("");
		assertEquals(tasks, returnedTasks);
	}

}
