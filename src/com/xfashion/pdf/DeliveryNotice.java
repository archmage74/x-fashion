package com.xfashion.pdf;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.jdo.JDOObjectNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.client.notepad.NotepadService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.UserCountry;

public class DeliveryNotice extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String ARTICLE_ATTRIBUTE_NOT_AVAILABLE = "[n/a]";
	
	private ArticleTypeService articleTypeService;
	private BarcodeHelper barcodeHelper;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);

	@Override
	public void init() {
		articleTypeService = new ArticleTypeServiceImpl();
		barcodeHelper = new BarcodeHelper();
	}

	@Override
	public void destroy() {
		articleTypeService = null;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		DeliveryNoticeDTO dn = (DeliveryNoticeDTO) req.getSession().getAttribute(NotepadService.SESSION_DELIVERY_NOTICE);

		res.setContentType("text/html; charset=iso-8859-15");
		res.setCharacterEncoding("iso-8859-15");

		ServletOutputStream out = res.getOutputStream();

		out.print(renderAddresses(dn));
		out.print(renderTableHeader());
		int piecesSum = 0;
		int priceSum = 0;
		for (ArticleAmountDTO aa : dn.getNotepad().getArticles()) {
			piecesSum += aa.getAmount();
			ArticleTypeDTO at = readArticleType(aa.getArticleTypeKey());
			Integer ap = getPriceFromArticle(dn.getTargetShop(), at);
			if (ap != null) {
				priceSum += aa.getAmount() * ap;
			}
			out.print(renderArticle(dn.getTargetShop(), aa, at));
		}
		out.print(renderFooter(priceSum, piecesSum));
	}

	public String renderTableHeader() {
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
	
	public String renderAddresses(DeliveryNoticeDTO dn) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-15\" />");
		sb.append(createStyles());
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("\n");
		sb.append("<p>\n");
		sb.append("<table class=\"headerTable\">\n");
		sb.append("<tr>\n");
		sb.append("<td class=\"headline\">Lieferschein</td>\n");
		sb.append("<td class=\"date\">Datum: ");
		sb.append(dateFormat.format(dn.getNotepad().getCreationDate()));
		sb.append("</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td class=\"barcode\"><img width=\"200px\" height=\"50px\" src=\"http://intern.spielemarkt.at/spielemarkt_at/barcode.php?num=");
		sb.append(barcodeHelper.generateDeliveryNoticeEan(dn.getId()));
		sb.append("&type=ean13&imgtype=png\" /></td>\n");
		sb.append("<td class=\"logo\"><img width=\"400\" height=\"185\" src=\"/XFashion-LOGO.png\" /></td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td class=\"addressTo\">An:<br />\n");
		sb.append(renderAsAddress(dn.getTargetShop()));
		sb.append("</td>");
		sb.append("<td class=\"addressFrom\">");
		sb.append(renderAsAddress(dn.getSourceShop()));
		sb.append("</td>\n");
		sb.append("</tr>\n");
		sb.append("\n");
		sb.append("<tr>\n");
		sb.append("<td colspan=\"2\" class=\"articleTableHeader0\">Diese Lieferung beinhaltet</td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");

		sb.append("<table class=\"articleTable\" width=\"100%\" cellspacing=\"0\">\n");
		sb.append("<thead>\n");
		sb.append("<tr>\n");
		sb.append("<th class=\"hHeader hCategory\">Kategorie</td>\n");
		sb.append("<th class=\"hHeader hBrand\">Marke</td>\n");
		sb.append("<th class=\"hHeader hStyle\">Stil</td>\n");
		sb.append("<th class=\"hHeader hName\">Name</td>\n");
		sb.append("<th class=\"hHeader hColor\">Farbe</td>\n");
		sb.append("<th class=\"hHeader hSize\">Größe</td>\n");
		sb.append("<th class=\"hHeader hPrice\">VK</td>\n");
		sb.append("<th class=\"hHeader hPieces\">Stück</td>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n");
		sb.append("<tbody>\n");
		
		return sb.toString();
	}

	private StringBuffer renderAsAddress(ShopDTO shop) {
		StringBuffer sb = new StringBuffer();

		addAddressLine(sb, shop.getName(), null);
		addAddressLine(sb, shop.getStreet(), shop.getHousenumber());
		addAddressLine(sb, shop.getPostalcode(), shop.getCity());

		return sb;
	}

	private StringBuffer addAddressLine(StringBuffer sb, String s1, String s2) {
		boolean line = false;
		if (s1 != null && s1.length() != 0) {
			sb.append(s1);
			sb.append(" ");
			line = true;
		}
		if (s2 != null && s2.length() != 0) {
			sb.append(s2);
			line = true;
		}
		if (line) {
			sb.append("<br />\n");
		}
		return sb;
	}

	public String renderArticle(ShopDTO shop, ArticleAmountDTO aa, ArticleTypeDTO at) {
		StringBuffer sb = new StringBuffer();
		String price = formatPrice(getPriceFromArticle(shop, at));
		sb.append("<tr>\n");
		sb.append("<td class=\"articleCell1\">").append(readCategoryName(at.getCategoryKey())).append("</td>\n");
		sb.append("<td class=\"articleCell1\">").append(readBrandName(at.getBrandKey())).append("</td>\n");
		sb.append("<td class=\"articleCell1\">").append(readStyleName(at.getStyleKey())).append("</td>\n");
		sb.append("<td class=\"articleCell1\">").append(at.getName()).append("</td>\n");
		sb.append("<td class=\"articleCell1\">").append(readColorName(at.getColorKey())).append("</td>\n");
		sb.append("<td class=\"articleCell2\">").append(readSizeName(at.getSizeKey())).append("</td>\n");
		sb.append("<td class=\"articleCell3\">").append(price).append("</td>\n");
		sb.append("<td class=\"articleCell3\">").append(aa.getAmount()).append("</td>");
		sb.append("<td width=\"66%\">\n");
		sb.append("</tr>\n");
		return sb.toString();
	}

	private Integer getPriceFromArticle(ShopDTO s, ArticleTypeDTO a) {
		Integer price = a.getSellPriceAt();
		if (UserCountry.DE == s.getCountry()) {
			price = a.getSellPriceDe();
		}
		return price;
	}
	
	private String formatPrice(Integer price) {
		if (price == null) {
			return "n/a";
		} else {
			return currencyFormat.format(((price.doubleValue()) ) / 100);
		}
	}
	
	private String renderFooter(int priceSum, int piecesSum) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tfoot>\n");
		sb.append("<tr>\n");
		sb.append("<td colspan=\"8\">&nbsp;</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td colspan=\"5\"></td>\n");
		sb.append("<td class=\"articleTableSumLabel\">Gesamt:</td>\n");
		sb.append("<td class=\"articleTableSum\">").append(formatPrice(priceSum)).append("</td>\n");
		sb.append("<td class=\"articleTableSum\">").append(piecesSum).append("</td>\n");
		sb.append("</tr>\n");
		sb.append("</tfoot>\n");
		sb.append("</table>\n");
		sb.append("\n");
		sb.append("</body>\n");
		sb.append("</html>");
		return sb.toString();
	}

	private String createStyles() {
		String s = "<style type=\"text/css\">\n\n" +
		"body,td,th {\n" +
		"	font-family: Verdana;\n" +
		"	font-size: 24px;\n" +
		"}\n" +
		".headline {\n" +
		"	width: 50%;\n" +
		"	font-size: 48;\n" +
		"	font-family: Verdana;\n" +
		"	font-style: italic;\n" +
		"	font-weight: bolder;\n" +
		"	text-align: left;\n" +
		"}\n" +
		".date {\n" +
		"	font-style: italic;\n" +
		"	font-weight: bold;\n" +
		"	width: 50%;\n" +
		"	direction: rtl;\n" +
		"	text-align: right;\n" +
		"}\n" +
		".barcode {\n" +
		"	width: 50%;\n" +
		"	text-align: center;\n" +
		"}\n" +
		".logo {\n" +
		"	width: 50%;\n" +
		"	text-align: right;\n" +
		"}\n" +
		".addressTo {\n" +
		"	font-style: italic;\n" +
		"	width: 50%;\n" +
		"	text-align: left;\n" +
		"}\n" +
		".addressFrom {\n" +
		"	font-style: italic;\n" +
		"	width: 50%;\n" +
		"	text-align: right;\n" +
		"}\n" +
		".articleTableHeader0 {\n" +
		"	font-style: italic;\n" +
		"	font-weight: bold;\n" +
		"	width: 100%;\n" +
		"	text-align: center;\n" +
		"}\n" +
		".hHeader {\n" +
		"	font-weight: bold;\n" +
		"	font-size: 13px;\n" +
		"	border-bottom: 1px;\n" +
		"}\n" +
		".hCategory {\n" +
		"	text-align: left;\n" +
		"	width: 15%\n" +
		"}\n" +
		".hBrand {\n" +
		"	text-align: left;\n" +
		"	width: 13%\n" +
		"}\n" +
		".hStyle {\n" +
		"	text-align: left;\n" +
		"	width: 10%\n" +
		"}\n" +
		".hName {\n" +
		"	text-align: left;\n" +
		"	width: 13%\n" +
		"}\n" +
		".hColor {\n" +
		"	text-align: left;\n" +
		"	width: 15%\n" +
		"}\n" +
		".hSize {\n" +
		"	text-align: center;\n" +
		"	width: 10%\n" +
		"}\n" +
		".hPrice {\n" +
		"	text-align: right;\n" +
		"	width: 12%\n" +
		"}\n" +
		".hPieces {\n" +
		"	text-align: right;\n" +
		"	width: 12%\n" +
		"}\n" +
		"\n" +
		".articleTableHeader1 {\n" +
		"	font-weight: bold;\n" +
		"	font-size: 13px;\n" +
		"	border-bottom: 1px;\n" +
		"	text-align: left;\n" +
		"}\n" +
		".articleTableHeader2 {\n" +
		"	font-weight: bold;\n" +
		"	font-size: 13px;\n" +
		"	border-bottom: 1px;\n" +
		"	text-align: center;\n" +
		"}\n" +
		".articleTable {\n" +
		"	widht: 100%;\n" +
		"}\n" +
		".articleTable td {\n" +
		"	font-size: 13px;\n" +
		"}\n" +
		".articleCell1 {\n" +
		"	text-align: left;\n" +
		"}\n" +
		".articleCell2 {\n" +
		"	text-align: center;\n" +
		"}\n" +
		".articleCell3 {\n" +
		"	text-align: right;\n" +
		"}\n" +
		".articleTableSumLabel {\n" +
		"	border-top: 1px solid black;\n" +
		"	font-weight: bold;\n" +
		"	text-align: left;\n" +
		"}\n" +
		".articleTableSum {\n" +
		"	border-top: 1px solid black;\n" +
		"	font-weight: bold;\n" +
		"	text-align: right;\n" +
		"}\n" +
		"</style>\n";
		return s;
	}

	private ArticleTypeDTO readArticleType(String id) {
		return articleTypeService.readArticleType(id);
	}

	private String readCategoryName(String id) {
		try {
			return articleTypeService.readCategory(id).getName();
		} catch (JDOObjectNotFoundException e) {
			return ARTICLE_ATTRIBUTE_NOT_AVAILABLE;
		}
	}

	private String readStyleName(String id) {
		try {
			return articleTypeService.readStyle(id).getName();
		} catch (JDOObjectNotFoundException e) {
			return ARTICLE_ATTRIBUTE_NOT_AVAILABLE;
		}
	}

	private String readBrandName(String id) {
		try {
			return articleTypeService.readBrand(id).getName();
		} catch (JDOObjectNotFoundException e) {
			return ARTICLE_ATTRIBUTE_NOT_AVAILABLE;
		}
	}

	private String readColorName(String id) {
		try {
			return articleTypeService.readColor(id).getName();
		} catch (JDOObjectNotFoundException e) {
			return ARTICLE_ATTRIBUTE_NOT_AVAILABLE;
		}
	}

	private String readSizeName(String id) {
		try {
			return articleTypeService.readSize(id).getName();
		} catch (JDOObjectNotFoundException e) {
			return ARTICLE_ATTRIBUTE_NOT_AVAILABLE;
		}
	}

}