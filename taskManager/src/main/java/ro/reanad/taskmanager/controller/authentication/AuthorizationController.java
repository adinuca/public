package ro.reanad.taskmanager.controller.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("index.htm")
public class AuthorizationController {
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView getLoggedUser(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					request.getSession()
							.setAttribute("user", cookie.getValue());
					return new ModelAndView("redirect:board.htm");
				}
			}
		}
		return new ModelAndView("index.jsp");
	}
}
