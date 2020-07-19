package com.backend.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.project.entity.Novel;

public interface NovelRepo extends JpaRepository<Novel, Integer>{
	@Query(value = "SELECT * FROM novel WHERE owner = ?1" , nativeQuery = true)
	public Iterable<Novel> findNovelByUserId(int owner);
	
	@Query(value = "SELECT * FROM novel as n inner join novel_category as nc on n.id=nc.novel_id WHERE nc.category_id = ?1" , nativeQuery = true)
	public Iterable<Novel> findNovelByCategory(int categoryId);

	@Query(value = "SELECT * FROM novel WHERE id = ?1" , nativeQuery = true)
	public void deleteNovel(int novelId);
}
