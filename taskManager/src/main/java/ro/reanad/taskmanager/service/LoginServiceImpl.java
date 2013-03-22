package ro.reanad.taskmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.reanad.taskmanager.dao.UserDao;
import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

@Service
public class LoginServiceImpl implements LoginService {
	private Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User authenticate(String username, String password)
			throws NoSuchUserException, WrongPasswordException {
		logger.debug("User tried to login ",username );
		return userDao.getUser(username, password);
	}

	@Override
	public void register(User user) throws DuplicateUserException {
		userDao.createUser(user);
	}

}
