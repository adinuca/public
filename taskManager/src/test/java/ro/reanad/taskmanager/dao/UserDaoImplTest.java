package ro.reanad.taskmanager.dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ro.reanad.taskmanager.dao.exception.DuplicateUserException;
import ro.reanad.taskmanager.dao.exception.NoSuchUserException;
import ro.reanad.taskmanager.dao.exception.WrongPasswordException;
import ro.reanad.taskmanager.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:BeanLocations.xml" })
public class UserDaoImplTest {

	@Autowired
	UserDao userdao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCreateUser() throws DuplicateUserException, NoSuchUserException, WrongPasswordException {
		User u = new User("usernameTest", "passwordTest", "emailTest");
		userdao.createUser(u);
		User u2 = userdao.getUser("usernameTest","passwordTest");
		assertEquals(u, u2);
	}

	@Test(expected = DuplicateUserException.class)
	public void testDuplicateUser() throws DuplicateUserException {
		User u = new User("username1", "passwordTest", "email1");
		userdao.createUser(u);
		userdao.createUser(u);
	}
	
	@Test(expected = NoSuchUserException.class)
	public void testChangePasswordInvalidUser() throws DuplicateUserException, NoSuchUserException {
		userdao.changePassword("nosuchemail","password");
	}

	@Test
	public void testChangePassword() throws NoSuchUserException {
		userdao.changePassword("email1", "newpassword");
		User u = userdao.getUser("username1");
		assertEquals(u.getPassword(), "newpassword");
	}

	@Test
	public void testExistsUser() throws DuplicateUserException {
		User u = new User("usernameTes1t", "passwordTest1", "emailTest1");
		userdao.createUser(u);
		assertTrue(userdao.isUserWithEmail("emailTest1"));

		assertFalse(userdao.isUserWithEmail("NotExistentemailTest1"));
	}

	@Test(expected= NoSuchUserException.class)
	public void testGetInexistentUser() throws NoSuchUserException, WrongPasswordException{
		userdao.getUser("noSuchUser","password");
	}
	
	@Test(expected= WrongPasswordException.class)
	public void testGetUserWrongPassword() throws NoSuchUserException, WrongPasswordException{
		userdao.getUser("username1","passwordWrong");
	}
}
