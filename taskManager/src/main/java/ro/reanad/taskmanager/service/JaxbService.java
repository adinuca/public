package ro.reanad.taskmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.Tasks;
import ro.reanad.taskmanager.xmlparsing.XmlValidator;

@Service
public class JaxbService implements XmlParsingService {

	Logger logger = Logger.getLogger(JaxbService.class);

	@Override
	public List<Task> parseXml(File file, String servletContextPath)
			throws ParserConfigurationException, SAXException, IOException {
		try {
			XmlValidator validator = new XmlValidator();
			validator.validate(file, servletContextPath);

			// create JAXB context and initializing Marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(Tasks.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// specify the location and name of xml file to be read
			// this will create Java object - country from the XML file

			Tasks tasks = (Tasks) jaxbUnmarshaller.unmarshal(file);

			return tasks.getTask();
		} catch (JAXBException e) {
			// some exception occured
			logger.error(e);
		}
		return null;
	}

	public File getXml(List<Task> tasksList)
			throws ParserConfigurationException, TransformerException {
		try {
			File f = new File("tasks.xml");
			Tasks tasks = new Tasks();
			tasks.setTask(tasksList);
			JAXBContext jaxbContext = JAXBContext.newInstance(Tasks.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(tasks, f);
			return f;

		} catch (JAXBException e) {
			// some exception occured
			logger.error(e);
		}
		return null;
	}
}
