package com.xfashion.client.stock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class StockPanel {

	public static final int PANEL_MAX_WIDTH = 550; 
	public static final int PANEL_MIN_WIDTH = 25;
	
	private ProvidesArticleFilter provider;
	
	protected Panel scrollPanel;
	
	private HorizontalPanel headerPanel;
	
	protected boolean minimized = false;

	protected TextMessages textMessages;
	protected ImageResources images;
	
	public StockPanel(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.provider = provider;
	}
	
	public Panel createPanel(ArticleAmountDataProvider articleTypeProvider) {
		Panel articlePanel = createArticlePanel(articleTypeProvider);
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_MAX_WIDTH);
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}
	
	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		panel.add(headerPanel);
		
		
		ArticleTable<ArticleAmountDTO> att = new StockArticleTable(provider);
		Panel atp = att.create(articleAmountProvider);
		panel.add(atp);
		
		return panel;
	}
	
	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

//		headerPanel.add(createMinmaxButton());
		headerPanel.add(createHeaderLabel());
		
		HorizontalPanel toolPanel = new HorizontalPanel();
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
	
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
}
