package com.backend.project.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.project.dao.PaymentRepo;
import com.backend.project.entity.Payment;
import com.backend.project.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepo paymentRepo;
	
	@Transactional
	@Override
	public Iterable<Payment> getAllUserPayment() {
		return paymentRepo.findAll();
	}

	@Transactional
	@Override
	public Payment addPayment(Payment payment) {
		payment.setId(0);
		return paymentRepo.save(payment);
	}

	@Transactional
	@Override
	public void deletePayment(int paymentId) {
		Payment findPayment = paymentRepo.findById(paymentId).get();
		
		if(findPayment == null)
			throw new RuntimeException("Payment with ID " + paymentId + " does not exist.");
		
		paymentRepo.deleteById(paymentId);

	}

	@Transactional
	@Override
	public Iterable<Payment> getPendingPayment(String status) {		
		return paymentRepo.getPendingPayment(status);
	}
}