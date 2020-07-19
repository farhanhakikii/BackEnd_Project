package com.backend.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.project.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer>{
	@Query(value = "SELECT * FROM payment WHERE user_id = ?1" , nativeQuery = true)
	public Optional<Payment> getPaymentFromUserId(int userId);
	
	@Query(value = "SELECT * FROM payment WHERE status = ?1" , nativeQuery = true)
	public Iterable<Payment> getPendingPayment(String status);
}
