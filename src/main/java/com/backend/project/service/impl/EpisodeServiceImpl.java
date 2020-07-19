package com.backend.project.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.project.dao.EpisodeRepo;
import com.backend.project.dao.NovelRepo;
import com.backend.project.entity.Episode;
import com.backend.project.entity.Novel;
import com.backend.project.service.EpisodeService;

@Service
public class EpisodeServiceImpl implements EpisodeService{
	
	@Autowired
	private EpisodeRepo episodeRepo;
	
	@Autowired
	private NovelRepo novelRepo;

	@Transactional
	@Override
	public Iterable<Episode> getAllEpisode() {
		return episodeRepo.findAll();
	}

	@Transactional
	@Override
	public Optional<Episode> getEpisodeById(int episodeId) {
		return episodeRepo.findById(episodeId);
	}

	@Transactional
	@Override
	public Episode updateEpisode(Episode episode) {
		Optional<Episode> findEpisode = episodeRepo.findById(episode.getId());
		
		if(findEpisode.toString() == "Optional.empty")
			throw new RuntimeException("Episode with ID " + episode.getId() + " does not exist.");
		
		return episodeRepo.save(episode);
	}

	@Transactional
	@Override
	public void deleteEpisode(int episodeId) {
		Episode findEpisode = episodeRepo.findById(episodeId).get();
		
		if(findEpisode == null)
			throw new RuntimeException("Episode with ID " + episodeId + " does not exist.");
		
		episodeRepo.deleteById(episodeId);
	}
	
	@Transactional
	@Override
	public Episode addEpisode(Episode episode, int novelId) {
		Novel findNovel = novelRepo.findById(novelId).get();
		episode.setNovel(findNovel);
		episode.setId(0);
		return episodeRepo.save(episode);
	}

	@Transactional
	@Override
	public Optional<Episode> getEpisodeOfNovel(String novelNumber, int episodeNumber) {
		return episodeRepo.getEpisodeOfNovel(novelNumber, episodeNumber);
	}	
}