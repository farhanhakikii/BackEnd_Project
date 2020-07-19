package com.backend.project.controller;

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

import com.backend.project.entity.Episode;
import com.backend.project.service.EpisodeService;

@RestController
@RequestMapping("/episode")
@CrossOrigin
public class EpisodeController {
	
	@Autowired
	private EpisodeService episodeService;

	@GetMapping
	public Iterable<Episode> getAllEpisode(){
		return episodeService.getAllEpisode();
	}
	
	@GetMapping("/{episodeId}")
	public Optional<Episode> getEpisodeById(@PathVariable int episodeId){
		return episodeService.getEpisodeById(episodeId);
	}
	
	@GetMapping("/eps")
	public Optional<Episode> getEpisodeOfNovel(@RequestParam String novelNumber, @RequestParam int episodeNumber){
		return episodeService.getEpisodeOfNovel(novelNumber, episodeNumber);
	}
	
	@PostMapping("/{novelId}")
	public Episode addEpisode(@RequestBody Episode episode, @PathVariable int novelId) {
		return episodeService.addEpisode(episode, novelId);
	}
	
	@PutMapping
	public Episode updateEpisode(@RequestBody Episode episode) {
		return episodeService.updateEpisode(episode);
	}
	
	@DeleteMapping("/{episodeId}")
	public void deleteEpisode(@PathVariable int episodeId) {
		episodeService.deleteEpisode(episodeId);
	}
}