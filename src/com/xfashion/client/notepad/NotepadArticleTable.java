package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.NotepadPriceCell;
import com.xfashion.shared.ArticleAmountDTO;

public class NotepadArticleTable extends ArticleTable<ArticleAmountDTO> {

	protected NotepadManagement notepadManagement;

	protected HighlightLastUpdated highlightLastUpdated;

	protected NotepadMatrixTemplates notepadTemplates;

	public NotepadArticleTable(IProvideArticleFilter provider) {
		super(provider);
		this.notepadTemplates = GWT.create(NotepadMatrixTemplates.class);
		this.highlightLastUpdated = new HighlightLastUpdated();
		this.notepadManagement = NotepadManagement.getInstance();
	}

	public String getLastUpdatedArticleTypeKey() {
		return highlightLastUpdated.getLastUpdatedArticleTypeKey();
	}

	public void setLastUpdatedArticleTypeKey(String lastUpdatedArticleTypeKey) {
		highlightLastUpdated.setLastUpdatedArticleTypeKey(lastUpdatedArticleTypeKey);
	}

	@Override
	protected IGetPriceStrategy<ArticleAmountDTO> currentPriceStrategy() {
		return notepadManagement.currentPriceStrategy();
	}

	@Override
	protected Column<ArticleAmountDTO, ArticleAmountDTO> createPriceColumn(ArticleDataProvider<ArticleAmountDTO> ap) {
		Column<ArticleAmountDTO, ArticleAmountDTO> price = new Column<ArticleAmountDTO, ArticleAmountDTO>(new NotepadPriceCell(ap,
				highlightLastUpdated)) {
			@Override
			public ArticleAmountDTO getValue(ArticleAmountDTO a) {
				return a;
			}
		};
		
		return price;
	}

}
