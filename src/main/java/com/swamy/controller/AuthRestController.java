package com.swamy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;
import com.swamy.service.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	@Autowired
	private IAuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody RegisterDTO user) {
		return new ResponseEntity<String>(authService.saveUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDTO user) {
		return new ResponseEntity<String>(authService.loginUser(user), HttpStatus.CREATED);
	}
}
