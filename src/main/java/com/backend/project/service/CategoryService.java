package com.backend.project.service;

import java.util.List;
import java.util.Optional;

import com.backend.project.entity.Category;
import com.backend.project.entity.Novel;

public interface CategoryService {
	public Iterable<Category> getAllCategory();
	public Optional<Category> getCategoryById(int categoryId);
	public Category addCategory(Category category);
	public Category updateCategory(Category category);
	public void deleteCategory(int categoryId);
	public List<Novel> getNovelFromCategory(int categoryId);
	public Optional<Category> findCategory(String categoryName);
}