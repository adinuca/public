package ro.reanad.taskmanager.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ro.reanad.taskmanager.service.ConstructXmlService;
import ro.reanad.taskmanager.service.TaskService;

@Controller
@SessionAttributes("user")
public class XmlController {
	private ConstructXmlService constructXmlService;
	private TaskService taskService;

	@Autowired
	public void setConstructXmlService(ConstructXmlService constructXmlService) {
		this.constructXmlService = constructXmlService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/exportXml.htm", method = RequestMethod.POST)
	public String exportXml(HttpServletResponse response,
			HttpServletRequest request) throws ServletException, IOException, ParserConfigurationException, TransformerException {
		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		try {
			stream = response.getOutputStream();
			File xml = constructXmlService.getXml(taskService
					.getAllTasksForUser((String) request.getSession()
							.getAttribute("user")));
			response.setContentType("text/xml");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ xml.getCanonicalPath());
			response.setContentLength((int) xml.length());
			FileInputStream input = new FileInputStream(xml);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1)
				stream.write(readBytes);
		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (stream != null)
				stream.close();
			if (buf != null)
				buf.close();
		}
		return "board.jsp";

	}
}
