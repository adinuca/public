package ro.reanad.taskmanager.xmlparsing;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;


public class XmlValidator {
	private static final String SCHEMA_PATH = "/WEB-INF/classes/schema.xsd";
	private static final String HTTP_WWW_W3_ORG_2001_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	private Logger logger = LogManager.getLogger(XmlValidator.class);

	public void validate(File file, String servletContextPath)
			throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory
				.newInstance(HTTP_WWW_W3_ORG_2001_XML_SCHEMA);
		Schema schema = factory.newSchema(new StreamSource(servletContextPath
				+ SCHEMA_PATH));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(file));
		logger.info("File "+file.getName()+"is a valid xml");
	}
}
