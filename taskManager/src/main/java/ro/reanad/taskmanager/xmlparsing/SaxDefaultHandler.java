package ro.reanad.taskmanager.xmlparsing;

import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ro.reanad.taskmanager.dao.exception.WrongSubtaskException;
import ro.reanad.taskmanager.model.Task;

public class SaxDefaultHandler extends DefaultHandler {
    private Logger logger = Logger.getLogger(SaxDefaultHandler.class);
    
    protected List<Task> tasks = null;
    protected Task currentTask = null;
    protected String tmpValue;

    public SaxDefaultHandler(List<Task> tasks) {
        this.tasks=tasks;
    }

    @Override
    public void startElement(String uri, String localame, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("task")) {
            createTask();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tmpValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String element) throws SAXException {
        if (element.equals("task")) {
            try {
                goBackToParentTask();
            } catch (WrongSubtaskException e) {
                logger.error(e.getStackTrace());
            }
        }else
        if (element.equalsIgnoreCase("generatedId")) {
            currentTask.setGeneratedId(tmpValue);
        }else
        if (element.equalsIgnoreCase("name")) {
            currentTask.setName(tmpValue);
        }else
        if (element.equalsIgnoreCase("description")) {
            currentTask.setDescription(tmpValue);
        }else
        if (element.equalsIgnoreCase("category")) {
            currentTask.setCategory(tmpValue);
        }else
        /*
         * if (elementName.equalsIgnoreCase("dueDate")) {
         * currentTask.setDueDate(Calendar.getInstance().
         * attributes.getValue("dueDate")); }
         */if (element.equalsIgnoreCase("timeSpent")) {
            currentTask.setTimeSpent(Integer.parseInt(tmpValue));
        }else
        if (element.equalsIgnoreCase("url")) {
            currentTask.setUrl(tmpValue);
        }else
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
