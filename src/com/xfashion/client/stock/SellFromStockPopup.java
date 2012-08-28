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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.SellFromStockEvent;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.PromoDTO;
import com.xfashion.shared.SoldArticleDTO;

public class SellFromStockPopup {

	protected List<SoldArticleDTO> sellArticles;
	protected Map<Long, ArticleAmountDTO> articleAmounts;
	protected StockDataProvider stockProvider;

	protected Map<Long, PromoDTO> promos;

	protected DialogBox dialogBox;
	protected Grid articleGrid;
	protected Label priceSumLabel;
	protected TextBox eanTextBox;
	protected Label lastArticlePriceLabel;

	protected SoldArticleFactory soldArticleFactory;
	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected Formatter formatter;

	public SellFromStockPopup(StockDataProvider stockProvider, IProvideArticleFilter filterProvider) {
		this.stockProvider = stockProvider;
		this.sellArticles = new ArrayList<SoldArticleDTO>();
		this.articleAmounts = new HashMap<Long, ArticleAmountDTO>();
		
		this.soldArticleFactory = new SoldArticleFactory(filterProvider);
		this.textMessages = GWT.create(TextMessages.class);
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.formatter = Formatter.getInstance();
	}

	public void show(Map<Long, PromoDTO> promos) {
		this.promos = promos;
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
		vp.add(createArticleGrid());
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

	protected Widget createCancelButton() {
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	protected Grid createArticleGrid() {
		articleGrid = new Grid(1, 3);
		articleGrid.setWidget(0, 1, createPriceSumLabel());
		return articleGrid;
	}

	protected Widget createPriceSumLabel() {
		priceSumLabel = new Label();
		return priceSumLabel;
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
		Integer sellPrice = ArticleTypeManagement.getArticleTypePriceStrategy.getPrice(articleType);
		SoldArticleDTO sellArticle = soldArticleFactory.createSoldArticleDTO(articleType, sellPrice, UserManagement.user.getShop());

		ArticleAmountDTO sellArticleAmount = articleAmounts.get(ean);
		if (sellArticleAmount == null) {
			sellArticleAmount = new ArticleAmountDTO(articleType.getKey(), 0);
			articleAmounts.put(articleType.getProductNumber(), sellArticleAmount);
		}

		ArticleAmountDTO stockEntry = stockProvider.getStock().get(articleType.getKey());
		if (stockEntry == null || stockEntry.getAmount() <= sellArticleAmount.getAmount()) {
			Xfashion.fireError(errorMessages.notEnoughArticlesInStock());
		} else {
			sellArticleAmount.increaseAmount();
			sellArticles.add(sellArticle);
			addArticleToGrid(sellArticle, articleType);
			refreshPriceSum();
		}
	}

	protected void refreshPriceSum() {
		Integer sum = 0;
		for (SoldArticleDTO sellArticle : sellArticles) {
			sum += sellArticle.getSellPrice();
		}
		priceSumLabel.setText(formatter.formatCentsToCurrency(sum));
	}

	protected void addArticleToGrid(SoldArticleDTO sellArticle, ArticleTypeDTO articleType) {
		Label articleNameLabel = new Label(articleType.getName());
		lastArticlePriceLabel = new Label(formatter.formatCentsToCurrency(articleType.getSellPriceAt()));
		Button removeArticleButton = new Button(textMessages.sellRemoveArticle());
		removeArticleButton.addClickHandler(new RemoveArticleFromSellListClickHandler(this, sellArticle));
		articleGrid.resizeRows(sellArticles.size() + 1);
		articleGrid.setWidget(sellArticles.size() - 1, 0, articleNameLabel);
		articleGrid.setWidget(sellArticles.size() - 1, 1, lastArticlePriceLabel);
		articleGrid.setWidget(sellArticles.size() - 1, 2, removeArticleButton);
		articleGrid.setWidget(sellArticles.size(), 1, priceSumLabel);
	}
	
	void removeArticle(SoldArticleDTO sellArticle) {
		int listIndex = sellArticles.indexOf(sellArticle);
		removeArticleFromGrid(listIndex);
		sellArticles.remove(listIndex);
		refreshPriceSum();
	}

	private void removeArticleFromGrid(int listIndex) {
		articleGrid.removeRow(listIndex);
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
			} else if (value.toCharArray()[0] == BarcodeHelper.PROMO_NOTICE_PREFIX_CHAR) {
				if (sellArticles.size() == 0) {
					Xfashion.fireError(errorMessages.noSellArticles());
				} else {
					modifyPriceOfLastArticle(value.substring(0, 12));
				}
				resetEanTextBox();
			} else {
				Xfashion.fireError(errorMessages.noValidArticleTypeEAN());
			}
		}
	}

	private void modifyPriceOfLastArticle(String promoIdString) {
		Long promoId = Long.parseLong(promoIdString);
		PromoDTO promo = promos.get(promoId);
		if (promo == null) {
			Xfashion.fireError(errorMessages.unknownPromo());
			return;
		}

		SoldArticleDTO soldArticle = sellArticles.get(sellArticles.size() - 1);
		if (soldArticle.getPromoKey() != null) {
			Xfashion.fireError(errorMessages.onlyOnePromoAllowed());
			return;
		}
		
		soldArticle.setPromoKey(promo.getKey());
		if (promo.getPrice() != null) {
			soldArticle.setSellPrice(promo.getPrice());
		} else {
			Integer price = soldArticle.getSellPrice();
			Integer newPrice = (price * 100 - price * promo.getPercent()) / 100;
			soldArticle.setSellPrice(newPrice);
		}

		lastArticlePriceLabel.setText(formatter.formatCentsToCurrency(soldArticle.getSellPrice()));
		refreshPriceSum();
	}

	protected void sell() {
		Xfashion.eventBus.fireEvent(new SellFromStockEvent(sellArticles));
		hide();
	}

	protected void reset() {
		sellArticles.clear();
		articleAmounts.clear();
		priceSumLabel.setText("");
		articleGrid.clear();
		articleGrid.resizeRows(1);
		articleGrid.setWidget(0, 1, priceSumLabel);
		resetEanTextBox();
	}

	protected void resetEanTextBox() {
		eanTextBox.setText("");
		eanTextBox.setFocus(true);
	}

}
