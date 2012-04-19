package com.xfashion.pdf;

import java.text.NumberFormat;
import java.util.Locale;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.server.ArticleType;
import com.xfashion.server.PMF;

public class Sticker extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=iso-8859-15");
		response.setCharacterEncoding("iso-8859-15");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			service2(pm, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getOutputStream().print(e.getMessage());
			} catch (Exception ex) {
			}
		}
	}

	private void service2(PersistenceManager pm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long productNumber = Long.parseLong(request.getParameter("productNumber"));
		ArticleType articleType = pm.getObjectById(ArticleType.class, productNumber);

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
		String html = render(articleType, currencyFormat);

		response.getOutputStream().print(html);
	}

	private String render(ArticleType articleType, NumberFormat nf) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-15\" />");
		sb.append(createStyles());
		sb.append("</head><body>");

		sb.append("<table class=\"articleCell\">");
		sb.append("<tr>");
		sb.append(createTd(articleType.getCategory(), "articleUpLe"));
		sb.append(createTd(articleType.getName(), "articleUpCe"));
		sb.append(createTd(articleType.getColor(), "articleUpRi"));
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append(createTd(articleType.getStyle(), "articleBoLe"));
		sb.append(createTd(articleType.getBrand(), "articleBoCe"));
		sb.append(createTd(articleType.getSize(), "articleBoRi"));
		sb.append("</tr>");
		sb.append("</table>");

		sb.append("<table class=\"articleCell\" style=\"width: 210px;\">");
		sb.append("<tr style=\"padding: 0px; margin: 0px;\">");
		sb.append("<td style=\"align: center; padding-top: 0px;\">");
		sb.append("<img width=\"109\" height=\"35\" src=\"http://intern.spielemarkt.at/spielemarkt_at/barcode.php?num=");
		sb.append(articleType.getProductNumber());
		sb.append("&type=ean13&imgtype=png\" />");
		sb.append("</td>");
		sb.append("<td class=\"articlePrice\">");
		sb.append(nf.format(((double) articleType.getPrice()) / 100));
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");

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
