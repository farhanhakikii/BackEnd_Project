package com.backend.project.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Episode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String episodeTitle;
	private String episodeNumber;
	
	@Column(columnDefinition="TEXT")
	private String episodeContent;
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "novelId")
	private Novel novel;
	
	private int novelNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public String getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getEpisodeContent() {
		return episodeContent;
	}

	public void setEpisodeContent(String episodeContent) {
		this.episodeContent = episodeContent;
	}

	public Novel getNovel() {
		return novel;
	}

	public void setNovel(Novel novel) {
		this.novel = novel;
	}
	public int getNovelNumber() {
		return novelNumber;
	}

	public void setNovelNumber(int novelNumber) {
		this.novelNumber = novelNumber;
	}
}