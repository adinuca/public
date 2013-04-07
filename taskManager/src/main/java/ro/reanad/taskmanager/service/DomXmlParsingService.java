package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;
import ro.reanad.taskmanager.model.Task;

@Service
public class DomXmlParsingService extends DefaultHandler implements
		XmlParsingService {

	private Task currentTask = null;

	@Override
	public List<Task> parseXml(File file) {
		List<Task> tasks = new ArrayList<Task>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
		return tasks;
	}

	@Override
	public void startElement(String s, String s1, String elementName,
			Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("task")) {
			createTask();
		}
		if (elementName.equalsIgnoreCase("generatedId")) {
			currentTask.setGeneratedId(attributes.getValue("generatedId"));
		}
		if (elementName.equalsIgnoreCase("name")) {
			currentTask.setName(attributes.getValue("name"));
		}
		if (elementName.equalsIgnoreCase("description")) {
			currentTask.setDescription(attributes.getValue("description"));
		}
		if (elementName.equalsIgnoreCase("category")) {
			currentTask.setCategory(attributes.getValue("category"));
		}
		/*if (elementName.equalsIgnoreCase("dueDate")) {
			currentTask.setDueDate(Calendar.getInstance(). attributes.getValue("dueDate"));
		}
		*/if (elementName.equalsIgnoreCase("timeSpent")) {
			currentTask.setTimeSpent(Integer.parseInt(attributes.getValue("timeSpent")));
		}
		if (elementName.equalsIgnoreCase("url")) {
			currentTask.setUrl(attributes.getValue("url"));
		}
		if (elementName.equalsIgnoreCase("status")) {
			currentTask.setStatus(attributes.getValue("status"));
		}
	}


	@Override
	public void endElement(String s, String s1, String element)
			throws SAXException {
		// if end of book element add to list
		if (element.equals("task")) {
			try {
				goBackToParentTask();
			} catch (WrongSubtaskException e) {
				e.printStackTrace();
			}
		}
	}

	private void goBackToParentTask() throws WrongSubtaskException {
		Task newTask = currentTask;
		currentTask =newTask.getParentTask();
		currentTask.addSubTasks(newTask);
	}

	private void createTask() {
		if (currentTask!=null){
			Task newTask = currentTask;
			currentTask = new Task();
			currentTask.setParentTask(newTask);
		}
	}
}
