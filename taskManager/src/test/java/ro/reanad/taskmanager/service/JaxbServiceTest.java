package ro.reanad.taskmanager.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;

import org.xml.sax.SAXException;

import ro.reanad.taskmanager.xmlparsing.XmlValidator;

@RunWith(MockitoJUnitRunner.class)
public class JaxbServiceTest {

	private JaxbService jaxbService;
	
	@Mock
	private XmlValidator xmlValidator;
	
	public void setup(){
		jaxbService = new JaxbService();
	}
	
/*	@Test
	public void testParseXmlForInvalidXml() throws ParserConfigurationException, SAXException, IOException {
		assertNull(jaxbService.parseXml(null, ""));
	}
*/	
	@Test(expected=SAXException.class)
	public void testValidationFails() throws SAXException, IOException, ParserConfigurationException{
		doThrow(new SAXException()).when(xmlValidator).validate(any(File.class), anyString());
		jaxbService.parseXml(any(File.class), anyString());
	}

	@Test
	public void testGetXml() {
		fail("Not yet implemented");
	}

}
/**
 * 		try {
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

 * 
 * */
 