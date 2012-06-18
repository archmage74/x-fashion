package com.xfashion.client.sell;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.SoldArticleDTO;

public class SellStatisticPanel {

	public static final int PANEL_WIDTH = 550; 

	protected Panel scrollPanel;
	
	ListBox soldArticlesListBox; 
	private HorizontalPanel headerPanel;
	
	protected TextMessages textMessages;
	protected ImageResources images;
	protected Formatter formatter;
	
	public SellStatisticPanel() {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		formatter = new Formatter();
	}
	
	public Panel createPanel() {
		Panel articlePanel = createArticlePanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_WIDTH);
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}
	
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	public void addSoldArticles(List<SoldArticleDTO> result) {
		for (SoldArticleDTO soldArticle : result) {
			soldArticlesListBox.addItem(createSoldArticleEntry(soldArticle));
		}
	}
	
	protected Panel createArticlePanel() {

		VerticalPanel panel = new VerticalPanel();

		panel.add(createHeaderPanel());
		panel.add(createSoldArticleList());
		
		return panel;
	}
	
	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_WIDTH + "px");

		headerPanel.add(createHeaderLabel());
		
		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);
		
		return headerPanel;
	}
	
	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.sellStatisticHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}
	
	private Widget createSoldArticleList() {
		soldArticlesListBox = new ListBox();
		soldArticlesListBox.setVisibleItemCount(30);
		return soldArticlesListBox;
	}

	private String createSoldArticleEntry(SoldArticleDTO sa) {
		String sellPrice = formatter.formatCentsToCurrency(sa.getSellPrice()); 
		return textMessages.sellStatisticListBoxLine(sa.getAmount(), sa.getArticleName(), sa.getSellDate(), sellPrice);
	}

}
