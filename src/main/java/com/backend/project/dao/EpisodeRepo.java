package com.backend.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.project.entity.Episode;

public interface EpisodeRepo extends JpaRepository<Episode, Integer>{
	@Query(value = "SELECT * FROM episode WHERE novel_number = ?1 AND episode_number = ?2" , nativeQuery = true)
	public Optional<Episode> getEpisodeOfNovel(String novelNumber, int episodeNumber);
}
