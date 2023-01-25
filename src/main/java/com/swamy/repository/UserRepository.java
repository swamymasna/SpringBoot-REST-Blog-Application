package com.swamy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String name);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
