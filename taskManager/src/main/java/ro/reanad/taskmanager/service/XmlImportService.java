package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.User;

@Service
public class XmlImportService {
	private XmlParsingService xmlParsingService;
	private TaskService taskService;

	@Autowired
	public void setXmlParsingService(XmlParsingService xmlParsingService) {
		this.xmlParsingService = xmlParsingService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void saveXmlContentInDatabase(File xml, String user, String servletContextPath) throws ParserConfigurationException, SAXException, IOException {
		if (xml != null) {
			List<Task> tasks = xmlParsingService.parseXml(xml,
					servletContextPath);
			if (tasks != null) {
				saveTasks(tasks, user, null);
			}
		}
	}

	private void saveTasks(List<Task> tasks, String user, Task parentTask) {
		for (Task t : tasks) {
			t.setUser(user);
			List<Task> subtasks = t.getTask();
			t.setParentTask(parentTask);
			taskService.createTask(t);
			if (subtasks.size() != 0) {
				saveTasks(t.getTask(), user, t);
			}
		}
	}

}
