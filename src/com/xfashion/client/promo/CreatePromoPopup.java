package com.xfashion.client.promo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.xfashion.client.promo.event.CreatePromoEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.PromoDTO;

public class CreatePromoPopup {

	protected DialogBox dialogBox;
	protected Grid inputGrid;
	protected TextBox priceTextBox;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected Formatter formatter;

	public CreatePromoPopup() {
		this.textMessages = GWT.create(TextMessages.class);
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.formatter = new Formatter();
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
		vp.add(createInputGrid());

		HorizontalPanel nav = new HorizontalPanel();
		nav.add(createCreatePromoButton());
		nav.add(createCancelButton());
		vp.add(nav);

		dialogBox.add(vp);

		return dialogBox;
	}

	private Widget createCreatePromoButton() {
		Button okButton = new Button(textMessages.createPromo());
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createPromo();
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

	protected Grid createInputGrid() {
		Grid promoGrid = new Grid(1, 2);

		Label label = new Label(textMessages.sellPrice());
		promoGrid.setWidget(0, 0, label);

		priceTextBox = new TextBox();
		promoGrid.setWidget(0, 1, priceTextBox);

		return promoGrid;
	}

	protected void createPromo() {
		String priceString = priceTextBox.getValue();
		Integer price = null;
		try {
			price = formatter.parseEurToCents(priceString);
		} catch (NumberFormatException e) {
			Xfashion.fireError(errorMessages.invalidPrice());
			return;
		}
		PromoDTO promo = new PromoDTO(price, null);
		Xfashion.eventBus.fireEvent(new CreatePromoEvent(promo));
		hide();
	}

	protected void reset() {
		priceTextBox.setValue("");
	}

}
