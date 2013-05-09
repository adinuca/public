package ro.reanad.taskmanager.service;


import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import ro.reanad.taskmanager.dao.TaskDao;
import ro.reanad.taskmanager.model.Task;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {
	private static TaskServiceImpl taskService;
	
	String parentGeneratedId="parentGeneratedId";
	@Mock
	Task mockSubtask;
	@Mock
	Task mockParentTask;
	@Mock
	TaskDao mockTaskDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		taskService = new TaskServiceImpl();
	}
	
	@Test
	public void testAddSubtask(){
		taskService.setTaskDao(mockTaskDao);
		when(mockTaskDao.getTask(parentGeneratedId)).thenReturn(mockParentTask);
		taskService.addSubtask(parentGeneratedId, mockSubtask);
		verify(mockTaskDao).createTask(mockSubtask);
	}
}
