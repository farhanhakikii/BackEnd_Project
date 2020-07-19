package com.backend.project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.project.dao.PaymentRepo;
import com.backend.project.dao.UserRepo;
import com.backend.project.entity.Novel;
import com.backend.project.entity.Payment;
import com.backend.project.entity.User;
import com.backend.project.service.UserService;
import com.backend.project.util.EmailUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private EmailUtil emailUtil;

	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Transactional
	@Override
	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Transactional
	@Override
	public Optional<User> getUserById(int userId) {
		return userRepo.findById(userId);
	}

	@Transactional
	@Override
	public User addUser(User user) {
		String encodedPassword = pwEncoder.encode(user.getPassword());
		String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());

		user.setId(0);
		user.setPassword(encodedPassword);
		user.setVerifyToken(verifyToken);
		
		String linkToVerify = "http://localhost:8080/users/verify/" + user.getUsername() + "?token=" + verifyToken;
		  
		String message = "<h1>Selamat! Registrasi Berhasil</h1>\n"; 
		message += "Akun dengan username " + user.getUsername() + " telah terdaftar!\n"; 
		message += "Klik <a href=\"" + linkToVerify + "\">link ini</a> untuk verifikasi email anda.";
		
		emailUtil.sendEmail(user.getEmail(), "Registrasi Akun", message);
		
		return userRepo.save(user);
	}

	@Transactional
	@Override
	public User updateUser(User user) {
		Optional<User> findUser = userRepo.findById(user.getId());

		if(findUser.toString() == "Optional.empty")
			throw new RuntimeException("User with ID " + user.getId() + " does not exist.");
		
		return userRepo.save(user);
	}

	@Transactional
	@Override
	public void deleteUser(int userId) {
		User findUser = userRepo.findById(userId).get();
		
		if(findUser == null)
			throw new RuntimeException("User with ID " + userId + " does not exist.");
		
		userRepo.deleteById(userId);
	}

	@Transactional
	@Override
	public List<Novel> getNovelofUser(int userId) {
		User findUser = userRepo.findById(userId).get();
		return findUser.getNovel();
	}
	
	@Transactional
	@Override
	public User userLogin(String username, String password) {
		User findUser = userRepo.findByUsername(username).get();
		
		if (pwEncoder.matches(password, findUser.getPassword())) {	
			//findUser.setPassword(null);
			return findUser;
		} 

		throw new RuntimeException("Wrong Password!");
	}

	@Transactional
	@Override
	public String verifyEmail(String username, String token) {
		User findUser = userRepo.findByUsername(username).get();
		
		if(findUser.getVerifyToken().equals(token)) {
			findUser.setVerified(true);
		} else {
			throw new RuntimeException("Token is invalid");
		}
		
		String linkProfile = "http://localhost:3000/users/" + findUser.getId();
		
		String verifMessage = "Sukses!\n";
		verifMessage += "<a href=\"" + linkProfile + "\">Back To Profile</a>";
		
		userRepo.save(findUser);
		return verifMessage;
	}

	@Transactional
	@Override
	public User updatePassword(int userId, String password, String newPassword) {
		User findUser = userRepo.findById(userId).get();
		
		if(pwEncoder.matches(password, findUser.getPassword())) {
			String encodedNewPassword = pwEncoder.encode(newPassword);
			findUser.setPassword(encodedNewPassword);
			return userRepo.save(findUser);
		}
		
		throw new RuntimeException("Wrong Old Password!");
	}

	@Transactional
	@Override
	public User setNewPassword(int userId, String password) {
		User findUser = userRepo.findById(userId).get();
				
		if(findUser == null)
			throw new RuntimeException("User with ID " + userId + " does not exist.");
		
		String encodedNewPassword = pwEncoder.encode(password);
		findUser.setPassword(encodedNewPassword);
		return userRepo.save(findUser);
	}

	@Transactional
	@Override
	public User forgetPassword(String username) {
		User findUser = userRepo.findUsername(username).get();
		
		Random random = new Random();
		int forgetToken = random.nextInt();
		findUser.setForgetToken(forgetToken);
		
		String linkForget = "http://localhost:3000/users/" + findUser.getId() + "/lupapass/" + forgetToken;
		
		String message = "<h1>Berikut Link Untuk Password Baru Anda</h1>\n"; 
		message += "Klik <a href=\"" + linkForget + "\">New Password Page</a>";
		
		emailUtil.sendEmail(findUser.getEmail(), "Lupa Password", message);
		
		return userRepo.save(findUser);
	}

	@Transactional
	@Override
	public Optional<User> findUsername(String username) {
		return userRepo.findUsername(username);
	}

	@Transactional
	@Override
	public Optional<User> findEmail(String email) {
		return userRepo.findEmail(email);
	}

	@Transactional
	@Override
	public User keepLogin(int id) {
		return userRepo.findById(id).get();
	}

	@Transactional
	@Override
	public Iterable<User> getUserByRole(String role) {
		return userRepo.findUserByRole(role);
	}

	@Transactional
	@Override
	public User premiumUser(int userId) {		
		String message = "<h1>Selamat! Aktivasi Akun Premium Anda Berhasil</h1>\n";
		
		User findUser = userRepo.findById(userId).get();
		Payment findPayment = paymentRepo.getPaymentFromUserId(userId).get();
		
		String linkProfile = "http://localhost:3000/users/" + findUser.getId();
		message += "<a href=\"" + linkProfile + "\">Back To Profile</a>";
		
		findUser.setType("premium");
		findPayment.setStatus("completed");
		paymentRepo.save(findPayment);
		
		emailUtil.sendEmail(findUser.getEmail(), "Aktivasi Akun Premium", message);

		return userRepo.save(findUser);
	}

	@Transactional
	@Override
	public void premiumUserFail(int userId) {
		String message = "<h1>Mohon Maaf Terjadi Error. Silahkan Lakukan Upload Ulang.</h1>\n";

		User findUser = userRepo.findById(userId).get();
		Payment findPayment = paymentRepo.getPaymentFromUserId(userId).get();

		String linkProfile = "http://localhost:3000/users/" + findUser.getId();
		message += "<a href=\"" + linkProfile + "\">Back To Profile</a>";

		findUser.setType("free");
		findPayment.setStatus("failed");
		userRepo.save(findUser);
		paymentRepo.save(findPayment);
		
		emailUtil.sendEmail(findUser.getEmail(), "Aktivasi Akun Premium", message);
	}
}