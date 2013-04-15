package ro.reanad.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/errorPage.htm")
public class ErrorPageController {
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView getErrorPage(@ModelAttribute("errorMessage")Exception ex) {
				return new ModelAndView("WEB-INF/jsp/errorPage.jsp","errorMesage",ex);
	}
	
}
