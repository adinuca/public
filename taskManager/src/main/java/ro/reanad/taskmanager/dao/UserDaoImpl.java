package ro.reanad.taskmanager.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
	private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	@Transactional(rollbackFor = DuplicateUserException.class)
	public void createUser(User user) throws DuplicateUserException {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(user);
			logger.debug("Created user ", user.toString());
		} catch (ConstraintViolationException ex) {
			throw new DuplicateUserException(ex.getMessage(),
					ex.getCause());
		}
	}

	@Override
	public void changePassword(String email, String newPassword)
			throws NoSuchUserException {
		Session session = getSessionFactory().getCurrentSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("email", email)).uniqueResult();
		if (user == null)
			throw new NoSuchUserException("User with email: " + email
					+ "does not exist");
		user.setPassword(newPassword);
		session.saveOrUpdate(user);
		logger.debug("Changed password fo user ", user.toString());
	}

	@Override
	public boolean isUserWithEmail(String email) {
		if (getUserWithEmail(email) != null) {
			return true;
		}
		return false;
	}

	private User getUserWithEmail(String email) {
		Session session = getSessionFactory().getCurrentSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("email", email)).uniqueResult();
		return user;
	}

	@Override
	public User getUser(String username, String password)
			throws NoSuchUserException, WrongPasswordException {
		User user = getUser(username);
		if (user == null) {
			logger.debug("User does not exist ", username);
			throw new NoSuchUserException("User does not exist"+username);
		} else if (!user.getPassword().equals(password)) {
			logger.debug("Password is wrong for user ", username);
			throw new WrongPasswordException("Wrong password for user "+ username);
		}
		return user;
	}

	@Override
	public User getUser(String username) {
		Session session = getSessionFactory().getCurrentSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("username", username)).uniqueResult();
		return user;
	}
}
