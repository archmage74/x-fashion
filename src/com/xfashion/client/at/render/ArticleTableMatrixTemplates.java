package com.xfashion.client.at.render;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface ArticleTableMatrixTemplates extends SafeHtmlTemplates {

	@Template("<div class=\"matrixCell matrixUp matrixLe\">{0}</div>"
			+ "<div class=\"matrixCell matrixMi matrixLe\">{1}</div>"
			+ "<div class=\"matrixCell matrixBo matrixLe\">{2}</div>")
	SafeHtml cbsColumn(String category, String brand, String style);

	@Template("<div class=\"matrixCell matrixUp matrixLe matrixHighlighted\">{0}</div>"
			+ "<div class=\"matrixCell matrixMi matrixLe matrixHighlighted\">{1}</div>"
			+ "<div class=\"matrixCell matrixBo matrixLe matrixHighlighted\">{2}</div>")
	SafeHtml cbsHighlightedColumn(String category, String brand, String style);

	@Template("<div class=\"matrixCell matrixUp matrixCe\">{0}</div>"
			+ "<div class=\"matrixCell matrixMi matrixCe matrixBold\">{1}</div>"
			+ "<div class=\"matrixCell matrixBo matrixCe matrixBold\">{2}</div>")
	SafeHtml ncsColumn(String name, String color, String size);

	@Template("<div class=\"matrixCell matrixUp matrixCe matrixHighlighted\">{0}</div>"
			+ "<div class=\"matrixCell matrixMi matrixCe matrixBold matrixHighlighted\">{1}</div>"
			+ "<div class=\"matrixCell matrixBo matrixCe matrixBold matrixHighlighted\">{2}</div>")
	SafeHtml ncsHighlightedColumn(String name, String color, String size);

	@Template("<div class=\"matrixPlaceHolderLineUp\"></div>" + "<div class=\"matrixPlaceHolderLineBo\"></div>")
	SafeHtml placeHolderLineColumn();

	@Template("<div class=\"matrixCell matrixUp matrixRi\">" +
			"<span class=\"matrixCountryPrice\">{0}</span><span class=\"matrixPriceMarker\">AT</span></div>")
	SafeHtml atPriceCell(String price);

	@Template("<div class=\"matrixCell matrixMi matrixRi\">" +
			"<span class=\"matrixCountryPrice\">{0}</span><span class=\"matrixPriceMarker\">DE</span></div>")
	SafeHtml dePriceCell(String price);

	@Template("<div class=\"matrixCell matrixUp matrixRi\">" +
			"<span class=\"matrixPieces\">{0}</span><span class=\"matrixPiecesMarker\">STK</span></div>")
	SafeHtml piecesCell(Integer pieces);

	@Template("<div class=\"matrixCell matrixUp matrixRi matrixHighlighted\">" +
			"<span class=\"matrixPieces\">{0}</span><span class=\"matrixPiecesMarker\">STK</span></div>")
	SafeHtml piecesHighlightedCell(Integer pieces);

	@Template("<div class=\"matrixCell matrixMi matrixRi\"><span class=\"matrixPrice\">{0}</span></div>")
	SafeHtml priceCell(String price);

	@Template("<div class=\"matrixCell matrixMi matrixRi matrixHighlighted\"><span>{0}</span></div>")
	SafeHtml priceHighlightedCell(String price);

	@Template("<div class=\"matrixCell matrixBo matrixRi\">" +
			"<button class=\"matrixButton matrixNotepadButton\">{0}</button>" +
			"<button class=\"matrixButton matrixNotepadButton\">{1}</button></div>")
	SafeHtml twoButtonsCell(String buttonText1, String buttonText2);

	@Template("<div class=\"matrixCell matrixBo matrixRi matrixDateTime\">{0}</div>")
	SafeHtml dateTimeCell(String date);

	@Template("<div class=\"matrixCell matrixBo matrixRi\"></div>")
	SafeHtml emptyBoRiCell();

}
