package com.unq.edu.tpi.tip.backend.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest
{
	@Test
	public void creationUserTest(){
		User user = new User();

		assertNull(user.getIsAdmin());
		assertNull(user.getPassword());
		assertNull(user.getUsername());
	}

	@Test
	public void creationUserWithValuesTestt(){
		User user = new User("admin", "password", true);

		assertEquals("admin", user.getUsername());
		assertEquals("password", user.getPassword());
		assertTrue(user.getIsAdmin());
	}
}
