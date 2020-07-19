package com.backend.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.PERSIST, 
			CascadeType.REFRESH })
	@JoinTable(name = "novel_category", joinColumns = @JoinColumn(name = "category_Id"),
	        inverseJoinColumns = @JoinColumn(name = "novel_Id")) 
	@JsonIgnore
	private List<Novel> novel;
	
	private String categoryName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Novel> getNovel() {
		return novel;
	}

	public void setNovel(List<Novel> novel) {
		this.novel = novel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
