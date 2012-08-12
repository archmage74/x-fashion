package com.xfashion.client.pricechange;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.client.pricechange.event.AcceptPriceChangesEvent;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleAmountDTO;

public class PriceChangePanel implements ContentPanelResizeHandler {

	public static final int PANEL_MAX_WIDTH = 550;

	protected IProvideArticleFilter filterProvider;

	protected HorizontalPanel headerPanel;
	protected HorizontalPanel panel;
	protected Panel scrollPanel;

	protected TextMessages textMessages;

	public PriceChangePanel(IProvideArticleFilter filterProvider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.filterProvider = filterProvider;
		
		registerForEvents();
	}

	public Panel createPanel(PriceChangeArticleAmountDataProvider changedArticleTypesProvider) {

		if (panel == null) {
			panel = new HorizontalPanel();
			panel.add(createArticlePanel(changedArticleTypesProvider));
		}
		return panel;
	}

	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	protected Panel createArticlePanel(PriceChangeArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel articlePanel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		articlePanel.add(headerPanel);

		GetPriceFromArticleAmountStrategy<ArticleAmountDTO> priceStrategy = new GetPriceFromArticleAmountStrategy<ArticleAmountDTO>(
				articleAmountProvider, ArticleTypeManagement.getArticleTypePriceStrategy);
		ArticleTable<ArticleAmountDTO> att = new PriceChangeArticleTable(filterProvider, priceStrategy, articleAmountProvider);
		scrollPanel = att.create(articleAmountProvider);
		articlePanel.add(scrollPanel);

		setWidth(PANEL_MAX_WIDTH);
		setHeight(MainPanel.contentPanelHeight);

		return articlePanel;
	}
	
	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		setHeight(event.getHeight());
	}
	
	public void setHeight(int height) {
		if (scrollPanel != null) {
			int panelHeight = height - 40;
			scrollPanel.setHeight(panelHeight + "px");
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

		headerPanel.add(createHeaderLabel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.add(createAcceptAllPriceChangesButton());
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	protected Button createAcceptAllPriceChangesButton() {
		Button acceptAllPriceChangesButton = new Button(textMessages.acceptAllPriceChanges());
		acceptAllPriceChangesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				acceptAllPriceChanges();
			}
		});
		return acceptAllPriceChangesButton;
	}

	protected void acceptAllPriceChanges() {
		Xfashion.eventBus.fireEvent(new AcceptPriceChangesEvent());
	}

	protected Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.priceChangeManagementHeader());
		headerLabel.addStyleName("filterLabel");
		return headerLabel;
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ContentPanelResizeEvent.TYPE, this);
	}

}
