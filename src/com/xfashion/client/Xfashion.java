package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

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
	private String mainPanelStyle;
	
	HorizontalPanel sizeHeaderPanel;
	HorizontalPanel colorHeaderPanel;
	HorizontalPanel articleHeaderPanel;
	
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
		panelMediator.setXfashion(this);
		
		CategoryPanel categoryPanel = new CategoryPanel(panelMediator);
		mainPanel.add(categoryPanel.createPanel(articleTypeDatabase.getCategoryProvider()));
		
		StylePanel stylePanel = new StylePanel(panelMediator);
		mainPanel.add(stylePanel.createPanel(articleTypeDatabase.getStyleProvider()));
		
		BrandPanel brandPanel = new BrandPanel(panelMediator);
		mainPanel.add(brandPanel.createPanel(articleTypeDatabase.getBrandProvider()));
		
		mainPanel.add(createSizePanel(articleTypeDatabase));
		mainPanel.add(createColorPanel(articleTypeDatabase));

		ArticleTypePanel articleTypePanel = new ArticleTypePanel(panelMediator);
		mainPanel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider()));
		
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

	public void setHeaderStyle(String style) {
		if (mainPanelStyle != null) {
			sizeHeaderPanel.removeStyleName(mainPanelStyle);
			colorHeaderPanel.removeStyleName(mainPanelStyle);
		}
		mainPanelStyle = style;
		if (mainPanelStyle != null) {
			sizeHeaderPanel.addStyleName(style);
			colorHeaderPanel.addStyleName(style);
		}
	}

	private Panel createColorPanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		colorHeaderPanel = new HorizontalPanel();
		colorHeaderPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		colorHeaderPanel.add(clearButton);
		Label label = new Label("Farbe");
		label.addStyleName("filterLabel");
		colorHeaderPanel.add(label);
		panel.add(colorHeaderPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
//		styleList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
//		styleList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setColorFilter(selectionModel.getSelectedSet());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectionModel.clear();
			}
		});

		articleTypeDatabase.addColorDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	private Panel createSizePanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel cellListPanel = new VerticalPanel();

		sizeHeaderPanel = new HorizontalPanel();
		sizeHeaderPanel.addStyleName("filterHeader");
		Label label = new Label("Größe");
		label.addStyleName("filterLabel");
		sizeHeaderPanel.add(label);
		cellListPanel.add(sizeHeaderPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setSizeFilter(selectionModel.getSelectedSet());
			}
		});
		cellListPanel.add(styleList);
		articleTypeDatabase.addSizeDisplay(styleList);
		styleList.addStyleName("sizeList");

		return cellListPanel;
	}

}
