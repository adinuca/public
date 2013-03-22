package ro.reanad.taskmanager.service;

import ro.reanad.taskmanager.model.User;


public interface LoginService {
	User authenticate(String username, String password) throws Exception;
	void register(User u) throws Exception;
	
}
