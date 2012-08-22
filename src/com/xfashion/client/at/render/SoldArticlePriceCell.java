package com.xfashion.client.at.render;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticlePriceCell extends AbstractArticleTableCell<SoldArticleDTO> {

	protected IGetPriceStrategy<SoldArticleDTO> priceStrategy;

	public SoldArticlePriceCell(IGetPriceStrategy<SoldArticleDTO> getPriceStrategy) {
		super();
		this.priceStrategy = getPriceStrategy;
	}
	
	@Override
	public void render(Context context, SoldArticleDTO data, SafeHtmlBuilder sb) {
		sb.append(matrixTemplates.piecesCell(data.getAmount()));
		String priceString = formatter.formatCentsToCurrencyOrUnknown(priceStrategy.getPrice(data));
		sb.append(matrixTemplates.priceCell(priceString));
		sb.append(matrixTemplates.dateTimeCell(textMessages.dateTime(data.getSellDate())));
	}

}
