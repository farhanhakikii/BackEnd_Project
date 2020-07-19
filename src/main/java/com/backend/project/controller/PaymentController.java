package com.backend.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.project.entity.Payment;
import com.backend.project.service.PaymentService;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public Iterable<Payment> getAllPayment(){
		return paymentService.getAllUserPayment();
	}
	
	@GetMapping("/pending")
	public Iterable<Payment> getPendingPayment(@RequestParam String status){
		return paymentService.getPendingPayment(status);
	}
	
	@PostMapping
	public Payment addPayment(@RequestBody Payment payment) {
		return paymentService.addPayment(payment);
	}
	
	@DeleteMapping("/{paymentId}")
	public void deletePayment(@PathVariable int paymentId) {
		paymentService.deletePayment(paymentId);
	}
}
