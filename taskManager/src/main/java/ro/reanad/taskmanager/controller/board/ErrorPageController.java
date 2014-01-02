package ro.reanad.taskmanager.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/errorPage.htm")
public class ErrorPageController {

    private static final String ERROR_PAGE_JSP = "WEB-INF/jsp/errorPage.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    @RequestMapping(method = RequestMethod.GET)
	protected ModelAndView getErrorPage(@ModelAttribute(ERROR_MESSAGE)Exception ex) {
				return new ModelAndView(ERROR_PAGE_JSP, ERROR_MESSAGE,ex);
	}
	
}
