package ro.reanad.taskmanager.service;

import java.io.File;
import java.util.List;

import ro.reanad.taskmanager.model.Task;

public interface XmlParsingService {
	List<Task> parseXml(File f);
}
