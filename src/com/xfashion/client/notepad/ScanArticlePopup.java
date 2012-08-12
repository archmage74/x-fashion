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
import com.xfashion.client.at.ArticleScanner;
import com.xfashion.client.notepad.event.NotepadAddArticleEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;

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
		final ArticleScanner articleScanner = new ArticleScanner() {
			@Override
			public void onSuccess(long ean) {
				addArticle(ean);
			}
			@Override
			public void onError(String scannedText) {
				Xfashion.fireError(errorMessages.noValidArticleTypeEAN());
				clear();
			}
		};
		barcodeTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				articleScanner.scan(barcodeTextBox.getValue());
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

	private void addArticle(long productNumber) {
		ArticleTypeDTO articleType = provider.retrieveArticleType(productNumber);
		Xfashion.eventBus.fireEvent(new NotepadAddArticleEvent(articleType));
		clear();
	}

	private void clear() {
		barcodeTextBox.setText("");
	}
	
}
