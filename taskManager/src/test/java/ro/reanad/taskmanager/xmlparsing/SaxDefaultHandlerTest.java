package ro.reanad.taskmanager.xmlparsing;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import ro.reanad.taskmanager.model.Task;

public class SaxDefaultHandlerTest {

    static SaxDefaultHandler handler;
    @Mock
    static List<Task> tasks =Mockito.mock(List.class) ;
    
    @BeforeClass
    public static void setUp(){
        handler = new SaxDefaultHandler(tasks);
    }
    
    @Test
    public void testStartElementIsTask() throws SAXException {
        handler.startElement(null, null, "task", null);
        Mockito.verifyZeroInteractions(tasks);
    }
    
    @Test
    public void testStartElementIsNotTask() throws SAXException {
        handler.startElement(null, null, "", null);
        Mockito.verifyZeroInteractions(tasks);
    }

    @Test
    public void testEndElementIsNotTask() throws SAXException {
        handler.endElement(null, null, "");
        Mockito.verifyZeroInteractions(tasks);
    }

    @Test
    public void testEndElementIsTask() throws SAXException {
        handler.endElement(null, null, "task");
        Mockito.verify(tasks).add((Task) Mockito.any());
    }

}
