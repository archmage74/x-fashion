package com.xfashion.client.notepad;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadAddArticleEvent extends Event<NotepadAddArticleHandler> {

	public static Type<NotepadAddArticleHandler> TYPE = new Type<NotepadAddArticleHandler>();
	
	private ArticleTypeDTO articleType;
	
	public NotepadAddArticleEvent(ArticleTypeDTO articleType) {
		setArticleType(articleType);
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
	
}
