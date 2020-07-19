package com.backend.project.service;

import java.util.List;
import java.util.Optional;

import com.backend.project.entity.Episode;
import com.backend.project.entity.Novel;

public interface NovelService {
	public Iterable<Novel> getAllNovels();
	public Optional<Novel> getNovelById(int novelId);
	public Novel addNovel(Novel novel, int userId);
	public Novel updateNovel(Novel novel);
	public void deleteNovel(int novelId);
	public List<Episode> getEpisodesOfNovel(int novelId);
	public Novel addCategoryToMovie(int novelId, int categoryId);
	public Iterable<Novel> findNovelByUserId(int owner);
	public Novel premiumNovel(int novelId);
	public Iterable<Novel> findMovieByCategory(int categoryId);
}