package com.backend.project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.project.dao.CategoryRepo;
import com.backend.project.dao.NovelRepo;
import com.backend.project.entity.Category;
import com.backend.project.entity.Novel;
import com.backend.project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private NovelRepo novelRepo;
	
	@Transactional
	@Override
	public Iterable<Category> getAllCategory() {
		return categoryRepo.findAll();
	}

	@Transactional
	@Override
	public Optional<Category> getCategoryById(int categoryId) {
		return categoryRepo.findById(categoryId);
	}

	@Transactional
	@Override
	public Category addCategory(Category category) {
		category.setId(0);
		return categoryRepo.save(category);
	}

	@Transactional
	@Override
	public Category updateCategory(Category category) {
		Optional<Category> findCategory = categoryRepo.findById(category.getId());
		
		if(findCategory.toString() == "Optional.empty")
			throw new RuntimeException("Novel with ID " + category.getId() + " does not exist.");
			
		return categoryRepo.save(category);
	}

	@Transactional
	@Override
	public void deleteCategory(int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		if(findCategory == null)
			throw new RuntimeException("Novel with ID " + categoryId + " does not exist.");
		
		findCategory.getNovel().forEach(novel -> {
			Set<Category> novelCategory = novel.getCategories();	
			novelCategory.remove(findCategory);
			novelRepo.save(novel);
		});
	
		categoryRepo.deleteById(categoryId);
	}

	@Transactional
	@Override
	public List<Novel> getNovelFromCategory(int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		return findCategory.getNovel();
	}

	@Transactional
	@Override
	public Optional<Category> findCategory(String categoryName) {
		return categoryRepo.findCategory(categoryName);
	}
}