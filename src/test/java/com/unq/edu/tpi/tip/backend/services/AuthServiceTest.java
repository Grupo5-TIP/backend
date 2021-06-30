package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.UserDoesNotExistException;
import com.unq.edu.tpi.tip.backend.exceptions.UserMustHaveValuesException;
import com.unq.edu.tpi.tip.backend.models.User;
import com.unq.edu.tpi.tip.backend.models.dtos.LoginDTO;
import com.unq.edu.tpi.tip.backend.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest
{
	@InjectMocks
	private AuthService authService;

	@Mock
	private UserRepository userRepository;

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithEmptyUserAndPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("", "");
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithNonEmptyUserAndPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("username", "");
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithEmptyUserAndNonEmptyPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("", "password");
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithNullUserAndNonEmptyPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword(null, "password");
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithNonEmptyUserAndNullPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("username", null);
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithEmptyUserAndNullPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("", null);
	}

	@Test(expected = UserMustHaveValuesException.class)
	public void loginWithNullUserAndEmptyPasswordRaiseUserMustHaveValuesException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword(null, "");
	}

	@Test(expected = UserDoesNotExistException.class)
	public void loginWithValidUsernameAndPasswordButNotExistRaiseUserDoesNotExistException()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		authService.loginWithPassword("username", "password");

		verify(userRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
	}

	@Test
	public void loginWithValidUsernameAndPassword()
			throws UserDoesNotExistException, UserMustHaveValuesException
	{
		User userDTO = mock(User.class);
		when(userDTO.getIsAdmin()).thenReturn(true);
		when(userDTO.getUsername()).thenReturn("username");

		when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(userDTO));
		LoginDTO loginDTO = authService.loginWithPassword("username", "password");

		assertNotNull(loginDTO);
		assertEquals(loginDTO.getIsAdmin(), true);
		assertEquals(loginDTO.getUsername(), "username");
		verify(userRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
	}
}
