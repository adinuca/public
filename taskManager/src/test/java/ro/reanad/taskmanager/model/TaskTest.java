package ro.reanad.taskmanager.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;

public class TaskTest {
	private static Task task;
	private static String u;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		u = "username";
		task = new Task("testName",u);
	}


	@Test
	public void testGetGeneratedId() {
		assertTrue(task.getGeneratedId() != null);
	}

	@Test
	public void testGetName() {
		task = new Task("name",u);
		assertEquals("name", task.getName());
	}

	@Test
	public void testSetName() {
		task.setName("changed Name");
		assertEquals("changed Name", task.getName());
		assertFalse("changed Name ".equals(task.getName()));

	}

	@Test
	public void testGetAndSetDescription() {
		assertTrue(task.getDescription() == null);
		task.setDescription("description description description description");
		assertEquals("description description description description",
				task.getDescription());
	}

	@Test
	public void testGetAndSetCategory() {
		assertTrue(task.getCategory() == null);
		task.setCategory("category");
		assertEquals("category", task.getCategory());
	}

	@Test
	public void testSetParentTask() {
		Task parentTask = new Task("parentClass",u);
		task.setParentTask(parentTask);
		assertTrue(task.getParentTask().equals(parentTask));
	}

	@Test
	public void testgetTask() throws WrongSubtaskException {
		Task task1 = new Task("task1", u);
		Task task2 = new Task("task2", u);
		assertTrue(task.getTask().size() == 0);

		task.addSubTasks(task1);
		assertTrue(task.getTask().size() == 1);
		assertTrue(task.getTask().contains(task1));

		task.addSubTasks(task2);
		assertTrue(task.getTask().size() == 2);
		assertTrue(task.getTask().contains(task1));
		assertTrue(task.getTask().contains(task2));

	}

	@Test
	public void testGetAndSetDueDate() {
		assertTrue(task.getDueDate() != null);

		Date date = new Date();
		task.setDueDate(date);

		assertEquals(date, task.getDueDate());
	}

	@Test
	public void testGetAndSetTimeSpent() {
		assertEquals(0, task.getTimeSpent());
		
		task.setTimeSpent(5);

		assertEquals(5, task.getTimeSpent());
	}

	@Test
	public void testGetAndSetUrl() {
		assertTrue( task.getUrl()==null);
		
		task.setUrl("ft.com");

		assertEquals("ft.com", task.getUrl());
		}


	@Test
	public void testGetUser() {
		assertTrue(task.getUserEmail()!=null);
	}

	@Test
	public void testGetAndSetStatus() {
		task.setStatus("Defined");
		assertTrue(task.getStatus().equals("Defined"));
		
	}


}
