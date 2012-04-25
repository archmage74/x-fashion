package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.at.ArticleTypeDatabase;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xfashion implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	// private static final String SERVER_ERROR = "An error occurred while " +
	// "attempting to contact the server. Please check your network "
	// + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final GreetingServiceAsync greetingService =
	// GWT.create(GreetingService.class);

	ArticleTypeDatabase articleTypeDatabase;

	static class StyleCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}
			sb.appendHtmlConstant("<div style=\"height:20px; margin-left:3px; margin-right:3px; font-size:12px;\">");
			sb.appendHtmlConstant(category);
			sb.appendHtmlConstant("</div>");
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		articleTypeDatabase = new ArticleTypeDatabase();
		PanelMediator panelMediator = new PanelMediator();
		panelMediator.setXfashion(this);
		panelMediator.setArticleTypeDatabase(articleTypeDatabase);
		articleTypeDatabase.setApplicationLoadListener(panelMediator);
	}		
	
}
