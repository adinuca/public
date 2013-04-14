package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;
import ro.reanad.taskmanager.model.Task;

@Service
public class SaxXmlParsingService extends DefaultHandler implements
		XmlParsingService {

	private static final String SCHEMA_PATH = "/WEB-INF/classes/schema.xsd";
	private static final String HTTP_WWW_W3_ORG_2001_XML_SCHEMA= "http://www.w3.org/2001/XMLSchema";
	List<Task> tasks = null;
	private Task currentTask = null;
	private String tmpValue;

	public void validate(File file, String servletContextPath) throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance(HTTP_WWW_W3_ORG_2001_XML_SCHEMA);
		Schema schema = factory.newSchema(new StreamSource(servletContextPath
				+ SCHEMA_PATH));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(file));
	}

	@Override
	public List<Task> parseXml(File file, String servletContextPath)
			throws ParserConfigurationException, SAXException, IOException {
		validate(file, servletContextPath);
		tasks = new ArrayList<Task>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(file, this);
		return tasks;
	}

	@Override
	public void startElement(String s, String s1, String elementName,
			Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("task")) {
			createTask();
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		tmpValue = new String(ac, i, j);
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
		if (element.equalsIgnoreCase("generatedId")) {
			currentTask.setGeneratedId(tmpValue);
		}
		if (element.equalsIgnoreCase("name")) {
			currentTask.setName(tmpValue);
		}
		if (element.equalsIgnoreCase("description")) {
			currentTask.setDescription(tmpValue);
		}
		if (element.equalsIgnoreCase("category")) {
			currentTask.setCategory(tmpValue);
		}
		/*
		 * if (elementName.equalsIgnoreCase("dueDate")) {
		 * currentTask.setDueDate(Calendar.getInstance().
		 * attributes.getValue("dueDate")); }
		 */if (element.equalsIgnoreCase("timeSpent")) {
			currentTask.setTimeSpent(Integer.parseInt(tmpValue));
		}
		if (element.equalsIgnoreCase("url")) {
			currentTask.setUrl(tmpValue);
		}
		if (element.equalsIgnoreCase("status")) {
			currentTask.setStatus(tmpValue);
		}
	}

	private void goBackToParentTask() throws WrongSubtaskException {
		if (currentTask.getParentTask() == null) {
			tasks.add(currentTask);
			currentTask = null;
		} else {
			Task newTask = currentTask;
			currentTask = newTask.getParentTask();
			currentTask.addSubTasks(newTask);
		}
	}

	private void createTask() {
		if (currentTask != null) {
			Task newTask = currentTask;
			currentTask = new Task();
			currentTask.setParentTask(newTask);
		} else
			currentTask = new Task();
	}

}
