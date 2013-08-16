package ro.reanad.taskmanager.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Controller
@RequestMapping("/register.htm")
public class RegisterController {
    private Logger logger = LogManager.getLogger(RegisterController.class);
	@Autowired
	private UserService loginService;

	public void setLoginService(UserService loginService) {
		this.loginService = loginService;
	}

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView registre(@ModelAttribute("user") User user, BindingResult result) {
        new LoginValidator().validate(user, result);
        if (result.hasErrors()) {
            return new ModelAndView("index.jsp");
        } else {
            try {
                loginService.register(user);
            } catch (Exception e) {
                logger.error(e.getStackTrace());
            }
            return new ModelAndView("index.jsp");
        }

    }
}
