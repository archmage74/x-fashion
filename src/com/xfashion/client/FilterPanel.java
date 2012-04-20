package com.xfashion.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public abstract class FilterPanel {

	protected Panel headerPanel; 
	
	protected PanelMediator panelMediator;
	
	public FilterPanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
	}
	
	public void setHeaderColor(String color) {
		if (color != null) {
			headerPanel.getElement().getStyle().setBackgroundColor(color);
		} else {
			headerPanel.getElement().getStyle().setBackgroundColor("#777");
		}
	}
	
	protected Panel createHeaderPanel(String title) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label label = new Label(title);
		label.addStyleName("filterLabel");
		headerPanel.add(label);
		Button createButton = new Button("+");
		headerPanel.add(createButton);

		ClickHandler addClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showCreatePopup();
			}
		};

		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearSelection();
			}
		});

		createButton.addClickHandler(addClickHandler);

		setHeaderColor(null);
		return headerPanel;
	}
	
	public abstract void clearSelection();
	
	public abstract void showCreatePopup();
	
	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

}
