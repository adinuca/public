package ro.reanad.taskmanager.validators;

import org.springframework.validation.Errors;

import ro.reanad.taskmanager.model.User;

public class RegisterValidator {

	private static final String USERNAME_REGEX = "^[\\w]{3,20}$";
	private static final String PASSWORD_REGEX = "^[\\w]{6,20}$";
	private static final String EMAIL_REGEX = "^[\\w][\\w!#$%&'*+-/=?^_`{|}~]{1,20}['@'][a-z]{1,15}[.][a-z]{2,3}$";

	public void validate(User user, Errors errors) {
	
		if ((user.getUsername() == null) || (!user.getUsername().matches(USERNAME_REGEX))) {
			errors.rejectValue("username", "username.required",
					"Username must have at least 3 alpha numeric characters.");
		}
		if ((user.getPassword() == null) || (!user.getPassword().matches(PASSWORD_REGEX))) {
			errors.rejectValue("password", "password.required",
					"Password must have at least 6 alpha numeric characters.");
		}
		
		if ((user.getEmail() == null) || (!user.getEmail().matches(EMAIL_REGEX))) {
			errors.rejectValue("email", "email.required",
					"Email address must be valid.");
		}

	}
}
