package com.backend.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.project.entity.Episode;
import com.backend.project.entity.Novel;
import com.backend.project.service.NovelService;

@RestController
@RequestMapping("/novel")
@CrossOrigin
public class NovelController {
	
	@Autowired
	private NovelService novelService;

	@GetMapping
	public Iterable<Novel> getAllNovels(){
		return novelService.getAllNovels();
	}
	
	@GetMapping("/category/{categoryId}")
	public Iterable<Novel> getNovelByCategory(@PathVariable int categoryId){
		return novelService.findMovieByCategory(categoryId);
	}
	
	@GetMapping("/{novelId}")
	public Optional<Novel> getNovelById(@PathVariable int novelId){
		return novelService.getNovelById(novelId);
	}
	
	@GetMapping("/ofUser")
	public Iterable<Novel> findNovelByUserId(@RequestParam int owner){
		return novelService.findNovelByUserId(owner);
	}
	
	@PostMapping("/{userId}")
	public Novel addNovel(@RequestBody Novel novel,@PathVariable int userId) {
		return novelService.addNovel(novel, userId);
	}
	
	@PutMapping
	public Novel updateNovel(@RequestBody Novel novel) {
		return novelService.updateNovel(novel);
	}
	
	@DeleteMapping("/{novelId}")
	public void deleteNovel(@PathVariable int novelId) {
		novelService.deleteNovel(novelId);
	}
	
	@GetMapping("/{novelId}/episode")
	public List<Episode> getEpisodeOfNovel(@PathVariable int novelId) {
		return novelService.getEpisodesOfNovel(novelId);
	}
	
	@PutMapping("/{novelId}/category/{categoryId}")
	public Novel addCategoryToNovel(@PathVariable int novelId, @PathVariable int categoryId) {
		return novelService.addCategoryToMovie(novelId, categoryId);
	}
	
	//button di admin buat jadiin novel free jadi premium
	@PatchMapping("/{novelId}")
	public Novel premiumNovel(@PathVariable int novelId) {
		return novelService.premiumNovel(novelId);
	}
}
