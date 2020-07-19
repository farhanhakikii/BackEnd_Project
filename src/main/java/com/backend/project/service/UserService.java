package com.backend.project.service;

import java.util.List;
import java.util.Optional;

import com.backend.project.entity.User;
import com.backend.project.entity.Novel;

public interface UserService {
	public Iterable<User> getAllUsers();
	public Optional<User> getUserById(int userId);
	public User addUser(User user);
	public User updateUser(User user);
	public void deleteUser(int userId);
	public List<Novel> getNovelofUser(int userId);
	public User userLogin(String username, String password);
	public String verifyEmail(String username, String token);
	public User updatePassword(int userId, String password, String newPassword);
	public User forgetPassword(String username);
	public User setNewPassword(int userId, String newPassword);
	public Optional<User> findUsername(String username);
	public Optional<User> findEmail(String email);
	public User keepLogin(int id);
	public Iterable<User> getUserByRole(String role);
	public User premiumUser(int userId);
	public void premiumUserFail(int userId);	
}