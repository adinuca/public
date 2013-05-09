package ro.reanad.taskmanager.service;

import java.io.File;
import java.text.SimpleDateFormat;
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
	 SimpleDateFormat ft = 
		      new SimpleDateFormat ("yyyy-MM-dd");
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
	

	public File getXml(List<Task> tasks) throws ParserConfigurationException,
			TransformerException {
	    Document doc = initializeData();
		constructXml(tasks,doc);
		return writeToFile(doc);
	}

	private File writeToFile( Document doc) throws TransformerException {
		File file = new File(TASKS_XML);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);

		transformer.transform(source, result);

		return file;
	}

	private void constructXml(List<Task> tasks, Document doc) {
	    Element taskElement = doc.createElement(TASKS);
	    for (Task task : tasks) {
			taskElement.appendChild(constructTaskElement(task,doc));
		}
	    doc.appendChild(taskElement);
	}

	private Element constructTaskElement(Task task, Document doc) {
		Element taskElement = doc.createElement(TASK);
		taskElement.appendChild(constructStringElement(GENERATED_ID,
				task.getGeneratedId(), doc));
		taskElement.appendChild(constructStringElement(NAME, task.getName(), doc));
		taskElement.appendChild(constructStringElement(DESCRIPTION,
				task.getDescription(), doc));
		taskElement.appendChild(constructStringElement(CATEGORY,
				task.getCategory(), doc));
		taskElement.appendChild(constructDateElement(DUE_DATE,task.getDueDate(), doc));
		taskElement.appendChild(constructIntElement(TIME_SPENT,
				task.getTimeSpent(), doc));
		taskElement.appendChild(constructStringElement(STATUS,
				task.getStatus(), doc));
		if (task.getTask().size() != 0) {
			for (Task t : task.getTask()) {
				taskElement.appendChild(constructTaskElement(t,doc));
			}
		}
		return taskElement;
	}

	private Element constructStringElement(String name, String value, Document doc) {
		Element stringElement = doc.createElement(name);
		stringElement.setTextContent(value);
		return stringElement;
	}

	private Element constructDateElement(String name, Date value, Document doc) {
		Element dateElement = doc.createElement(name);
		dateElement.setTextContent(ft.format(value).toString());
		return dateElement;
	}

	private Element constructIntElement(String name, Integer value, Document doc) {
		Element integerElement = doc.createElement(name);
		integerElement.setTextContent(value.toString());
		return integerElement;
	}

	private Document initializeData() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

}
