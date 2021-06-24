package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.UserDoesNotExistException;
import com.unq.edu.tpi.tip.backend.models.User;
import com.unq.edu.tpi.tip.backend.models.dtos.LoginDTO;
import com.unq.edu.tpi.tip.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional
public class AuthService
{
	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	public LoginDTO loginWithPassword(String username, String password) throws UserDoesNotExistException
	{
		String passwordEncoded = Base64.getEncoder().encodeToString(password.getBytes());

		User user = this.userRepository.findByUsernameAndPassword(username, passwordEncoded)
				.orElseThrow(UserDoesNotExistException::new);

		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(user.getUsername());
		loginDTO.setIsAdmin(user.getIsAdmin());
		return loginDTO;
	}
}
