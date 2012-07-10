package com.xfashion.client.promo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.promo.event.ActivatePromoEvent;
import com.xfashion.client.promo.event.DeactivatePromoEvent;
import com.xfashion.client.promo.event.OpenCreatePromoPopupEvent;
import com.xfashion.client.promo.event.PrintPromosEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.PromoDTO;

public class PromoPanel {

	protected VerticalPanel panel;

	protected HorizontalPanel promoPanel;
	protected ListBox promoListBox;
	protected Button activateButton;
	protected Button deactivateButton;
	protected Button printButton;
	protected TextBox printAmountTextBox;

	protected List<PromoDTO> promos;
	protected List<PromoDTO> shownPromos;
	protected boolean showAll = false;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected Formatter formatter;
	protected BarcodeHelper barcodeHelper;

	public PromoPanel() {
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
		formatter = new Formatter();
		barcodeHelper = new BarcodeHelper();
		
		promos = new ArrayList<PromoDTO>();
		shownPromos = new ArrayList<PromoDTO>();
	}

	public Panel createPanel() {
		if (panel == null) {
			panel = new VerticalPanel();
			panel.add(createHeaderPanel());
			panel.add(createFilterListBox());
			panel.add(createPromoPanel());
			refreshActionPanel();
		}

		return panel;
	}

	public void setPromos(List<PromoDTO> newPromos) {
		promos.clear();
		promos.addAll(newPromos);
		refreshPromoListBox();
		refreshActionPanel();
	}

	protected void refreshActionPanel() {
		int promoIndex = promoListBox.getSelectedIndex();
		if (promoIndex < 0) {
			activateButton.setEnabled(false);
			deactivateButton.setEnabled(false);
			printButton.setEnabled(false);
		} else {
			PromoDTO selectedPromo = shownPromos.get(promoIndex);
			if (selectedPromo.isActivated()) {
				activateButton.setEnabled(false);
				deactivateButton.setEnabled(true);
				printButton.setEnabled(true);
			} else {
				activateButton.setEnabled(true);
				deactivateButton.setEnabled(false);
				printButton.setEnabled(false);
			}
		}
	}

	protected void printPromos() {
		int index = promoListBox.getSelectedIndex();
		if (index >= 0) {
			PromoDTO promo = shownPromos.get(index);
			String amountString = printAmountTextBox.getValue();
			Integer amount = Integer.parseInt(amountString);
			Xfashion.eventBus.fireEvent(new PrintPromosEvent(promo, amount));
		}
	}
	
	private void refreshPromoListBox() {
		promoListBox.clear();
		shownPromos.clear();
		for (PromoDTO promo : promos) {
			if (showAll || promo.isActivated()) {
				String price = formatter.formatCentsToCurrency(promo.getPrice());
				String ean = barcodeHelper.generatePromoEan(promo.getEan());
				if (promo.isActivated()) {
					promoListBox.addItem(textMessages.promoListBoxLine(price, ean));
				} else {
					promoListBox.addItem(textMessages.promoDeactivatedListBoxLine(price, ean));
				}
				shownPromos.add(promo);
			}
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth("500px");
		headerPanel.add(createHeaderLabel());
		return headerPanel;
	}

	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.promoHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}

	protected void activatePromo() {
		int index = promoListBox.getSelectedIndex();
		if (index >= 0) {
			PromoDTO promo = shownPromos.get(index);
			Xfashion.eventBus.fireEvent(new ActivatePromoEvent(promo));
		}
	}

	protected void deactivatePromo() {
		int index = promoListBox.getSelectedIndex();
		if (index >= 0) {
			PromoDTO promo = shownPromos.get(index);
			Xfashion.eventBus.fireEvent(new DeactivatePromoEvent(promo));
		}
	}
	
	private Widget createFilterListBox() {
		final ListBox filterListBox = new ListBox();
		filterListBox.setWidth("250px");
		filterListBox.addItem(textMessages.showActivatedPromos());
		filterListBox.addItem(textMessages.showAllPromos());
		filterListBox.setWidth("250px");
		filterListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (filterListBox.getSelectedIndex() == 0) {
					showAll = false;
					refreshPromoListBox();
				} else {
					showAll = true;
					refreshPromoListBox();
				}
			}
		});
		return filterListBox;
	}

	private Widget createPromoPanel() {
		promoPanel = new HorizontalPanel();
		promoPanel.add(createPromoListBox());
		promoPanel.add(createActionPanel());
		return promoPanel;
	}

	private Widget createPromoListBox() {
		promoListBox = new ListBox();
		promoListBox.setVisibleItemCount(20);
		promoListBox.setWidth("250px");
		promoListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				refreshActionPanel();
			}
		});
		return promoListBox;
	}

	private Widget createActionPanel() {
		VerticalPanel actionPanel = new VerticalPanel();
		actionPanel.add(createCreateButton());
		actionPanel.add(createActivateButton());
		actionPanel.add(createDeactivateButton());
		actionPanel.add(createPrintPanel());
		return actionPanel;
	}

	private Widget createPrintPanel() {
		HorizontalPanel printPanel = new HorizontalPanel();
		printPanel.add(createPrintAmountTextBox());
		printPanel.add(createPrintButton());
		return printPanel;
	}

	private Widget createPrintAmountTextBox() {
		printAmountTextBox = new TextBox();
		printAmountTextBox.setWidth("30px");
		printAmountTextBox.setValue("5");
		return printAmountTextBox;
	}

	private Widget createCreateButton() {
		Button createButton = new Button(textMessages.createPromo());
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new OpenCreatePromoPopupEvent());
			}
		});
		return createButton;
	}

	private Widget createPrintButton() {
		printButton = new Button(textMessages.printPromo());
		printButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				printPromos();
			}
		});
		return printButton;
	}

	private Widget createDeactivateButton() {
		deactivateButton = new Button(textMessages.deactivate());
		deactivateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				deactivatePromo();
			}
		});
		return deactivateButton;
	}

	private Widget createActivateButton() {
		activateButton = new Button(textMessages.activate());
		activateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				activatePromo();
			}
		});
		return activateButton;
	}

}
