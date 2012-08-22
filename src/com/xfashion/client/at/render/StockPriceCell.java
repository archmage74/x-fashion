package com.xfashion.client.at.render;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class StockPriceCell extends TwoButtonsCell<ArticleAmountDTO> {

	protected IGetPriceStrategy<ArticleAmountDTO> priceStrategy;
	
	public StockPriceCell(ArticleDataProvider<ArticleAmountDTO> provider, IGetPriceStrategy<ArticleAmountDTO> getPriceStrategy) {
		super(provider);
		this.priceStrategy = getPriceStrategy;
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
	public void render(Context context, ArticleAmountDTO data, SafeHtmlBuilder sb) {
		sb.append(matrixTemplates.piecesCell(data.getAmount()));
		Integer price = priceStrategy.getPrice(data);
		String priceString = formatter.formatCentsToCurrencyOrUnknown(price);
		sb.append(matrixTemplates.priceCell(priceString));
		super.render(context, data, sb);
	}

}
