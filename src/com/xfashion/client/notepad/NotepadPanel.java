package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.IsMinimizable;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.PanelWidthAnimation;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;

public class NotepadPanel implements IsMinimizable {

	public static final int PANEL_MAX_WIDTH = 600; 
	public static final int PANEL_MIN_WIDTH = 25;
	
	private PanelMediator panelMediator;
	
	private ProvidesArticleFilter provider;
	
	protected Panel scrollPanel;
	
	private HorizontalPanel headerPanel;
	
	protected boolean minimized = true;
	protected Image minmaxButton;

	protected TextMessages textMessages;
	protected ImageResources images;
	
	public NotepadPanel(PanelMediator panelMediator) {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.panelMediator = panelMediator;
		this.provider = panelMediator.getArticleTypeDatabase();
	}
	
	public Panel createPanel(ArticleTypeDataProvider articleTypeProvider) {
		Panel articlePanel = createArticlePanel(articleTypeProvider);
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_MIN_WIDTH);
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}
	
	protected Panel createArticlePanel(ArticleTypeDataProvider articleTypeProvider) {

		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		panel.add(headerPanel);
		
		
		ArticleTable att = new CountingArticleTable(provider);
		Panel atp = att.create(articleTypeProvider, panelMediator);
		panel.add(atp);
		
		return panel;
	}
	
	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");

		minmaxButton = new Image();
		if (isMinimized()) {
			minmaxButton.setResource(images.iconMaximize());
		} else {
			minmaxButton.setResource(images.iconMinimize());
		}
		minmaxButton.setStyleName("buttonMinMax");
		ClickHandler minmaxClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minmax();
			}
		};
		minmaxButton.addClickHandler(minmaxClickHandler);
		headerPanel.add(minmaxButton);

		Label headerLabel = new Label(textMessages.notepadManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		headerPanel.add(headerLabel);
		
		return headerPanel;
	}

	public void minmax() {
		if (isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MIN_WIDTH, PANEL_MAX_WIDTH);
			pwa.run(300);
		} else {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MAX_WIDTH, PANEL_MIN_WIDTH);
			pwa.run(300);
		}
	}
	
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
	@Override
	public boolean isMinimized() {
		return minimized;
	}
	
	@Override
	public void setMinimized(boolean minimized) {
		if (minimized) {
			if (!this.minimized) {
				minmaxButton.setResource(images.iconMaximize());
			}
		} else {
			if (this.minimized) {
				minmaxButton.setResource(images.iconMinimize());
				Xfashion.eventBus.fireEvent(new NotepadMaximizedEvent());
			}
		}
		this.minimized = minimized;
	}
	
}
