package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadAddArticleEvent extends Event<NotepadAddArticleHandler> {

	public static Type<NotepadAddArticleHandler> TYPE = new Type<NotepadAddArticleHandler>();
	
	private ArticleTypeDTO articleType;
	private Integer amount;
	
	public NotepadAddArticleEvent(ArticleTypeDTO articleType) {
		this(articleType, 1);
	}
	
	public NotepadAddArticleEvent(ArticleTypeDTO articleType, Integer amount) {
		setArticleType(articleType);
		setAmount(amount);
	}
	
	@Override
	public Type<NotepadAddArticleHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(NotepadAddArticleHandler handler) {
		handler.onNotepadAddArticle(this);
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
