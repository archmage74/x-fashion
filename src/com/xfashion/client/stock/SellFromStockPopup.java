package com.xfashion.client.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.SellFromStockEvent;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.SoldArticleDTO;

public class SellFromStockPopup {

	protected List<ArticleTypeDTO> articles;
	protected Map<Long, SoldArticleDTO> articleAmounts;
	protected ArticleAmountDataProvider stockProvider;
	protected Map<String, ArticleAmountDTO> stock;

	protected DialogBox dialogBox;
	protected VerticalPanel articlePanel;
	protected TextBox eanTextBox;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected Formatter formatter;

	public SellFromStockPopup(ArticleAmountDataProvider stockProvider, Map<String, ArticleAmountDTO> stock) {
		this.stockProvider = stockProvider;
		this.stock = stock;
		this.articles = new ArrayList<ArticleTypeDTO>();
		this.articleAmounts = new HashMap<Long, SoldArticleDTO>();
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
		formatter = new Formatter();
	}

	public void show() {
		if (dialogBox == null) {
			dialogBox = create();
		}
		dialogBox.setPopupPosition(500, 50);
		dialogBox.show();
		reset();
	}

	public void hide() {
		if (dialogBox != null && dialogBox.isShowing()) {
			dialogBox.hide();
		}
	}

	public DialogBox create() {
		dialogBox = new DialogBox();

		VerticalPanel vp = new VerticalPanel();
		articlePanel = new VerticalPanel();
		vp.add(articlePanel);
		vp.add(createEanTextBox());

		HorizontalPanel nav = new HorizontalPanel();
		nav.add(createSellButton());
		nav.add(createCancelButton());
		vp.add(nav);

		dialogBox.add(vp);

		return dialogBox;
	}

	private Widget createSellButton() {
		Button okButton = new Button(textMessages.sellArticles());
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sell();
			}
		});
		return okButton;
	}

	private Widget createCancelButton() {
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	protected Widget createEanTextBox() {
		eanTextBox = new TextBox();
		eanTextBox.setWidth("120px");
		eanTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkForEAN();
			}
		});
		return eanTextBox;
	}

	protected void addArticle(String eanString) {
		Long ean = Long.parseLong(eanString);

		ArticleTypeDTO articleType = stockProvider.retrieveArticleType(ean);

		SoldArticleDTO sellArticle = articleAmounts.get(ean);
		if (sellArticle == null) {
			sellArticle = new SoldArticleDTO(articleType, UserManagement.user.getShop(), 0);
		}

		ArticleAmountDTO stockEntry = stock.get(articleType.getKey());
		if (stockEntry == null || stockEntry.getAmount() <= sellArticle.getAmount()) {
			Xfashion.fireError(errorMessages.notEnoughArticlesInStock());
		} else {
			sellArticle.increaseAmount();
			articleAmounts.put(articleType.getProductNumber(), sellArticle);
			articlePanel.add(createArticleDisplay(articleType));
		}
	}

	private Widget createArticleDisplay(ArticleTypeDTO articleType) {
		StringBuffer sb = new StringBuffer();
		sb.append(articleType.getName());
		sb.append(" - ");
		sb.append(formatter.formatCentsToCurrency(articleType.getSellPrice()));
		Label l = new Label(sb.toString());
		return l;
	}

	private void checkForEAN() {
		String value = eanTextBox.getText();
		if (value != null && value.length() == 13) {
			for (Character c : value.toCharArray()) {
				if (c < '0' || c > '9') {
					Xfashion.fireError(errorMessages.noValidArticleTypeEAN());
				}
			}
			if (value.toCharArray()[0] == BarcodeHelper.ARTICLE_PREFIX_CHAR) {
				addArticle(value.substring(0, 12));
				eanTextBox.setText("");
			} else {
				Xfashion.fireError(errorMessages.noValidArticleTypeEAN());
			}
		}
	}

	protected void sell() {
		Xfashion.eventBus.fireEvent(new SellFromStockEvent(articleAmounts.values()));
		hide();
	}

	protected void reset() {
		articlePanel.clear();
		articles.clear();
		articleAmounts.clear();
		resetEanTextBox();
	}

	protected void resetEanTextBox() {
		eanTextBox.setText("");
		eanTextBox.setFocus(true);
	}

}
