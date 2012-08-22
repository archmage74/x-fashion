package com.xfashion.client.at.render;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.TextMessages;

public abstract class AbstractArticleTableCell<T> extends AbstractCell<T> {

	protected ArticleTableMatrixTemplates matrixTemplates = GWT.create(ArticleTableMatrixTemplates.class);
	
	protected TextMessages textMessages = GWT.create(TextMessages.class);
	
	protected Formatter formatter = Formatter.getInstance();
	
	public AbstractArticleTableCell(String... consumedEvents) {
		super(consumedEvents);
	}

}
