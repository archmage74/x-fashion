package com.xfashion.server.notepad;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.notepad.NotepadDTO;

public class Notepad {

	public static final String ID_COUNTER_NAME = "ArticleType";
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private List<Long> articleTypes;

	public Notepad() {
		articleTypes = new ArrayList<Long>();
	}
	
	public Notepad(NotepadDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(NotepadDTO dto) {
		setArticleTypes(dto.getArticleTypes());
	}
	
	public NotepadDTO createDTO() {
		NotepadDTO dto = new NotepadDTO();
		dto.setArticleTypes(getArticleTypes());
		return dto;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getArticleTypes() {
		return articleTypes;
	}

	public void setArticleTypes(List<Long> articleTypes) {
		this.articleTypes = articleTypes;
	}
	
}
