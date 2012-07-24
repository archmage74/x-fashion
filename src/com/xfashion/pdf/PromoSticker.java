package com.xfashion.pdf;

import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.server.PromoServiceImpl;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.PromoDTO;

public class PromoSticker extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected PromoServiceImpl promoService;
	protected BarcodeHelper barcodeHelper;
	NumberFormat currencyFormat;
	
	@Override
	public void init() {
		this.barcodeHelper = new BarcodeHelper();
		this.promoService = new PromoServiceImpl();
		this.currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
	}
	
	@Override
	public void destroy() {
		barcodeHelper = null;
		promoService = null;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=iso-8859-15");
		response.setCharacterEncoding("iso-8859-15");

		try {
			service2(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getOutputStream().print(e.getMessage());
			} catch (Exception ex) {
			}
		}
	}

	private void service2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String promoKey = request.getParameter("promoKey");
		Integer amount = Integer.parseInt(request.getParameter("amount"));

		PromoDTO promo = promoService.readPromo(promoKey);
		
		ServletOutputStream out = response.getOutputStream();
		
		out.print(renderHeader());
		out.print(renderBody(promo, amount));
		out.print(renderFooter());
	}

	public String renderHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-15\" />");
		sb.append("</head><body>");
		return sb.toString();
	}
	
	public String renderBody(PromoDTO promo, Integer amount) {
		StringBuffer sb = new StringBuffer();
		boolean firstpage = true;
		for (int i = 0; i<amount; i++) {
			sb.append(renderPromo(promo, firstpage));
			firstpage = false;
		}
		return sb.toString();
	}
	
	public String renderPromo(PromoDTO promo, boolean firstPage) {
		StringBuffer sb = new StringBuffer();
		
		if (!firstPage) {
			sb.append("<div style=\"page-break-before:always;\" />");
		}
		
		sb.append("<div style=\"width:120px; font-size:12px; text-align:center;\" >");
		if (promo.getPrice() != null) {
			sb.append(currencyFormat.format(((double) promo.getPrice()) / 100));
		} else {
			sb.append(promo.getPercent()).append("%");
		}
		sb.append("</div>");
		sb.append("<img width=\"120\" height=\"46\" src=\"http://intern.spielemarkt.at/spielemarkt_at/barcode.php?num=");
		sb.append(barcodeHelper.generatePromoEan(promo.getEan()));
		sb.append("&type=ean13&imgtype=png\" />");
		
		return sb.toString();
	}
	
	public String renderFooter() {
		StringBuffer sb = new StringBuffer();
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
}
