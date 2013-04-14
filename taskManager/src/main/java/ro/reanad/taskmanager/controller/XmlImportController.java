package ro.reanad.taskmanager.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.dao.exception.DuplicateGeneratedIdException;
import ro.reanad.taskmanager.model.Task;
import ro.reanad.taskmanager.model.UploadForm;
import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.TaskService;
import ro.reanad.taskmanager.service.UserService;
import ro.reanad.taskmanager.service.XmlParsingService;

@Controller
@RequestMapping(value = "/upload.htm")
public class XmlImportController implements HandlerExceptionResolver {
	private XmlParsingService xmlParsingService;
	private TaskService taskService;

	@Autowired
	public void setXmlParsingService(XmlParsingService xmlParsingService) {
		this.xmlParsingService = xmlParsingService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(ModelMap model) {
		UploadForm form = new UploadForm();

		model.addAttribute("FORM", form);
		return "WEB-INF/jsp/uploadFile.jsp";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "FORM") UploadForm form,
			BindingResult result, HttpSession session) {
		
		if (!result.hasErrors()) {
			File xml = saveFile(form);
			if(xml!=null){
				List<Task> tasks = xmlParsingService.parseXml(xml, session.getServletContext().getRealPath("/"));
				if(tasks!=null){
					User user = userService.getUserWithUsername((String)session.getAttribute("user"));
					saveTasks(tasks,user);
				}
			}
			saveFile(form);
			xml.delete();
			return "WEB-INF/jsp/success.jsp";
		} else {
			return "/upload.htm";
		}
	}

	private void saveTasks(List<Task> tasks, User user) {
		for(Task t:tasks){
			t.setUser(user);
			try {
				taskService.createTask(t);
			} catch (DuplicateGeneratedIdException e) {
				e.printStackTrace();
			}
			if(t.getSubTasks().size()!=0){
				saveTasks(t.getSubTasks(), user);
			}
		}
	}

	private File saveFile(UploadForm form) {
		FileOutputStream outputStream = null;
		String filePath = System.getProperty("java.io.tmpdir") + "/"
				+ form.getFile().getOriginalFilename();
		File xml = new File(filePath);
		try {
			outputStream = new FileOutputStream(xml);
			outputStream.write(form.getFile().getFileItem().get());
			outputStream.close();
		} catch (Exception e) {
			System.out.println("Error while saving file");
			return null;
		}
		return xml;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception exception) {
		Map<Object, Object> model = new HashMap<Object, Object>();
		if (exception instanceof MaxUploadSizeExceededException) {
			model.put(
					"errors",
					"File size should be less then "
							+ ((MaxUploadSizeExceededException) exception)
									.getMaxUploadSize() + " byte.");
		} else {
			model.put("errors", "Unexpected error: " + exception.getMessage());
		}
		model.put("FORM", new UploadForm());
		return new ModelAndView("board.htm", (Map) model);
	}
}
