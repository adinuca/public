package ro.reanad.taskmanager.service;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.xmlparsing.XmlValidator;

@RunWith(MockitoJUnitRunner.class)
public class JaxbServiceTest {
    @Mock
    private XmlValidator xmlValidator;
    @Mock
    private File xmlFile;
    
    private JaxbService jaxbService;
    @Before
    public void setUp() throws Exception {
        jaxbService = new JaxbService();
        jaxbService.setXmlValidator(xmlValidator);
    }

    @Test(expected= SAXException.class)
    public void testValidationFails() throws SAXException, IOException, ParserConfigurationException {
        doThrow(new SAXException()).when(xmlValidator).validate(any(File.class), anyString());
        jaxbService.parseXml(xmlFile, null);
    }

}
