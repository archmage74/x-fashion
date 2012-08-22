package com.xfashion.client.at.render;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.notepad.HighlightLastUpdated;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.notepad.event.NotepadRemoveArticleEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadPriceCell extends TwoButtonsCell<ArticleAmountDTO> {

	private HighlightLastUpdated highlightLastUpdated;

	public NotepadPriceCell(ArticleDataProvider<ArticleAmountDTO> provider, HighlightLastUpdated highlightLastUpdated) {
		super(provider);
		this.highlightLastUpdated = highlightLastUpdated;
	}

	@Override
	protected void buttonOnePressed(ArticleTypeDTO articleType) {
		Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(articleType, 1));
	}
	
	@Override
	protected void buttonTwoPressed(ArticleTypeDTO articleType) {
		Xfashion.eventBus.fireEvent(new NotepadRemoveArticleEvent(articleType, 10));
	}
	
	@Override
	protected String buttonOneText() {
		return textMessages.removeOneFromNotepadButton();
	}
	
	@Override
	protected String buttonTwoText() {
		return textMessages.removeTenFromNotepadButton();
	}

	@Override
	public void render(Context context, ArticleAmountDTO data, SafeHtmlBuilder sb) {
		Integer price = NotepadManagement.getInstance().currentPriceStrategy().getPrice(data);
		String priceString = formatter.formatCentsToCurrencyOrUnknown(price);
		if (highlightLastUpdated.isLastUpdated(data)) {
			sb.append(matrixTemplates.piecesHighlightedCell(data.getAmount()));
		} else {
			sb.append(matrixTemplates.piecesCell(data.getAmount()));
		}
		sb.append(matrixTemplates.priceCell(priceString));
		super.render(context, data, sb);
	}

}
