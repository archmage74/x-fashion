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
	private TextBox priceTextBox = null;
	private TextBox productNumberTextBox = null;
	private Label categoryLabel = null;
	private Label styleLabel = null;
	private Label brandLabel = null;
	private Label colorLabel = null;
	private Label sizeLabel = null;
	private Label errorLabel = null;
	
	private ArticleTypeDTO currentArticleType = null;
	private PanelMediator panelMediator = null;
	
	private Formatter formatter;
	
	public CreateArticleTypePopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setCreateArticleTypePopup(this);
		formatter = Formatter.getInstance();
	}
	
	public void showForPrefilledArticleType(ArticleTypeDTO articleType) {
//		if (popup == null) {
			popup = createPopup();
//		}
		currentArticleType = articleType;
		categoryLabel.setText(articleType.getCategory());
		styleLabel.setText(articleType.getStyle());
		brandLabel.setText(articleType.getBrand());
		colorLabel.setText(articleType.getColor());
		sizeLabel.setText(articleType.getSize());
		sizeLabel.setText(articleType.getSize());
		nameTextBox.setText("");
		priceTextBox.setText("€");
		productNumberTextBox.setText("");
		errorLabel.setText(" ");
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
		
		errorLabel = new Label("");
		errorLabel.addStyleName("errorText");
		panel.add(errorLabel);
		
		HorizontalPanel navPanel = new HorizontalPanel();
		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					updateArticleType(currentArticleType);
					panelMediator.addArticleType(currentArticleType);
					popup.hide();
				} catch (CreateArticleException e) {
					errorLabel.setText(e.getMessage());
				}
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
	
	private void updateArticleType(ArticleTypeDTO at) throws CreateArticleException {
		try {
			Integer price = formatter.parseEurToCents(priceTextBox.getText());
			currentArticleType.setPrice(price);
		} catch (Exception e) {
			throw new CreateArticleException("Ungültiger Preis");
		}
		try {
			long productNumber = Long.parseLong(productNumberTextBox.getText());
			currentArticleType.setProductNumber(productNumber);
		} catch (Exception e) {
			throw new CreateArticleException("Ungültige EAN");
		}
		if (nameTextBox.getText() == null || "".equals(nameTextBox.getText())) {
			throw  new CreateArticleException("Ungültiger Name");
		} else {
			currentArticleType.setName(nameTextBox.getText());
		}
	}
	
	private Grid createMainGrid() {
		Grid grid = new Grid(8, 2);
		categoryLabel = createLabelGridRow(grid, 0, "Kategorie:");
		styleLabel = createLabelGridRow(grid, 1, "Stil:");
		brandLabel = createLabelGridRow(grid, 2, "Marke:");
		sizeLabel = createLabelGridRow(grid, 3, "Größe:");
		colorLabel = createLabelGridRow(grid, 4, "Farbe:");
		nameTextBox = createTextBoxGridRow(grid, 5, "Name:");
		priceTextBox = createTextBoxGridRow(grid, 6, "Preis:");
		productNumberTextBox = createTextBoxGridRow(grid, 7, "EAN:");
		return grid;
	}
	
	private Label createLabelGridRow(Grid grid, int row, String labelName) {
		Label label = new Label(labelName);
		Label content = new Label("");
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}

	private TextBox createTextBoxGridRow(Grid grid, int row, String labelName) {
		Label label = new Label(labelName + ":");
		TextBox textBox = new TextBox();
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, textBox);
		return textBox;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}
	
}
