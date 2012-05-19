package com.xfashion.server;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.ArticleTypeDTO;

@PersistenceCapable
public class ArticleTypes {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Set<ArticleType> articleTypes;

	@Persistent
	Long idCounter;

	public ArticleTypes() {
		this.articleTypes = new HashSet<ArticleType>();
		this.idCounter = 0L;
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Set<ArticleTypeDTO> getDtos() {
		Set<ArticleTypeDTO> dtos = new HashSet<ArticleTypeDTO>(getArticleTypes().size());
		for (ArticleType o : getArticleTypes()) {
			ArticleTypeDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public Set<ArticleType> getArticleTypes() {
		return articleTypes;
	}

	public void setArticleTypes(Set<ArticleType> articleTypes) {
		this.articleTypes = articleTypes;
	}

	public Long getIdCounter() {
		return idCounter;
	}

	public void setIdCounter(Long idCounter) {
		this.idCounter = idCounter;
	}

}
