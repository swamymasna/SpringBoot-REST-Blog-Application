package com.swamy.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;
import com.swamy.entity.Role;
import com.swamy.entity.User;
import com.swamy.exception.BlogApiException;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.RoleRepository;
import com.swamy.repository.UserRepository;
import com.swamy.service.IAuthService;
import com.swamy.utils.AppConstants;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String saveUser(RegisterDTO register) {

		if (userRepository.existsByUsername(register.getUsername())) {
			throw new BlogApiException(AppConstants.USERNAME_EXISTS);
		} else if (userRepository.existsByEmail(register.getEmail())) {
			throw new BlogApiException(AppConstants.EMAIL_EXISTS);
		}

		User user = new User();
		user.setName(register.getName());
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(passwordEncoder.encode(register.getPassword()));

		Role role = roleRepository.findByName(AppConstants.ROLE_NAME).orElseThrow(
				() -> new ResourceNotFoundException(AppConstants.ROLE_NOT_FOUND + user.getUsername()));
		user.setRoles(Set.of(role));

		userRepository.save(user);

		return AppConstants.USER_REGISTRATION_SUCCESS;
	}

	@Override
	public String loginUser(LoginDTO login) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return AppConstants.USER_LOGIN_SUCCESS;
	}

}
