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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.xmlparsing.SaxDefaultHandler;

@Service
public class SaxXmlParsingService implements
		XmlParsingService {

    private static final String SCHEMA_PATH = "/WEB-INF/classes/schema.xsd";
    private static final String HTTP_WWW_W3_ORG_2001_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    private Logger logger = Logger.getLogger(SaxXmlParsingService.class);
    
	public void validate(File file, String servletContextPath) throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance(HTTP_WWW_W3_ORG_2001_XML_SCHEMA);
		Schema schema = factory.newSchema(new StreamSource(servletContextPath
				+ SCHEMA_PATH));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(file));
		logger.debug("File "+file.getName()+" has been succesfully validated.");
	}

	@Override
	public List<Task> parseXml(File file, String servletContextPath)
			throws ParserConfigurationException, SAXException, IOException {
	    List<Task> tasks = new ArrayList<Task>();
	    SaxDefaultHandler handler = new SaxDefaultHandler(tasks);
		validate(file, servletContextPath);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(file, handler);
		return tasks;
	}



}
