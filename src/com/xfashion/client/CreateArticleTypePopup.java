package com.xfashion.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.shared.ArticleTypeDTO;

public class CreateArticleTypePopup {
	
	private DialogBox popup = null;
	
	private TextBox nameTextBox = null;
	private Label categoryLabel = null;
	private Label styleLabel = null;
	private Label brandLabel = null;
	private Label colorLabel = null;
	private Label sizeLabel = null;
	
	private ArticleTypeDTO currentArticleType = null;
	private PanelMediator panelMediator = null;
	
	public CreateArticleTypePopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setCreateArticleTypePopup(this);
	}
	
	public void showForPrefilledArticleType(ArticleTypeDTO articleType) {
		if (popup == null) {
			popup = createPopup();
		}
		currentArticleType = articleType;
		categoryLabel.setText(articleType.getCategory());
		styleLabel.setText(articleType.getStyle());
		brandLabel.setText(articleType.getBrand());
		colorLabel.setText(articleType.getColor());
		sizeLabel.setText(articleType.getSize());
		nameTextBox.setText("");

		popup.show();
		nameTextBox.setFocus(true);
	}
	
	private DialogBox createPopup() {
		// Caption popupCaption = new Caption
		final DialogBox popup = new DialogBox();

		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		
		VerticalPanel panel = new VerticalPanel();

		Label headerLabel = new Label("Artikel anlegen");
		panel.add(headerLabel);
		
		Grid grid = createMainGrid();
		panel.add(grid);
		
		HorizontalPanel navPanel = new HorizontalPanel();
		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(nameTextBox.getText() != null && nameTextBox.getText().length() > 0) {
					currentArticleType.setName(nameTextBox.getText());
					panelMediator.addArticleType(currentArticleType);
				}
				popup.hide();
			}
		});
		navPanel.add(createButton);
		Button cancelButton = new Button("Abbrechen");
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		navPanel.add(cancelButton);
		panel.add(navPanel);
		
		popup.add(panel);
		popup.center();
		
		return popup;
	}
	
	private Grid createMainGrid() {
		Grid grid = new Grid(6, 2);
		categoryLabel = createGridRow(grid, 0, "Kategorie:");
		styleLabel = createGridRow(grid, 1, "Stil:");
		brandLabel = createGridRow(grid, 2, "Marke:");
		sizeLabel = createGridRow(grid, 3, "Größe:");
		colorLabel = createGridRow(grid, 4, "Farbe:");

		Label nameLabel = new Label("Name:");
		nameTextBox = new TextBox();
		grid.setWidget(5, 0, nameLabel);
		grid.setWidget(5, 1, nameTextBox);
		return grid;
	}
	
	private Label createGridRow(Grid grid, int row, String labelName) {
		Label label = new Label(labelName);
		Label content = new Label("");
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}
	
}
