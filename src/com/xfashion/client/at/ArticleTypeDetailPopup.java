package com.xfashion.client.at;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.dialog.YesNoCallback;
import com.xfashion.client.dialog.YesNoPopup;
import com.xfashion.client.stock.StockDataProvider;
import com.xfashion.client.stock.event.RemoveFromStockEvent;
import com.xfashion.shared.ArticleAmountDTO;

public class ArticleTypeDetailPopup extends ArticleTypePopup {

	protected Grid buyPriceGrid;
	protected Label buyPrice;
	protected Grid sellPriceGrid;
	protected Label sellPrice;
	protected Label stock;
	
	protected StockDataProvider stockProvider;

	public ArticleTypeDetailPopup(ProvidesArticleFilter provider, StockDataProvider stockProvider) {
		super(provider);
		this.stockProvider = stockProvider;
	}

	@Override
	protected void updateDetails() {
		buyPrice.setText(formatter.formatCentsToValue(this.articleType.getBuyPrice()));
		Integer p = ArticleTypeManagement.getArticleTypePriceStrategy.getPrice(this.articleType);
		if (p != null) {
			sellPrice.setText(formatter.formatCentsToValue(p));
		} else {
			sellPrice.setText(textMessages.unknownPrice());
		}
		ArticleAmountDTO articleAmount = stockProvider.getStock().get(articleType.getKey());
		int amount = 0;
		if (articleAmount != null) {
			 amount = articleAmount.getAmount();
		}
		stock.setText("" + amount);
	}

	@Override
	protected Grid createDetailsGrid() {
		Grid grid = new Grid(3, 2);

		buyPriceGrid = new Grid(1, 2);
		Label buyPriceLabel = new Label(textMessages.buyPrice() + ":");
		grid.setWidget(0, 0, buyPriceLabel);
		buyPrice = createGridLabelRow(buyPriceGrid, 0, textMessages.currencySign());
		grid.setWidget(0, 1, buyPriceGrid);

		sellPriceGrid = new Grid(1, 2);
		Label sellPriceLabel = new Label(textMessages.sellPrice() + ":");
		grid.setWidget(1, 0, sellPriceLabel);
		sellPrice = createGridLabelRow(sellPriceGrid, 0, textMessages.currencySign());
		grid.setWidget(1, 1, sellPriceGrid);

		Grid stockGrid = new Grid(1, 2);
		Label stockLabel = new Label(textMessages.stock() + ":");
		stock = new Label();
		Button removeFromStockButton = new Button(textMessages.removeFromStock());
		removeFromStockButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmRemoveFromStock();
			}
		});
		stockGrid.setWidget(0, 0, stock);
		stockGrid.setWidget(0, 1, removeFromStockButton);
		grid.setWidget(2, 0, stockLabel);
		grid.setWidget(2, 1, stockGrid);
		
		return grid;
	}

	protected Button createCancelButton() {
		Button cancelButton = new Button(textMessages.close());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	@Override
	protected Panel createNavPanel() {
		HorizontalPanel hp = new HorizontalPanel();

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button cancelButton = createCancelButton();
		hp.add(cancelButton);

		return hp;
	}
	
	protected void confirmRemoveFromStock() {
		YesNoPopup confirmPopup = new YesNoPopup(textMessages.confirmRemoveFromStock(), new YesNoCallback() {
			@Override
			public void yes() {
				Xfashion.eventBus.fireEvent(new RemoveFromStockEvent(articleType));
				hide();
			}
			@Override
			public void no() {
			}
		});
		confirmPopup.show();
	}

	private Label createGridLabelRow(Grid grid, int row, String name) {
		Label label = new Label(name);
		Label content = new Label();
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}

}
