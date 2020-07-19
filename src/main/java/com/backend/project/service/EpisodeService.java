package com.backend.project.service;

import java.util.Optional;

import com.backend.project.entity.Episode;

public interface EpisodeService {
	public Iterable<Episode> getAllEpisode();
	public Optional<Episode> getEpisodeById(int episodeId);
	public Episode addEpisode(Episode episode, int novelId);
	public Episode updateEpisode(Episode episode);
	public void deleteEpisode(int episodeId);
	public Optional<Episode> getEpisodeOfNovel(String novelNumber, int episodeNumber);
}