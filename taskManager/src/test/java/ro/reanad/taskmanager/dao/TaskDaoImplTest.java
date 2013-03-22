package ro.reanad.taskmanager.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ro.reanad.taskmanager.dao.exception.DuplicateGeneratedIdException;
import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:BeanLocations.xml" })
public class TaskDaoImplTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private TaskDao taskDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Test
	public void testModifyTask() {
		Task t = taskDao.getTask("TA1");
		t.setCategory("NewCategory");
		t.setDescription("NewDescription");
		t.setStatus("In progress");
		t.setTimeSpent(3);
		t.setUrl("ft.com");
		taskDao.modifyTask(t);

		Task t1 = taskDao.getTask("TA1");
		assertEquals(t, t1);
	}

	@Test
	public void testCreateTask() throws DuplicateGeneratedIdException {
		Task t = taskDao.getTask("TA1");
		User u = t.getUser();
		Task t1 = new Task("created task", u);

		t1.setCategory("NewCategory");
		t1.setDescription("NewDescription");
		t1.setStatus("In progress");
		t1.setTimeSpent(3);
		t1.setUrl("ft.com");
		taskDao.createTask(t1);
		t = taskDao.getTask(t1.getGeneratedId());
		assertEquals(t, t1);
	}

	@Test
	public void testRemoveTask() {
		taskDao.removeTask("TA2");
		Task t = taskDao.getTask("TA2");
		assertTrue(t == null);
	}

	@Test
	public void testGetTasksWithCategoryForUsername() {
		List<Task> tasks = taskDao.getTasksWithCategoryForUsername("username1",
				"Study");
		assertTrue(tasks.size() == 4);
		assertTrue(tasks.get(0).getCategory().equals("Study"));
	}

	@Test
	public void testGetTasksWithStatusForUsername() {
		List<Task> tasks = taskDao.getTasksWithStatusForUsername("username1",
				"Defined");
		assertTrue(tasks.size() == 5);
		assertTrue(tasks.get(0).getStatus().equals("Defined"));
	}

	@Test
	public void testGetTasksForUsername() {
		List<Task> tasks = taskDao.getTasksForUsername("username2");
		assertTrue(tasks.size() == 8);
	}

}
