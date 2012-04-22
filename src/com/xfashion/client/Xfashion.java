package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.at.ArticleTypeDatabase;
import com.xfashion.client.at.ArticleTypePanel;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;

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

	private Panel mainPanel;
	
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
		
		VerticalPanel panel = new VerticalPanel();
		addMainPanel(panel);
		addNavPanel(panel);
		RootPanel.get("mainPanelContainer").add(panel);
	}
	
	public void addMainPanel(Panel panel) {
		mainPanel = new HorizontalPanel();

		PanelMediator panelMediator = new PanelMediator();
		panelMediator.setArticleTypeDatabase(articleTypeDatabase);
		articleTypeDatabase.setApplicationLoadListener(panelMediator);
		articleTypeDatabase.setApplicationErrorListener(panelMediator);
		panelMediator.setXfashion(this);
		
		CategoryPanel categoryPanel = new CategoryPanel(panelMediator);
		mainPanel.add(categoryPanel.createPanel(articleTypeDatabase.getCategoryProvider()));
		
		BrandPanel brandPanel = new BrandPanel(panelMediator);
		mainPanel.add(brandPanel.createPanel(articleTypeDatabase.getBrandProvider()));
		
		StylePanel stylePanel = new StylePanel(panelMediator);
		mainPanel.add(stylePanel.createPanel(articleTypeDatabase.getStyleProvider()));
		
		SizePanel sizePanel = new SizePanel(panelMediator);
		mainPanel.add(sizePanel.createPanel(articleTypeDatabase.getSizeProvider()));
		
		ColorPanel colorPanel = new ColorPanel(panelMediator);
		mainPanel.add(colorPanel.createPanel(articleTypeDatabase.getColorProvider()));
		
		ArticleTypePanel articleTypePanel = new ArticleTypePanel(panelMediator);
		mainPanel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
		
//		mainPanel.add(createArticleTypeList(articleTypeDatabase));

		panel.add(mainPanel);
	}
	
	public void addNavPanel(Panel panel) {
		HorizontalPanel navPanel = new HorizontalPanel();
		
		Button createCategoriesButton = new Button("create categories");
		createCategoriesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				articleTypeDatabase.createCategories();
			}
		});
		
		navPanel.add(createCategoriesButton);
		panel.add(navPanel);
	}

}
