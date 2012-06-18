package com.xfashion.pdf;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.client.notepad.NotepadService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;

public class DeliveryNotice extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		
		out.print(renderHeader(dn));
		int sum = 0;
		for (ArticleAmountDTO aa : dn.getNotepad().getArticles()) {
			sum += aa.getAmount();
			out.print(renderArticle(aa));
		}
		out.print(renderFooter(sum));
	}
	
	public String renderHeader(DeliveryNoticeDTO dn) {
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
		sb.append(renderAsAddress(dn.getTargetShop().getDescription()));
		sb.append("</td>");
		sb.append("<td class=\"addressFrom\">");
		sb.append(renderAsAddress(dn.getSourceShop().getDescription()));
		sb.append("</td>\n");
		sb.append("</tr>\n");
		sb.append("\n");
		sb.append("<tr>\n");
		sb.append("<td colspan=\"2\" class=\"articleTableHeader\">Diese Lieferung beinhaltet</td>\n");
		sb.append("</tr>\n");
		sb.append("\n");
		sb.append("<tr>\n");
		sb.append("<td colspan=\"2\" width=\"100%\">\n");
		sb.append("<table class=\"articleTable\" width=\"100%\">\n");
		return sb.toString();
	}
	
	private StringBuffer renderAsAddress(String shopDescription) {
		StringBuffer sb = new StringBuffer();
		StringTokenizer t = new StringTokenizer(shopDescription, "\n");
		while (t.hasMoreElements()) {
			sb.append(t.nextToken()).append("<br />\n");
		}
		return sb;
	}

	public String renderArticle(ArticleAmountDTO aa) {
		ArticleTypeDTO at = readArticleType(aa.getArticleTypeKey());
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>\n");
		sb.append("<td width=\"66%\">\n");
		sb.append("<table class=\"articleMatrix\" >\n");
		sb.append("<tr>\n");
		sb.append("<td class=\"articleUpLe\">").append(readCategoryName(at.getCategoryKey())).append("</td>\n");
		sb.append("<td class=\"articleUpCe\">").append(at.getName()).append("</td>\n");
		sb.append("<td class=\"articleUpRi\">").append(readColorName(at.getColorKey())).append("</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td class=\"articleBoLe\">").append(readStyleName(at.getStyleKey())).append("</td>\n");
		sb.append("<td class=\"articleBoCe\">").append(readBrandName(at.getBrandKey())).append("</td>\n");
		sb.append("<td class=\"articleBoRi\">").append(readSizeName(at.getSizeKey())).append("</td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("</td>\n");
		sb.append("<td class=\"price\">").append(currencyFormat.format(((double) at.getSellPrice()) / 100)).append("</td>\n");
		sb.append("<td class=\"pieces\">").append(aa.getAmount()).append("</td><td class=\"piecesLabel\">Stk.</td>\n");
		sb.append("</tr>\n");
		return sb.toString();
	}
	
	private String renderFooter(int sum) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>\n");
		sb.append("<td></td><td class=\"piecesSumHeader\">Gesamt:</td>\n");
		sb.append("<td class=\"piecesSum\">").append(sum).append("</td><td class=\"piecesSumLabel\">Stk.</td>\n");
		sb.append("</tr>\n");
		sb.append("\n");
		sb.append("</table>\n");
		sb.append("\n");
		sb.append("</td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("\n");
		sb.append("</body>\n");
		sb.append("</html>");
		return sb.toString();
	}

	private StringBuffer createStyles() {
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">\n");
		sb.append("body td {\n");
		sb.append("	font-family: Verdana;\n");
		sb.append("	font-size: 24px;\n");
		sb.append("}\n");
		sb.append(".headline {\n");
		sb.append("	width: 50%;\n");
		sb.append("	font-size: 48;\n");
		sb.append("	font-family: Verdana;\n");
		sb.append("	font-style: italic;\n");
		sb.append("	font-weight: bolder;\n");
		sb.append("	text-align: left;\n");
		sb.append("}\n");
		sb.append(".date {\n");
		sb.append("	font-style: italic;\n");
		sb.append("	font-weight: bold;\n");
		sb.append("	width: 50%;\n");
		sb.append("	direction: rtl;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".barcode {\n");
		sb.append("	width: 50%;\n");
		sb.append("	text-align: center;\n");
		sb.append("}\n");
		sb.append(".logo {\n");
		sb.append("	width: 50%;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".addressTo {\n");
		sb.append("	font-style: italic;\n");
		sb.append("	width: 50%;\n");
		sb.append("	text-align: left;\n");
		sb.append("}\n");
		sb.append(".addressFrom {\n");
		sb.append("	font-style: italic;\n");
		sb.append("	width: 50%;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".articleTableHeader {\n");
		sb.append("	font-style: italic;\n");
		sb.append("	font-weight: bold;\n");
		sb.append("	width: 100%;\n");
		sb.append("	text-align: center;\n");
		sb.append("}\n");
		sb.append(".articleTable {\n");
		sb.append("	widht: 100%;\n");
		sb.append("}\n");
		sb.append(".articleMatrix {\n");
		sb.append("	border-collapse: collapse;\n");
		sb.append("	width: 95%;\n");
		sb.append("}\n");
		sb.append(".articleUpLe {\n");
		sb.append("	font-size: 18px;\n");
		sb.append("	font-weight: bolder;\n");
		sb.append("	font-style: italic;\n");
		sb.append("	text-transform: uppercase;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("	height: 20px;\n");
		sb.append("	border-bottom: 1px solid black;\n");
		sb.append("}\n");
		sb.append(".articleUpCe {\n");
		sb.append("	font-size: 16px;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("	border-bottom: 1px solid black;\n");
		sb.append("	border-left: 1px solid black;\n");
		sb.append("	border-right: 1px solid black;\n");
		sb.append("}\n");
		sb.append(".articleUpRi {\n");
		sb.append("	font-size: 16px;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("	border-bottom: 1px solid black;\n");
		sb.append("}\n");
		sb.append(".articleBoLe {\n");
		sb.append("	font-size: 16px;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("}\n");
		sb.append(".articleBoCe {\n");
		sb.append("	font-size: 16px;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("	border-left: 1px solid black;\n");
		sb.append("	border-right: 1px solid black;\n");
		sb.append("}\n");
		sb.append(".articleBoRi {\n");
		sb.append("	font-size: 16px;\n");
		sb.append("	text-align: center;\n");
		sb.append("	width: 33%;\n");
		sb.append("}\n");
		sb.append(".price {\n");
		sb.append("	width: 18%;\n");
		sb.append("	margin: 0;\n");
		sb.append("	padding: 0;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".pieces {\n");
		sb.append("	width: 8%;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".piecesLabel {\n");
		sb.append("	width: 8%;\n");
		sb.append("	text-align: left;\n");
		sb.append("}\n");
		sb.append(".piecesSumHeader {\n");
		sb.append("	font-style: italic;\n");
		sb.append("	font-weight: bold;\n");
		sb.append("	width: 18%;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".piecesSum {\n");
		sb.append("	font-weight: bold;\n");
		sb.append("	font-style: italic;\n");
		sb.append("	width: 8%;\n");
		sb.append("	text-align: right;\n");
		sb.append("}\n");
		sb.append(".piecesSumLabel {\n");
		sb.append("	font-weight: bold;\n");
		sb.append("	font-style: italic;\n");
		sb.append("	width: 8%;\n");
		sb.append("	text-align: left;\n");
		sb.append("}\n");
		sb.append("</style>\n");
		return sb;
	}

	private ArticleTypeDTO readArticleType(String key) {
		return articleTypeService.readArticleType(key);
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
	

}