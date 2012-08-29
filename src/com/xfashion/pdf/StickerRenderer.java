package com.xfashion.pdf;

import java.text.NumberFormat;
import java.util.Locale;

import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.UserCountry;

public class StickerRenderer {

	private ArticleTypeService articleTypeService;
	private NumberFormat currencyFormat;
	
	public StickerRenderer() {
		this.articleTypeService = new ArticleTypeServiceImpl();
		this.currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
	}

	public String renderHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-15\" />");
		sb.append(createStyles());
		sb.append("</head><body>");
		return sb.toString();
	}
	
	public String render(Long productNumber, UserCountry country) {
		ArticleTypeDTO articleType = readArticleType(productNumber);
		return render(articleType, country, false);
	}
	
	public String render(String articleTypeKey, UserCountry country, boolean pagebreakBefore) {
		ArticleTypeDTO articleType = readArticleType(articleTypeKey);
		return render(articleType, country, pagebreakBefore);
	}
	
	public String render(ArticleTypeDTO articleType, UserCountry country, boolean pagebreakBefore) {
		
		StringBuffer sb = new StringBuffer();
		String pagebreak = "";
		if (pagebreakBefore) {
			pagebreak = "style=\"page-break-before:always;\"";
		}
		sb.append("<table class=\"articleCell\" " + pagebreak + ">");
		sb.append("<tr>");
		sb.append(createTd(readCategoryName(articleType.getCategoryKey()), "articleUpLe"));
		sb.append(createTd(articleType.getName(), "articleUpCe"));
		sb.append(createTd(readColorName(articleType.getColorKey()), "articleUpRi"));
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append(createTd(readStyleName(articleType.getStyleKey()), "articleBoLe"));
		sb.append(createTd(readBrandName(articleType.getBrandKey()), "articleBoCe"));
		sb.append(createTd(readSizeName(articleType.getSizeKey()), "articleBoRi"));
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("<table class=\"articleCell\" style=\"width: 203px;\">");
		sb.append("<tr style=\"padding: 0px; margin: 0px;\">");
		sb.append("<td style=\"align: center; padding-top: 0px;\">");
		sb.append("<img width=\"120\" height=\"46\" src=\"http://intern.spielemarkt.at/spielemarkt_at/barcode.php?num=");
		sb.append(new BarcodeHelper().generateArticleEan(articleType));
		sb.append("&type=ean13&imgtype=png\" />");
		sb.append("</td>");
		sb.append("<td class=\"articlePrice\">");
		sb.append(currencyFormat.format(((double) getSellPrice(articleType, country)) / 100));
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		return sb.toString();
	}
	
	private Integer getSellPrice(ArticleTypeDTO articleType, UserCountry country) {
		if (country == UserCountry.DE) {
			return articleType.getSellPriceDe();
		} else {
			return articleType.getSellPriceAt();
		}
	}
	
	public String renderFooter() {
		StringBuffer sb = new StringBuffer();
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	private StringBuffer createTd(String s, String style) {
		StringBuffer sb = new StringBuffer();
		sb.append("<td class=\"");
		sb.append(style);
		sb.append("\">");
		sb.append(s);
		sb.append("</td>");
		return sb;
	}
	
	private ArticleTypeDTO readArticleType(String key) {
		return articleTypeService.readArticleType(key);
	}
	
	private ArticleTypeDTO readArticleType(Long productNumber) {
		return articleTypeService.readArticleType(productNumber);
	}
	
	private String readCategoryName(String id) {
		return articleTypeService.readCategory(id).getName();
	}
	
	private String readStyleName(String id) {
		return articleTypeService.readStyle(id).getName();
	}
	
	private String readBrandName(String key) {
		return articleTypeService.readBrand(key).getName();
	}
	
	private String readColorName(String key) {
		return articleTypeService.readColor(key).getName();
	}
	
	private String readSizeName(String key) {
		return articleTypeService.readSize(key).getName();
	}
	
	private StringBuffer createStyles() {
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">");
		sb.append("" + 
			"body td {\n" +
			"	font-family: Arial Unicode MS, Arial, sans-serif;\n" +
			"	font-size: 11px;" +
			"}\n" +
			".articleCell {\n" +
			"	border-collapse: collapse;\n" +
			"	margin: 3px;\n" +
			"}\n" +
			".articleUpLe {\n" +
			"	text-align: center;\n" + 
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-bottom: 1px solid black;\n" +
			"}\n" +
			".articleUpCe {\n" +
			"	text-align: center;\n" + 
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-bottom: 1px solid black;\n" +
			"	border-left: 1px solid black;\n" +
			"	border-right: 1px solid black;\n" + 
			"}\n" +
			".articleUpRi {\n" +
			"	text-align: center;\n" +
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-bottom: 1px solid black;\n" + 
			"}\n" +
			".articleBoLe {\n" +
			"	text-align: center;\n" +
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"}\n" +
			".articleBoCe {\n" +
			"	text-align: center;\n" +
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-left: 1px solid black;\n" +
			"	border-right: 1px solid black;\n" + 
			"}\n" +
			".articleBoRi {\n" +
			"	text-align: center;\n" +
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"}\n" +
			".articlePrice {\n" +
			"	margin: 0;\n" +
			"	padding: 0;\n" +
			"	text-align: right;\n" +
			"	font-size: 18px;\n" +
			"	font-weight: bold;\n" + 
			"}\n" +
			"");
		sb.append("</style>");
		return sb;
	}
	
}
