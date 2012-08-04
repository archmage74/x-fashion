package com.xfashion.client.notepad;

import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.notepad.event.NotepadRemoveArticleEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadArticleTable extends ArticleTable<ArticleAmountDTO> {

	public static final long HIGHLIGHT_TIME = 5000;

	protected String lastUpdatedArticleTypeKey = null;
	protected Date lastUpdatedTime = null;

	public NotepadArticleTable(IProvideArticleFilter provider, GetPriceFromArticleAmountStrategy<ArticleAmountDTO> priceStrategy) {
		super(provider, priceStrategy);
	}

	public String getLastUpdatedArticleTypeKey() {
		return lastUpdatedArticleTypeKey;
	}

	public void setLastUpdatedArticleTypeKey(String lastUpdatedArticleTypeKey) {
		this.lastUpdatedArticleTypeKey = lastUpdatedArticleTypeKey;
		this.lastUpdatedTime = new Date();
	}

	protected void addNavColumns(CellTable<ArticleAmountDTO> cellTable) {
		Column<ArticleAmountDTO, SafeHtml> amount = new Column<ArticleAmountDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleAmountDTO a) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articleAmount", getAmountStyle(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				sb.append(a.getAmount());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(amount);

		Column<ArticleAmountDTO, String> notepadButton = new Column<ArticleAmountDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleAmountDTO at) {
				return textMessages.removeOneFromNotepadButton();
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO a, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
				Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(at));
			}
		});

		Column<ArticleAmountDTO, String> removeTenFromNotepadButton = new Column<ArticleAmountDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleAmountDTO at) {
				return textMessages.removeTenFromNotepadButton();
			}
		};
		cellTable.addColumn(removeTenFromNotepadButton);
		removeTenFromNotepadButton.setFieldUpdater(new FieldUpdater<ArticleAmountDTO, String>() {
			@Override
			public void update(int index, ArticleAmountDTO a, String value) {
				ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
				Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(at, 10));
			}
		});

	}

	protected String getAmountStyle(ArticleAmountDTO a) {
		Date now = new Date();
		if (lastUpdatedTime != null &&
				lastUpdatedArticleTypeKey != null &&
				lastUpdatedTime.getTime() + HIGHLIGHT_TIME > now.getTime() &&
				lastUpdatedArticleTypeKey.equals(a.getArticleTypeKey())) {
			return "articleAmountHighlighted";
		} else {
			return null;
		}
	}

}
