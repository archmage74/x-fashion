package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Formatter;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.img.ImageManagementPopup;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public class CreateArticleTypePopup {
	
	private DialogBox popup = null;
	
	private TextBox nameTextBox = null;
	private TextBox buyPriceTextBox = null;
	private TextBox sellPriceTextBox = null;
	private Label categoryLabel = null;
	private Label styleLabel = null;
	private Label brandLabel = null;
	private Label colorLabel = null;
	private Label sizeLabel = null;
	private Label imageLabel = null;
	private Label errorLabel = null;
	
	private ArticleTypeDTO currentArticleType = null;
	private List<SizeDTO> currentSizes = null;
	
	private PanelMediator panelMediator = null;
	private ProvidesArticleFilter provider; 
	
	private Formatter formatter;
	
	private ImageManagementPopup imagePopup;
	
	private ErrorMessages errorMessages;
	private TextMessages textMessages;
	
	public CreateArticleTypePopup(PanelMediator panelMediator) {
		errorMessages = GWT.create(ErrorMessages.class);
		textMessages = GWT.create(TextMessages.class);
		this.panelMediator = panelMediator;
		provider = panelMediator.getArticleTypeDatabase();
		panelMediator.setCreateArticleTypePopup(this);
		formatter = Formatter.getInstance();
	}
	
	public void showForPrefilledArticleType(ArticleTypeDTO articleType, List<SizeDTO> sizes) {
		if (popup == null) {
			popup = createPopup();
		}
		currentArticleType = articleType;
		currentSizes = sizes;
		
		categoryLabel.setText(provider.getCategoryProvider().resolveData(articleType.getCategoryId()).getName());
		styleLabel.setText(provider.getStyleProvider().resolveData(articleType.getStyleId()).getName());
		brandLabel.setText(provider.getBrandProvider().resolveData(articleType.getBrandId()).getName());
		colorLabel.setText(provider.getColorProvider().resolveData(articleType.getColorId()).getName());
		if (sizes.size() > 1) {
			sizeLabel.setText(textMessages.articleCreateMultipleSizes());
			StringBuffer sb = new StringBuffer();
			for (SizeDTO size : currentSizes) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(size.getName());
			}
			sizeLabel.setTitle(sb.toString());
		} else {
			sizeLabel.setText(sizes.get(0).getName());
			sizeLabel.setTitle("");
		}

		nameTextBox.setText("");
		sellPriceTextBox.setText("");
		buyPriceTextBox.setText("");
		errorLabel.setText(" ");
		popup.show();
		nameTextBox.setFocus(true);
	}
	
	public void hide() {
		if (imagePopup != null) {
			imagePopup.hide();
		}
		if (popup.isShowing()) {
			popup.hide();
		}
	}
	
	private DialogBox createPopup() {
		// Caption popupCaption = new Caption
		final DialogBox popup = new DialogBox();

		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		
		VerticalPanel panel = new VerticalPanel();

		Label headerLabel = new Label(textMessages.articleCreateHeader());
		headerLabel.setStyleName("dialogHeader");
		panel.add(headerLabel);
		
		Grid grid = createMainGrid();
		panel.add(grid);
		
		Button imageButton = new Button(textMessages.selectImage());
		imageButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (imagePopup == null) {
					imagePopup = new ImageManagementPopup();
					imagePopup.addSelectionHandler(new SelectionHandler<ArticleTypeImageDTO>() {
						@Override
						public void onSelection(SelectionEvent<ArticleTypeImageDTO> event) {
							imageLabel.setText(event.getSelectedItem().getName());
							currentArticleType.setImageId(event.getSelectedItem().getId());
						}
					});
				}
				imagePopup.show();
			}
		});
		panel.add(imageButton);
		
		errorLabel = new Label("");
		errorLabel.addStyleName("errorText");
		panel.add(errorLabel);
		

		HorizontalPanel navPanel = new HorizontalPanel();
		
		Button createButton = new Button(textMessages.articleCreate());
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try {
					createArticles();
					hide();
				} catch (CreateArticleException e) {
					errorLabel.setText(e.getMessage());
				}
			}
		});
		navPanel.add(createButton);
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		navPanel.add(cancelButton);
		panel.add(navPanel);
		
		popup.add(panel);
		popup.center();
		
		return popup;
	}
	
	private void createArticles() throws CreateArticleException {
		updateArticleType(currentArticleType);
		for (SizeDTO size : currentSizes) {
			currentArticleType.setSizeId(size.getId());
			panelMediator.createArticleType(currentArticleType);
		}
	}
	
	private void updateArticleType(ArticleTypeDTO at) throws CreateArticleException {
		try {
			Integer price = formatter.parseEurToCents(sellPriceTextBox.getText());
			currentArticleType.setSellPrice(price);
		} catch (Exception e) {
			throw new CreateArticleException(errorMessages.invalidPrice());
		}
		try {
			Integer price = formatter.parseEurToCents(buyPriceTextBox.getText());
			currentArticleType.setBuyPrice(price);
		} catch (Exception e) {
			throw new CreateArticleException(errorMessages.invalidPrice());
		}
		if (nameTextBox.getText() == null || "".equals(nameTextBox.getText())) {
			throw  new CreateArticleException(errorMessages.invalidName());
		} else {
			currentArticleType.setName(nameTextBox.getText());
		}
	}
	
	private Grid createMainGrid() {
		Grid grid = new Grid(9, 2);
		categoryLabel = createLabelGridRow(grid, 0, textMessages.category() + ":");
		brandLabel = createLabelGridRow(grid, 1, textMessages.brand() + ":");
		styleLabel = createLabelGridRow(grid, 2, textMessages.style() + ":");
		sizeLabel = createLabelGridRow(grid, 3, textMessages.size() + ":");
		colorLabel = createLabelGridRow(grid, 4, textMessages.color() + ":");
		nameTextBox = createTextBoxGridRow(grid, 5, textMessages.name() + ":");
		buyPriceTextBox = createPriceTextBoxGridRow(grid, 6, textMessages.buyPrice() + ":");
		sellPriceTextBox = createPriceTextBoxGridRow(grid, 7, textMessages.sellPrice() + ":");
		imageLabel = createLabelGridRow(grid, 8, textMessages.image() + ":");
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
		Label label = new Label(labelName);
		TextBox textBox = new TextBox();
		textBox.setStyleName("baseInput");
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, textBox);
		return textBox;
	}

	private TextBox createPriceTextBoxGridRow(Grid grid, int row, String labelName) {
		Label label = new Label(labelName);
		
		HorizontalPanel hp = new HorizontalPanel();

		Label euroLabel = new Label("€");
		euroLabel.setStyleName("pricePrefix");
		
		TextBox textBox = new TextBox();
		textBox.setStyleName("baseInput");
		textBox.addStyleName("priceInput");
		
		hp.add(euroLabel);
		hp.add(textBox);
		
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, hp);
		return textBox;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}
	
}