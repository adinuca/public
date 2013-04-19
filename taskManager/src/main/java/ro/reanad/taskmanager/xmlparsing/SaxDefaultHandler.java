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
    
    List<Task> tasks = null;
    private Task currentTask = null;
    private String tmpValue;

    public SaxDefaultHandler(List<Task> tasks) {
        this.tasks=tasks;
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("task")) {
            createTask();
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equals("task")) {
            try {
                goBackToParentTask();
            } catch (WrongSubtaskException e) {
                logger.error(e.getStackTrace());
            }
        }
        if (element.equalsIgnoreCase("generatedId")) {
            currentTask.setGeneratedId(tmpValue);
        }
        if (element.equalsIgnoreCase("name")) {
            currentTask.setName(tmpValue);
        }
        if (element.equalsIgnoreCase("description")) {
            currentTask.setDescription(tmpValue);
        }
        if (element.equalsIgnoreCase("category")) {
            currentTask.setCategory(tmpValue);
        }
        /*
         * if (elementName.equalsIgnoreCase("dueDate")) {
         * currentTask.setDueDate(Calendar.getInstance().
         * attributes.getValue("dueDate")); }
         */if (element.equalsIgnoreCase("timeSpent")) {
            currentTask.setTimeSpent(Integer.parseInt(tmpValue));
        }
        if (element.equalsIgnoreCase("url")) {
            currentTask.setUrl(tmpValue);
        }
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
