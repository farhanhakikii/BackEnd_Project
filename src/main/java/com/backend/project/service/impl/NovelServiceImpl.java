package com.backend.project.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.project.dao.CategoryRepo;
import com.backend.project.dao.NovelRepo;
import com.backend.project.dao.UserRepo;
import com.backend.project.entity.Category;
import com.backend.project.entity.Episode;
import com.backend.project.entity.Novel;
import com.backend.project.entity.User;
import com.backend.project.service.NovelService;

@Service
public class NovelServiceImpl implements NovelService{
	
	@Autowired
	private NovelRepo novelRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Transactional
	@Override
	public Iterable<Novel> getAllNovels() {
		return novelRepo.findAll();
	}

	@Transactional
	@Override
	public Optional<Novel> getNovelById(int novelId) {
		return novelRepo.findById(novelId);
	}

	@Transactional
	@Override
	public Novel updateNovel(Novel novel) {
		Optional<Novel> findNovel = novelRepo.findById(novel.getId());
		
		if(findNovel.toString() == "Optional.empty")
			throw new RuntimeException("Novel with ID " + novel.getId() + " does not exist.");

		return novelRepo.save(novel);
	}

	@Transactional
	@Override
	public void deleteNovel(int novelId) {
		Novel findNovel = novelRepo.findById(novelId).get();
		
		if(findNovel == null)
			throw new RuntimeException("Novel with ID " + novelId + " does not exist.");
		
		findNovel.getCategories().forEach(category -> {
			List<Novel> categoryOfNovel = category.getNovel();
			categoryOfNovel.remove(findNovel);
			categoryRepo.save(category);
		});
		
		findNovel.setAuthor(null);
		novelRepo.save(findNovel);
		novelRepo.deleteById(novelId);
	}

	@Transactional
	@Override
	public Novel addNovel(Novel novel, int userId) {
		User findUser = userRepo.findById(userId).get();
		novel.setAuthor(findUser);
		novel.setId(0);
		return novelRepo.save(novel);
	}

	@Transactional
	@Override
	public List<Episode> getEpisodesOfNovel(int novelId) {
		Novel findNovel = novelRepo.findById(novelId).get();
		return findNovel.getEpisode();
	}

	@Transactional
	@Override
	public Novel addCategoryToMovie(int novelId, int categoryId) {
		Novel findNovel = novelRepo.findById(novelId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		findNovel.getCategories().add(findCategory);
		return novelRepo.save(findNovel);
	}

	@Transactional
	@Override
	public Iterable<Novel> findNovelByUserId(int owner) {
		return novelRepo.findNovelByUserId(owner);
	}

	@Transactional
	@Override
	public Novel premiumNovel(int novelId) {
		Novel findNovel = novelRepo.findById(novelId).get();
		findNovel.setType("premium");
		return novelRepo.save(findNovel);
	}

	@Transactional
	@Override
	public Iterable<Novel> findMovieByCategory(int categoryId) {
		return novelRepo.findNovelByCategory(categoryId);
	}
}