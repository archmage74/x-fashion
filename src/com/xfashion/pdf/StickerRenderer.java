package com.xfashion.pdf;

import java.text.NumberFormat;

import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.shared.ArticleTypeDTO;

public class StickerRenderer {

	private ArticleTypeService articleTypeService;
	
	public StickerRenderer() {
		articleTypeService = new ArticleTypeServiceImpl();
	}

	public String renderHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-15\" />");
		sb.append(createStyles());
		sb.append("</head><body>");
		return sb.toString();
	}
	
	public String render(Long productNumber, NumberFormat nf) {
		return render(productNumber, nf, false);
	}
	
	public String render(Long productNumber, NumberFormat nf, boolean pagebreakBefore) {
		ArticleTypeDTO articleType = readArticleType(productNumber);
		
		StringBuffer sb = new StringBuffer();
		String pagebreak = "";
		if (pagebreakBefore) {
			pagebreak = "style=\"page-break-before:always;\"";
		}
		sb.append("<table class=\"articleCell\" " + pagebreak + ">");
		sb.append("<tr>");
		sb.append(createTd(readCategoryName(articleType.getCategoryId()), "articleUpLe"));
		sb.append(createTd(articleType.getName(), "articleUpCe"));
		sb.append(createTd(readColorName(articleType.getColorId()), "articleUpRi"));
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append(createTd(readStyleName(articleType.getStyleId()), "articleBoLe"));
		sb.append(createTd(readBrandName(articleType.getBrandId()), "articleBoCe"));
		sb.append(createTd(readSizeName(articleType.getSizeId()), "articleBoRi"));
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("<table class=\"articleCell\" style=\"width: 210px;\">");
		sb.append("<tr style=\"padding: 0px; margin: 0px;\">");
		sb.append("<td style=\"align: center; padding-top: 0px;\">");
		sb.append("<img width=\"142\" height=\"46\" src=\"http://intern.spielemarkt.at/spielemarkt_at/barcode.php?num=");
		sb.append(new BarcodeHelper().generateEan(articleType));
		sb.append("&type=ean13&imgtype=png\" />");
		sb.append("</td>");
		sb.append("<td class=\"articlePrice\">");
		sb.append(nf.format(((double) articleType.getSellPrice()) / 100));
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

		return sb.toString();
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
	
	private ArticleTypeDTO readArticleType(Long productNumber) {
		return articleTypeService.readArticleType(productNumber);
	}
	
	private String readCategoryName(Long id) {
		return articleTypeService.readCategory(id).getName();
	}
	
	private String readStyleName(String id) {
		return articleTypeService.readStyle(id).getName();
	}
	
	private String readBrandName(Long id) {
		return articleTypeService.readBrand(id).getName();
	}
	
	private String readColorName(Long id) {
		return articleTypeService.readColor(id).getName();
	}
	
	private String readSizeName(Long id) {
		return articleTypeService.readSize(id).getName();
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
			"	border-bottom: 1px solid silver;\n" +
			"}\n" +
			".articleUpCe {\n" +
			"	text-align: center;\n" + 
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-bottom: 1px solid silver;\n" +
			"	border-left: 1px solid silver;\n" +
			"	border-right: 1px solid silver;\n" + 
			"}\n" +
			".articleUpRi {\n" +
			"	text-align: center;\n" +
			"	width: 70px;\n" +
			"	height: 20px;\n" +
			"	border-bottom: 1px solid silver;\n" + 
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
			"	border-left: 1px solid silver;\n" +
			"	border-right: 1px solid silver;\n" + 
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
			"	font-size: 14px;\n" +
			"	font-weight: bold;\n" + 
			"}\n" +
			"");
		sb.append("</style>");
		return sb;
	}
	
}
