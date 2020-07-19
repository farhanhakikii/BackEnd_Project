package com.backend.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.project.entity.Category;
import com.backend.project.entity.Novel;
import com.backend.project.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Iterable<Category> getAllCategory(){
		return categoryService.getAllCategory();
	}
	
	@GetMapping("/{categoryId}")
	public Optional<Category> getCategoryById(@PathVariable int categoryId){
		return categoryService.getCategoryById(categoryId);
	}
	
	@GetMapping("/name")
	public Optional<Category> findCategory(@RequestParam String categoryName){
		return categoryService.findCategory(categoryName);
	}
	
	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}
	
	@PutMapping
	public Category updateCategory(@RequestBody Category category) {
		return categoryService.updateCategory(category);
	}
	
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable int categoryId) {
		categoryService.deleteCategory(categoryId);
	}
	
	@GetMapping("/{categoryId}/novels")
	public List<Novel> getNovelFromCategory(@PathVariable int categoryId) {
		return categoryService.getNovelFromCategory(categoryId);
	}
}
