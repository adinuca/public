package ro.reanad.taskmanager.xmlparsing;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;


import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;

public class SaxDefaultHandlerTest {

	private static final String NAME = "name";
	private static final String TIME_SPENT = "timeSpent";
	private static final String DESCRIPTION = "Description";
	private static final String CATEGORY = "Category";
	private static final String URL = "Url";
	private static final String STATUS = "Status";
	private static final String GENERATED_ID = "generatedId";
	private static final String TASK = "task";
	static SaxDefaultHandler handler;

	static List<Task> mockTasks = Mockito.mock(List.class);
	Task mockTask = Mockito.mock(Task.class);
	Task mockParentTask = Mockito.mock(Task.class);

	@BeforeClass
	public static void setUp() {
		handler = new SaxDefaultHandler(mockTasks);
	}

	@Test
	public void testStartElementIsTaskCreatesNewTask() throws SAXException {
		handler.currentTask = null;
		handler.startElement(null, null, TASK, null);
		assertNotNull(handler.currentTask);
	}
	

	@Test
	public void testStartElementIsTaskCreatesNewTaskAndSetsParrent()
			throws SAXException {
		handler.currentTask = mockTask;
		handler.startElement(null, null, TASK, null);
		assertNotNull(handler.currentTask);
		assertFalse(handler.currentTask.equals(mockTask));
		assertTrue(handler.currentTask.getParentTask().equals(mockTask));
	}

	@Test
	public void testStartElementIsNotTask() throws SAXException {
		handler.currentTask = null;
		handler.startElement(null, null, "", null);
		assertTrue(handler.currentTask == null);
	}

	@Test
	public void testEndElementIsNotTask() throws SAXException {
		handler.endElement(null, null, "");
		Mockito.verifyZeroInteractions(mockTasks);
	}

	@Test
	public void testEndElementIsGeneratedId() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=GENERATED_ID;
		handler.endElement(null, null, GENERATED_ID);
		Mockito.verify(mockTask).setGeneratedId(GENERATED_ID);
	}

	@Test
	public void testEndElementIsName() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=NAME;
		handler.endElement(null, null, NAME);
		Mockito.verify(mockTask).setName(NAME);
	}

	@Test
	public void testEndElementIsDescription() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=DESCRIPTION;
		handler.endElement(null, null, DESCRIPTION);
		Mockito.verify(mockTask).setDescription(DESCRIPTION);
	}

	@Test
	public void testEndElementIsCategory() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=CATEGORY;
		handler.endElement(null, null, CATEGORY);
		Mockito.verify(mockTask).setCategory(CATEGORY);
	}

	@Test
	public void testEndElementIsTimeSpent() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue = "3";
		handler.endElement(null, null, TIME_SPENT);
		Mockito.verify(mockTask).setTimeSpent(3);
	}

	@Test
	public void testEndElementIsUrl() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=URL;
		handler.endElement(null, null, URL);
		Mockito.verify(mockTask).setUrl(URL);
	}

	@Test
	public void testEndElementIsStatus() throws SAXException {
		handler.currentTask = mockTask;
		handler.tmpValue=STATUS;
		handler.endElement(null, null, STATUS);
		Mockito.verify(mockTask).setStatus(STATUS);
	}

	@Test
	public void testEndElementIsTaskAndHasNoParrent() throws SAXException {
		handler.currentTask = mockTask;
		handler.currentTask.setParentTask(null);
		handler.endElement(null, null, TASK);
		Mockito.verify(mockTasks).add(mockTask);
	}

	@Test
	public void testEndElementIsTaskAndHasParrent() throws SAXException {
		handler.currentTask = mockTask;
		Mockito.when(mockTask.getParentTask()).thenReturn(mockParentTask);
		handler.endElement(null, null, TASK);
		Mockito.verifyZeroInteractions(mockTasks);
		assertEquals(handler.currentTask,mockParentTask);
	}
}
