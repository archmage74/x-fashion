package com.xfashion.server.notepad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.NotepadDTO;

@PersistenceCapable
public class Notepad {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	List<ArticleAmount> articles;

	public Notepad() {
		articles = new ArrayList<ArticleAmount>();
	}
	
	public Notepad(NotepadDTO dto) {
		this();
		updateFromDTO(dto);
	}

	public void updateFromDTO(NotepadDTO dto) {
		setName(dto.getName());
		setCreationDate(dto.getCreationDate());
		updateArticles(dto.getArticles());
	}

	public void updateArticles(List<ArticleAmountDTO> dtos) {
		List<ArticleAmount> toRemove = new ArrayList<ArticleAmount>(articles);
		List<ArticleAmount> toAdd = new ArrayList<ArticleAmount>();
		for (ArticleAmountDTO dto : dtos) {
			ArticleAmount item = null;
			if (dto.getKey() != null) {
				item = findItem(articles, dto);
			}
			if (item == null) {
				item = new ArticleAmount(dto);
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
		}
		for (ArticleAmount item : toRemove) {
			articles.remove(item);
		}
		articles.addAll(toAdd);
	}

	private ArticleAmount findItem(List<ArticleAmount> articles, ArticleAmountDTO dto) {
		for (ArticleAmount item : articles) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}

	
	public Key getKey() {
		return key;
	}
    
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<ArticleAmount> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleAmount> articles) {
		this.articles = articles;
	}

	public NotepadDTO createDTO() {
		NotepadDTO dto = new NotepadDTO();
		dto.setCreationDate(getCreationDate());
		dto.setKey(getKeyAsString());
		dto.setName(getName());
		dto.setArticles(new ArrayList<ArticleAmountDTO>(createArticleAmountDTOs()));
		return dto;
	}

	public NotepadDTO createFlatDTO() {
		NotepadDTO dto = new NotepadDTO();
		dto.setCreationDate(getCreationDate());
		dto.setKey(getKeyAsString());
		dto.setName(getName());
		dto.setArticles(null);
		return dto;
	}

	public List<ArticleAmountDTO> createArticleAmountDTOs() {
		List<ArticleAmountDTO> dtos = new ArrayList<ArticleAmountDTO>();
		for (ArticleAmount article : articles) {
			dtos.add(article.createDTO());
		}
		return dtos;
	}
}
