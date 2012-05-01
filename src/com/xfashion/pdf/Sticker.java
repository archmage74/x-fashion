package com.xfashion.pdf;

import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Sticker extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private StickerRenderer stickerRenderer;
	
	@Override
	public void init() {
		stickerRenderer = new StickerRenderer();
	}
	
	@Override
	public void destroy() {
		stickerRenderer = null;
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
		Long productNumber = Long.parseLong(request.getParameter("productNumber"));

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
		ServletOutputStream out = response.getOutputStream();
		
		out.print(stickerRenderer.renderHeader());
		out.print(stickerRenderer.render(productNumber, currencyFormat));
		out.print(stickerRenderer.renderFooter());
	}

}
