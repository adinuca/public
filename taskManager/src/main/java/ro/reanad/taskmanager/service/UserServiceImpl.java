package ro.reanad.taskmanager.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.reanad.taskmanager.dao.UserDao;
import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

@Service
public class UserServiceImpl implements UserService {
	private Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User authenticate(String username, String password)
			throws NoSuchUserException, WrongPasswordException {
		logger.debug("User tried to login " );
		return userDao.getUser(username, password);
	}

	@Override
	public void register(User user) throws DuplicateUserException {
		userDao.createUser(user);
	}

	@Override
	public User getUserWithUsername(String username){
		return userDao.getUser(username);
	}

}
