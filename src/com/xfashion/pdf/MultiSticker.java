package com.xfashion.pdf;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.notepad.NotepadService;
import com.xfashion.shared.notepad.ArticleAmountDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class MultiSticker extends HttpServlet {

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
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		NotepadDTO notepad = (NotepadDTO) req.getSession().getAttribute(NotepadService.SESSION_NOTEPAD);
		
		res.setContentType("text/html; charset=iso-8859-15");
		res.setCharacterEncoding("iso-8859-15");
		
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMAN);
		ServletOutputStream out = res.getOutputStream();
		
		out.print(stickerRenderer.renderHeader());
		boolean firstpage = true;
		for (ArticleAmountDTO aa : notepad.getArticles()) {
			String articleTypeKey = aa.getArticleTypeKey();
			for (int i=0; i<aa.getAmount(); i++) {
				out.print(stickerRenderer.render(articleTypeKey, currencyFormat, !firstpage));
				firstpage = false;
			}
		}
		out.print(stickerRenderer.renderFooter());
	}
	
}
