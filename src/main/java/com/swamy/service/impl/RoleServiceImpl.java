package com.swamy.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.dto.RoleDTO;
import com.swamy.entity.Role;
import com.swamy.repository.RoleRepository;
import com.swamy.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDTO saveRole(RoleDTO roleDTO) {
		Role role = modelMapper.map(roleDTO, Role.class);
		Role savedRole = roleRepository.save(role);
		return modelMapper.map(savedRole, RoleDTO.class);
	}

}
