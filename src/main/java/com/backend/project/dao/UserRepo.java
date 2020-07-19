package com.backend.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.project.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query(value = "SELECT * FROM user WHERE username = ?1" , nativeQuery = true)
	public Optional<User> findUsername(String username);

	@Query(value = "SELECT * FROM user WHERE email = ?1" , nativeQuery = true)
	public Optional<User> findEmail(String email);
	
	@Query(value = "SELECT * FROM user WHERE role = ?1" , nativeQuery = true)
	public Iterable<User> findUserByRole(String role);

	public Optional<User> findByUsername(String username);
}
