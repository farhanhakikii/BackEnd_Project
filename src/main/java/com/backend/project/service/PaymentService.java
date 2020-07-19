package com.backend.project.service;

import com.backend.project.entity.Payment;

public interface PaymentService {
	public Iterable<Payment> getAllUserPayment();
	public Iterable<Payment> getPendingPayment(String status);
	public Payment addPayment(Payment payment);
	public void deletePayment(int paymentId);
}
