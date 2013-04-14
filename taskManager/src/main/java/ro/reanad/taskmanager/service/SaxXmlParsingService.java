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

	List<Task> tasks = null;
	private Task currentTask = null;
	private String tmpValue;

	public boolean validate(File file, String servletContextPath) {
		try {
			// define the type of schema - we use W3C:
			String schemaLang = "http://www.w3.org/2001/XMLSchema";

			// get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(schemaLang);

			// create schema by reading it from an XSD file:
			Schema schema = factory.newSchema(new StreamSource(
					servletContextPath + "/WEB-INF/classes/schema.xsd"));
			Validator validator = schema.newValidator();

			// at last perform validation:
			validator.validate(new StreamSource(file));
			return true;
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Task> parseXml(File file, String servletContextPath) {
		if (validate(file, servletContextPath)) {
			tasks = new ArrayList<Task>();
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
		return null;
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
