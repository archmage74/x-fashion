package com.xfashion.client.stock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.RequestOpenSellPopupEvent;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.ArticleAmountDTO;

public class StockPanel {

	public static final int PANEL_MAX_WIDTH = 550; 
	public static final int PANEL_MIN_WIDTH = 25;
	
	private ProvidesArticleFilter provider;
	
	protected HorizontalPanel headerPanel;
	protected HorizontalPanel panel;
	protected ScrollPanel scrollPanel;
	
	protected boolean minimized = false;
	
	protected TextMessages textMessages;
	protected ImageResources images;
	
	public StockPanel(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.provider = provider;
	}
	
	public Panel createPanel(ArticleTypeDatabase articleTypeDatabase, ArticleAmountDataProvider stockProvider, 
			ArticleAmountDataProvider notepadArticleProvider) {

		if (panel == null) {
			panel = new HorizontalPanel();
			panel.add(createArticlePanel(stockProvider));
			NotepadPanel notepadPanel = new NotepadPanel(articleTypeDatabase);
			panel.add(notepadPanel.createPanel(notepadArticleProvider, false));
		}
		return panel;
	}
	
	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel articlePanel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		articlePanel.add(headerPanel);
		
		ArticleTable<ArticleAmountDTO> att = new StockArticleTable(provider);
		Panel atp = att.create(articleAmountProvider);
		articlePanel.add(atp);

		scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_MAX_WIDTH);
		scrollPanel.add(articlePanel);
		
		return articlePanel;
	}
	
	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

		headerPanel.add(createHeaderLabel());
		
		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.add(createSellIcon());
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);
		
		return headerPanel;
	}
	
	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.stockManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}
	
	private Image createSellIcon() {
		Image icon = Buttons.sell();
		icon.setTitle(textMessages.sellHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RequestOpenSellPopupEvent());
			}
		});
		return icon;
	}

	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
}
