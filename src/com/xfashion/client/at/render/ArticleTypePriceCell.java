package com.xfashion.client.at.render;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypePriceCell<T> extends TwoButtonsCell<T> {

	public ArticleTypePriceCell(ArticleDataProvider<T> provider) {
		super(provider);
	}

	@Override
	protected void buttonOnePressed(ArticleTypeDTO articleType) {
		Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(articleType, 1));
	}
	
	@Override
	protected void buttonTwoPressed(ArticleTypeDTO articleType) {
		Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(articleType, 10));
	}
	
	@Override
	protected String buttonOneText() {
		return textMessages.addOneToNotepadButton();
	}
	
	@Override
	protected String buttonTwoText() {
		return textMessages.addTenToNotepadButton();
	}

	@Override
	public void render(Context context, T data, SafeHtmlBuilder sb) {
		ArticleTypeDTO at = provider.retrieveArticleType(data);
		String atPriceString = formatter.centsToCurrencyOrUnknown(at.getSellPriceAt());
		sb.append(matrixTemplates.atPriceCell(atPriceString));
		String dePriceString = formatter.centsToCurrencyOrUnknown(at.getSellPriceDe());
		sb.append(matrixTemplates.dePriceCell(dePriceString));
		super.render(context, data, sb);
	}

}
