package ro.reanad.taskmanager.controller;

import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.UploadForm;
import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.UserService;
import ro.reanad.taskmanager.service.XmlImportService;

@Controller
@RequestMapping(value = "/upload.htm")
public class XmlImportController{
	private static final String UPLOAD_FILE_JSP = "WEB-INF/jsp/uploadFile.jsp";
	private static final String BOARD_HTM = "/board.htm";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String REDIRECT_ERROR_PAGE = "redirect:errorPage.htm";
	private static final String SUCCESS_JSP = "WEB-INF/jsp/success.jsp";
	private static final String USER = "user";
	@Autowired
	private XmlImportService xmlImportService;

	public void setXmlImportService(XmlImportService xmlImportService) {
		this.xmlImportService = xmlImportService;
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
		return UPLOAD_FILE_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processForm(
			@ModelAttribute(value = "FORM") UploadForm form,
			BindingResult result, HttpSession session) {

		if (!result.hasErrors()) {
			File xml = saveFile(form);
			User user = userService.getUserWithUsername((String) session
					.getAttribute(USER));
			String contextPath = session.getServletContext().getRealPath("/");
			try {
				xmlImportService.saveXmlContentInDatabase(xml, user,
						contextPath);
				return new ModelAndView(SUCCESS_JSP);
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView(REDIRECT_ERROR_PAGE,
						ERROR_MESSAGE, e);
			} finally {
				xml.delete();
			}
		}
		return new ModelAndView(BOARD_HTM);
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
}
