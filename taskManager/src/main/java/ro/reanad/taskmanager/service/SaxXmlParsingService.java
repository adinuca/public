package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.xmlparsing.SaxDefaultHandler;
import ro.reanad.taskmanager.xmlparsing.XmlValidator;

public class SaxXmlParsingService implements XmlParsingService {

	@Override
	public List<Task> parseXml(File file, String servletContextPath)
			throws ParserConfigurationException, SAXException, IOException {
		XmlValidator validator = new XmlValidator();
		validator.validate(file, servletContextPath);
		
		List<Task> tasks = new ArrayList<Task>();
		SaxDefaultHandler handler = new SaxDefaultHandler(tasks);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(file, handler);
		return tasks;
	}

}
