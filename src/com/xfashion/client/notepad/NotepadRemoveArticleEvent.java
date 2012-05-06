package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadRemoveArticleEvent extends Event<NotepadRemoveArticleHandler> {

	public static Type<NotepadRemoveArticleHandler> TYPE = new Type<NotepadRemoveArticleHandler>();
	
	private ArticleTypeDTO articleType;
	
	public NotepadRemoveArticleEvent(ArticleTypeDTO articleType) {
		setArticleType(articleType);
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
	
}
