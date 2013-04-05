package ro.reanad.taskmanager.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.reanad.taskmanager.model.Task;

@Service
public class ConstructXmlService {
	public ConstructXmlService() {
    }

    private static final String TASKS_XML = "tasks.xml";
	private static final String STATUS = "status";
	private static final String TIME_SPENT = "timeSpent";
	private static final String DUE_DATE = "dueDate";
	private static final String CATEGORY = "category";
	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String GENERATED_ID = "generatedId";
	private static final String TASK = "task";
    private static final String TASKS = "tasks";
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;

	public File getXml(List<Task> tasks) throws ParserConfigurationException,
			TransformerException {
		initializeData();
		constructXml(tasks);
		return writeToFile();
	}

	private File writeToFile() throws TransformerException {
		File file = new File(TASKS_XML);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);

		transformer.transform(source, result);

		return file;
	}

	private void constructXml(List<Task> tasks) {
	    Element taskElement = doc.createElement(TASKS);
	    for (Task task : tasks) {
			taskElement.appendChild(constructTaskElement(task));
		}
	    doc.appendChild(taskElement);
	}

	private Element constructTaskElement(Task task) {
		Element taskElement = doc.createElement(TASK);
		taskElement.appendChild(constructStringElement(GENERATED_ID,
				task.getGeneratedId()));
		taskElement.appendChild(constructStringElement(NAME, task.getName()));
		taskElement.appendChild(constructStringElement(DESCRIPTION,
				task.getDescription()));
		taskElement.appendChild(constructStringElement(CATEGORY,
				task.getCategory()));
		taskElement.appendChild(constructDateElement(DUE_DATE,
				task.getDueDate()));
		taskElement.appendChild(constructIntElement(TIME_SPENT,
				task.getTimeSpent()));
		taskElement.appendChild(constructStringElement(STATUS,
				task.getStatus()));
		if (task.getSubTasks().size() != 0) {
			for (Task t : task.getSubTasks()) {
				taskElement.appendChild(constructTaskElement(t));
			}
		}
		return taskElement;
	}

	private Element constructStringElement(String name, String value) {
		Element stringElement = doc.createElement(name);
		stringElement.setTextContent(value);
		return stringElement;
	}

	private Element constructDateElement(String name, Date value) {
		Element dateElement = doc.createElement(name);
		dateElement.setTextContent(value.toString());
		return dateElement;
	}

	private Element constructIntElement(String name, Integer value) {
		Element integerElement = doc.createElement(name);
		integerElement.setTextContent(value.toString());
		return integerElement;
	}

	private void initializeData() throws ParserConfigurationException {
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
	}

}
