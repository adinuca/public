package ro.reanad.taskmanager.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import ro.reanad.taskmanager.dao.UserDao;
import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

public class UserServiceImplTest {
	private static UserServiceImpl loginService;
	private static UserDao mockUserDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockUserDao = Mockito.mock(UserDao.class);
		loginService = new UserServiceImpl();
		loginService.setUserDao(mockUserDao);
	}

	@Test
	public void testAuthenticate() throws NoSuchUserException,
			WrongPasswordException, DuplicateUserException {
		Mockito.when(mockUserDao.getUser("username", "password")).thenReturn(
				new User("username", "password", "email"));
		User u = loginService.authenticate("username", "password");
		Mockito.verify(mockUserDao).getUser("username", "password");
		assertEquals(u.getUsername(), "username");
		assertEquals(u.getPassword(), "password");
		assertEquals(u.getEmail(), "email");
	}

	@Test(expected = NoSuchUserException.class)
	public void testAuthenticateFailNoSuchUser() throws NoSuchUserException,
			WrongPasswordException {
		Mockito.when(mockUserDao.getUser("username2", "password")).thenThrow(
				new NoSuchUserException(null));
		loginService.authenticate("username2", "password");
	}

	@Test(expected = WrongPasswordException.class)
	public void testAuthenticateFailWrongPassword() throws NoSuchUserException,
			WrongPasswordException {
		Mockito.when(mockUserDao.getUser("username", "password1")).thenThrow(
				new WrongPasswordException(""));
		loginService.authenticate("username", "password1");
	}

	@Test
	public void testRegister() throws DuplicateUserException {
		User u = new User("username1", "password", "email1"); 
		loginService.register(u);
		Mockito.verify(mockUserDao).createUser(u);
	}

	@Test(expected = DuplicateUserException.class)
	public void testRegisterFail() throws DuplicateUserException {
		User u = new User("username1", "password1", "email1"); 
		Mockito.doThrow(DuplicateUserException.class).when(mockUserDao).createUser(u);
		loginService.register(u);
		Mockito.verify(mockUserDao).createUser(u);
	}

}
