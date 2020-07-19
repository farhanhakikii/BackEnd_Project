package com.backend.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.project.entity.Novel;
import com.backend.project.entity.User;
import com.backend.project.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public Iterable<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	public Optional<User> getUserById(@PathVariable int userId){
		return userService.getUserById(userId);
	}

	@GetMapping("/un")
	public Optional<User> findUsername(@RequestParam String username) {
		return userService.findUsername(username);
	}
	
	@GetMapping("/em")
	public Optional<User> findEmail(@RequestParam String email) {
		return userService.findEmail(email);
	}
	
	@GetMapping("/kl")
	public User keepLogin(@RequestParam int id) {
		return userService.keepLogin(id);
	}

	@GetMapping("/login")
	public User userLogin(@RequestParam String username, @RequestParam String password) {
		return userService.userLogin(username, password);
	}
	
	@GetMapping("/{userId}/novel") 
	public List<Novel> getNovelsOfUser(@PathVariable int userId) { 
		return userService.getNovelofUser(userId);
	}
	
	@GetMapping("/verify/{username}")
	public String verifyEmail(@PathVariable String username, @RequestParam String token) {
		return userService.verifyEmail(username, token);
	}
	
	@GetMapping("/role")
	public Iterable<User> getUserByRole(@RequestParam String role){
		return userService.getUserByRole(role);
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@PutMapping
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
	}
	
	//buat jadiin user premium, lokasi di tombol konfirm pembayaran dari user
	@PatchMapping("/{userId}")
	public User premiumUser(@PathVariable int userId) {
		return userService.premiumUser(userId);
	}

	//user premium gagal, lokasi di tombol reject pembayaran dari user
	@PatchMapping("/{userId}/failed")
	public void premiumUserFail(@PathVariable int userId){
		userService.premiumUserFail(userId);
	}
	
	//updatepassword
	@PatchMapping("/{userId}/password")
	public User updatePassword(@PathVariable int userId, @RequestParam String password, @RequestParam String newPassword) {
		return userService.updatePassword(userId, password, newPassword);
	}
	
	@PatchMapping("/{userId}/setNewPassword")
	public User newForgetPassword(@PathVariable int userId, @RequestParam String password) {
		return userService.setNewPassword(userId, password);
	}
	
	@PatchMapping("/forget")
	public User forgetPassword(@RequestParam String username) {
		return userService.forgetPassword(username);
	}
}