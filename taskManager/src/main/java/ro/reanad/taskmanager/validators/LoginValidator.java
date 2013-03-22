package ro.reanad.taskmanager.validators;

import org.springframework.validation.Errors;

import ro.reanad.taskmanager.model.User;

public class LoginValidator {
	private static final String USERNAME_REGEX = "^[\\w]{3,20}$";
	private static final String PASSWORD_REGEX = "^[\\w]{6,20}$";

	public void validate(User user, Errors errors) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		if ((username == null) || (!username.matches(USERNAME_REGEX))) {
			errors.rejectValue("username", "username.required", "Username must have at least 3 alpha numeric characters.");
		}
		if ((password == null) ||(!password.matches(PASSWORD_REGEX))) {
			errors.rejectValue("password", "password.required", "Password must have at least 6 alpha numeric characters.");	
		}
		
	}
}