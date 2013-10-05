package ro.reanad.taskmanager.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author adinuca
 * 
 */
@Controller
@RequestMapping("logout.htm")
public class LogoutController {

    private static final String REDIRECT_INDEX_JSP = "redirect:index.jsp";

    @RequestMapping(method = RequestMethod.POST)
	protected ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		removeCookies(request);
		return new ModelAndView(REDIRECT_INDEX_JSP);
	}

	private void removeCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
			}

	}
}