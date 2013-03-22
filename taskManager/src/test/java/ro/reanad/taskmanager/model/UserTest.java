package ro.reanad.taskmanager.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	static User u;

	@BeforeClass
	public static void setUp() {
		u = new User("", "");
	}

	@Test
	public void testHashCode() {
		u = new User("username", "password", "email");
		User u1 = new User("username", "password", "email");

		assertTrue(u.hashCode() == u1.hashCode());

		u1 = new User("username1", "password", "email");
		assertFalse(u.hashCode() == u1.hashCode());

		u1 = new User("username", "password", "email1");
		assertFalse(u.hashCode() == u1.hashCode());

		u1 = new User("username", "password1", "email");
		assertFalse(u.hashCode() == u1.hashCode());

		u = new User(null, null, null);
		u1 = new User(null, null, null);
		assertTrue(u.hashCode() == u1.hashCode());

		u = new User("", "", "");
		u1 = new User("", "", "");
		assertTrue(u.hashCode() == u1.hashCode());

	}

	@Test
	public void testGetUsername() {
		u = new User("username", "password");
		assertEquals("username", u.getUsername());

		u = new User("username1", "password1", "email1");
		assertEquals("username1", u.getUsername());

	}

	@Test
	public void testGetPassword() {
		u = new User("username", "password");
		assertEquals("password", u.getPassword());

		u = new User("username1", "password1", "email1");
		assertEquals("password1", u.getPassword());

	}

	@Test
	public void testSetPassword() {
		u.setPassword("passwordTest");
		assertEquals("passwordTest", u.getPassword());
		assertFalse("passwordTest1".equals(u.getUsername()));
	}

	@Test
	public void testGetEmail() {
		u = new User("username", "password");
		assertEquals(null, u.getEmail());

		u = new User("username1", "password1", "email1");
		assertEquals("email1", u.getEmail());
	}

	@Test
	public void testEqualsObject() {
		u = new User("username", "password");
		User u1 = new User("username", "password");
		assertTrue(u.equals(u1));
		assertTrue(u1.equals(u));

		u = new User("username", "password", "email");
		u1 = new User("username", "password", "email");
		assertTrue(u.equals(u1));
		assertTrue(u1.equals(u));

		u1 = new User("username1", "password", "email");
		assertFalse(u.equals(u1));

		u1 = new User("username", "password", "email1");
		assertFalse(u.equals(u1));

		u1 = new User("username", "password1", "email");
		assertFalse(u.equals(u1));

		String s = "user";
		assertFalse(s.equals(u));
		assertFalse(u.equals(s));

		assertTrue(u.equals(u));
		assertFalse(u.equals(null));

	}

	@Test
	public void testToString() {
		u = new User("username", "password");
		assertEquals("User [username=username, email=null]", u.toString());
		
		u = new User("username", "password", "email");
		assertEquals("User [username=username, email=email]", u.toString());

		u = new User(null, null, null);
		assertEquals("User [username=null, email=null]", u.toString());

		u = new User("", "", "");
		assertEquals("User [username=, email=]", u.toString());

		
	}

}
