package ro.reanad.taskmanager.dao;

import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

public interface UserDao {

	void createUser(User user) throws DuplicateUserException;

	void changePassword(String email, String password) throws NoSuchUserException;

	boolean isUserWithEmail(String email);

	User getUser(String username, String password) throws NoSuchUserException, WrongPasswordException;

	User getUser(String username);

}