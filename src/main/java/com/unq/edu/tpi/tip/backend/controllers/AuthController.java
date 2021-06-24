package com.unq.edu.tpi.tip.backend.controllers;

import com.unq.edu.tpi.tip.backend.aspects.ExceptionAspect;
import com.unq.edu.tpi.tip.backend.models.dtos.LoginDTO;
import com.unq.edu.tpi.tip.backend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController
{
	private final AuthService authService;
	public AuthController(AuthService authService)
	{
		this.authService = authService;
	}

	@ExceptionAspect
	@PostMapping(path = "/login", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginDTO login)
			throws Exception
	{
		return new ResponseEntity<>(authService.loginWithPassword(login.getUsername().trim(),login.getPassword().trim()), HttpStatus.OK);
	}
}
