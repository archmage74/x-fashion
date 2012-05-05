package com.xfashion.client.notepad;

import com.google.gwt.user.cellview.client.CellTable;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.shared.ArticleTypeDTO;

public class CountingArticleTable extends ArticleTable {

	public CountingArticleTable(ProvidesArticleFilter provider) {
		super(provider);
	}

	protected void addNavColumns(CellTable<ArticleTypeDTO> cellTable) {
		
	}
}
