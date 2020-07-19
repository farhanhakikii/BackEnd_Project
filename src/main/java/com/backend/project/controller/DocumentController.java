package com.backend.project.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backend.project.dao.NovelRepo;
import com.backend.project.dao.PaymentRepo;
import com.backend.project.dao.UserRepo;
import com.backend.project.entity.Novel;
import com.backend.project.entity.Payment;
import com.backend.project.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/documents")
@CrossOrigin
public class DocumentController {
	private String uploadUser = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\profile\\";
	private String uploadNovel = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\novel\\";
	private String uploadPayment = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\payment\\";
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private NovelRepo novelRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@PatchMapping("/user/{id}")
	public String uploadUser(@RequestParam("file") MultipartFile file, @PathVariable int id) {
		Date date = new Date();
		
		User findUser = userRepo.findById(id).get();
			
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "USER-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadUser) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/user/")
				.path(fileName).toUriString();
		
		findUser.setImage(fileDownloadUri);
		userRepo.save(findUser);
				
		return fileName + " has been uploaded!";
	}
	
	@GetMapping("/user/{fileName:.+}")
	public ResponseEntity<Object> getUser(@PathVariable String fileName) {
		Path path = Paths.get(uploadUser + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PatchMapping("/novel/{id}")
	public String uploadNovel(@RequestParam("file") MultipartFile file, @PathVariable int id) {
		Date date = new Date();
		
		Novel findNovel = novelRepo.findById(id).get();
			
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "NOVEL-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadNovel) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/novel/")
				.path(fileName).toUriString();
		
		findNovel.setImage(fileDownloadUri);
		novelRepo.save(findNovel);
				
		return fileName + " has been uploaded!";
	}
	
	@GetMapping("/novel/{fileName:.+}")
	public ResponseEntity<Object> getNovel(@PathVariable String fileName) {
		Path path = Paths.get(uploadNovel + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping("/payment/{id}")
	public String uploadPayment(@PathVariable int id, @RequestParam("file") MultipartFile file, @RequestParam("paymentData") String paymentString) throws JsonMappingException, JsonProcessingException {
		Date date = new Date();
		
		Payment payment = new ObjectMapper().readValue(paymentString, Payment.class);
		User findUser = userRepo.findById(id).get();
			
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "PAYMENT-" + date.getTime() + "." + fileExtension;
		
		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get(StringUtils.cleanPath(uploadPayment) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/documents/payment/")
				.path(fileName).toUriString();
		
		findUser.setType("pending");
		payment.setImage(fileDownloadUri);
		payment.setStatus("pending");
		userRepo.save(findUser);
		paymentRepo.save(payment);
						
		return fileName + " has been uploaded!";
	}
	
	@GetMapping("/payment/{fileName:.+}")
	public ResponseEntity<Object> getPayment(@PathVariable String fileName) {
		Path path = Paths.get(uploadPayment + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}