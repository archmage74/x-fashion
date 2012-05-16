package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadRemoveArticleEvent extends Event<NotepadRemoveArticleHandler> {

	public static Type<NotepadRemoveArticleHandler> TYPE = new Type<NotepadRemoveArticleHandler>();
	
	private ArticleTypeDTO articleType;
	private Integer amount;

	public NotepadRemoveArticleEvent(ArticleTypeDTO articleType) {
		this(articleType, 1);
	}

	public NotepadRemoveArticleEvent(ArticleTypeDTO articleType, Integer amount) {
		this.articleType = articleType;
		this.amount = amount;
	}
	
	@Override
	public Type<NotepadRemoveArticleHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(NotepadRemoveArticleHandler handler) {
		handler.onNotepadRemoveArticle(this);
	}

	public void setArticleType(ArticleTypeDTO articleType) {
		this.articleType = articleType;
	}

	public ArticleTypeDTO getArticleType() {
		return articleType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
