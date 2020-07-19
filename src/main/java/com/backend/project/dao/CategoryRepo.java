package com.backend.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.project.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	@Query(value = "SELECT * FROM category WHERE category_name = ?1" , nativeQuery = true)
	public Optional<Category> findCategory(String categoryName);
}
