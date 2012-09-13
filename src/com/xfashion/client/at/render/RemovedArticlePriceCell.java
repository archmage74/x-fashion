package com.xfashion.client.at.render;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticlePriceCell extends AbstractArticleTableCell<RemovedArticleDTO> {

	protected IGetPriceStrategy<RemovedArticleDTO> priceStrategy;

	public RemovedArticlePriceCell(IGetPriceStrategy<RemovedArticleDTO> getPriceStrategy) {
		super();
		this.priceStrategy = getPriceStrategy;
	}
	
	@Override
	public void render(Context context, RemovedArticleDTO data, SafeHtmlBuilder sb) {
		sb.append(matrixTemplates.piecesCell(data.getAmount()));
		String priceString = formatter.centsToCurrencyOrUnknown(priceStrategy.getPrice(data));
		sb.append(matrixTemplates.priceCell(priceString));
		sb.append(matrixTemplates.dateTimeCell(textMessages.dateTime(data.getRemoveDate())));
	}

}
