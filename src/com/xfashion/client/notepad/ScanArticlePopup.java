package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;

public class ScanArticlePopup {

	protected DialogBox dialogBox;
	protected TextBox barcodeTextBox;
	
	private ArticleAmountDataProvider provider;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	
	public ScanArticlePopup(ArticleAmountDataProvider provider) {
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
		this.provider = provider;
	}
	
	public void show() {
		if (dialogBox == null) {
			dialogBox = create();
		}
		clear();
		dialogBox.center();
		barcodeTextBox.setFocus(true);
	}
	
	public void hide() {
		if (dialogBox != null && dialogBox.isShowing()) {
			clear();
			dialogBox.hide();
		}
	}
	
	public DialogBox create() {
		dialogBox = new DialogBox();
		VerticalPanel vp = new VerticalPanel();

		vp.add(createBarcodeTextBox());
		vp.add(createCancelButton());

		dialogBox.add(vp);

		return dialogBox;
	}

	private Widget createBarcodeTextBox() {
		barcodeTextBox = new TextBox();
		barcodeTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkForEAN();
			}
		});
		return barcodeTextBox;
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

	private void checkForEAN() {
		String name = barcodeTextBox.getValue();
		if (name != null && name.length() == 13) {
			for (Character c : name.toCharArray()) {
				if (c < '0' || c > '9') {
					return;
				}
			}
			if (name.toCharArray()[0] == BarcodeHelper.ARTICLE_PREFIX_CHAR) {
				addArticle(barcodeTextBox.getValue());
			} else {
				Xfashion.fireError(errorMessages.noValidDeliveryNoticeEAN());
				clear();
			}
		}
	}
	
	private void addArticle(String ean) {
		String idString = ean.substring(0, 12);
		Long productNumber = Long.parseLong(idString);
		ArticleTypeDTO articleType = provider.retrieveArticleType(productNumber);
		Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(articleType));
		clear();
	}

	private void clear() {
		barcodeTextBox.setText("");
	}
	
}
