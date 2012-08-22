package com.xfashion.client.pricechange;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface PriceChangeMatrixTemplates extends SafeHtmlTemplates {

	@Template("<div class=\"matrixCell matrixBo matrixRi\"><button class=\"matrixButton\">Drucken</button></div>")
	SafeHtml printStickerCell();

	@Template("<div class=\"matrixCell matrixBo matrixRi matrixHighlighted\"><button class=\"matrixButton\">Drucken</button></div>")
	SafeHtml printStickerHighlightedCell();

}
