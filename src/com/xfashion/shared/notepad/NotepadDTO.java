package com.xfashion.shared.notepad;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadDTO implements IsSerializable {
	
	private Long id;
	
	private List<Long> articleTypes;

	public NotepadDTO() {
		articleTypes = new ArrayList<Long>();
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

	public void addArticleType(ArticleTypeDTO articleType) {
		getArticleTypes().add(articleType.getProductNumber());
	}
	
}
