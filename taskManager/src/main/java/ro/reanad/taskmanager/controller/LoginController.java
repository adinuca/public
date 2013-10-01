package ro.reanad.taskmanager.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.UserService;
import ro.reanad.taskmanager.validators.LoginValidator;

/**
 * Controller handles the request and returnes the ModelAndView
 * 
 * @author adinuca
 * 
 */
@Controller
@RequestMapping("/login.htm")
public class LoginController {

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView login(@ModelAttribute("user") User user, BindingResult result, HttpSession session, HttpServletResponse response) {
		try {
			new LoginValidator().validate(user, result);
			if (result.hasErrors()) {
				return new ModelAndView("index.jsp");
			}

			if (user != null) {
				session.setAttribute("user", user.getUsername());
				Cookie cookie = new Cookie("user", user.getUsername());
				cookie.setMaxAge(1000);
				response. addCookie(cookie);
				return new ModelAndView("redirect:board.htm");
			} else {
				return new ModelAndView("index.jsp", "loginErrorMessage",
						"Wrong username or password!");
			}
		} catch (Exception e) {
			
			return new ModelAndView("index.jsp", "loginErrorMessage",
					e.getMessage());

		}

	}

}