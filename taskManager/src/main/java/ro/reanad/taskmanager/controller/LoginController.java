package ro.reanad.taskmanager.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.service.LoginService;
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
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView login(@ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		try {
			new LoginValidator().validate(user, result);
			if (result.hasErrors()) {
				return new ModelAndView("index.jsp");
			}
			user = loginService.authenticate(user.getUsername(), user.getPassword());
			
			if (user != null) {
				logger.debug("User has just logged in", user.toString());
				session.setAttribute("user", user);
				return new ModelAndView("redirect:board.htm");
			} else {
				logger.debug("Loggin for user failed");		
				return new ModelAndView("index.jsp", "loginErrorMessage",
						"Wrong username or password!");
			}
		} catch (Exception e) {
			
			logger.error(e.getStackTrace().toString());
			return new ModelAndView("index.jsp", "loginErrorMessage",
					e.getMessage());

		}

	}

}