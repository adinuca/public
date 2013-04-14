package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;

public interface XmlParsingService {
	List<Task> parseXml(File f, String string) throws ParserConfigurationException, SAXException, IOException;
}
