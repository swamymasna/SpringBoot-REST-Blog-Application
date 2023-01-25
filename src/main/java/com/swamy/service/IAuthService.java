package com.swamy.service;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;

public interface IAuthService {

	public String saveUser(RegisterDTO register);

	public String loginUser(LoginDTO login);
}
