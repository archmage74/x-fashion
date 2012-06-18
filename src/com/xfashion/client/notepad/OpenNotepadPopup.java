package com.xfashion.client.notepad;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.OpenDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.OpenNotepadEvent;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

public class OpenNotepadPopup extends NotepadActionPopup {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	public OpenNotepadPopup() {
		super();
	}

	@Override
	protected boolean validateOnShow() {
		if (currentNotepad.getArticles().size() != 0) {
			Xfashion.fireError(errorMessages.notepadNotEmpty());
			return false;
		}
		return true;
	}
	
	@Override
	protected void postCreate() {
		nameTextBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				checkForEAN();
			}
		});
	}
	
	@Override
	protected void performAction() {
		switch (currentType) {
		case TYPE_NOTEPAD:
			loadNotepad();
			break;
		case TYPE_DELIVERY_NOTICE:
			loadDeliveryNotice();
		}
	}

	@Override
	protected String getActionButtonText() {
		return textMessages.open();
	}
	
	private void checkForEAN() {
		String name = nameTextBox.getValue();
		if (name != null && name.length() == 13) {
			for (Character c : name.toCharArray()) {
				if (c < '0' || c > '9') {
					return;
				}
			}
			if (name.toCharArray()[0] == BarcodeHelper.DELIVERY_NOTICE_PREFIX_CHAR) {
				loadDeliveryNoticeByEAN(nameTextBox.getValue());
			} else {
				Xfashion.fireError(errorMessages.noValidDeliveryNoticeEAN());
				nameTextBox.setText("");
			}
		}
	}
	
	private void loadDeliveryNoticeByEAN(String ean) {
		String idString = ean.substring(0, 12);
		Long id = Long.parseLong(idString);
		AsyncCallback<DeliveryNoticeDTO> callback = new AsyncCallback<DeliveryNoticeDTO>() {
			@Override
			public void onSuccess(DeliveryNoticeDTO result) {
				validateLoadDeliveryNotice(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readDeliveryNoticeById(id, callback);
	}

	private void loadNotepad() {
		if (notepadListBox.getSelectedIndex() != -1) {
			NotepadDTO stored = savedNotepads.get(notepadListBox.getSelectedIndex());
			Xfashion.eventBus.fireEvent(new OpenNotepadEvent(stored));
			resetListBoxes();
			hide();
		} else {
			Xfashion.fireError(errorMessages.noNotepadToOpenSelected());
		}
	}

	private void validateLoadDeliveryNotice(DeliveryNoticeDTO deliveryNotice) {
		String myShopKey = UserManagement.user.getShop().getKeyString();
		if (deliveryNotice == null) {
			Xfashion.fireError(errorMessages.noValidDeliveryNoticeEAN());
		} else if (!deliveryNotice.getSourceShopKey().equals(myShopKey) && !deliveryNotice.getTargetShopKey().equals(myShopKey)) {
			Xfashion.fireError(errorMessages.noValidDeliveryNoticeEAN());
		} else {
			loadDeliveryNotice(deliveryNotice);
		}
	}
	
	private void loadDeliveryNotice() {
		if (notepadListBox.getSelectedIndex() != -1) {
			DeliveryNoticeDTO deliveryNotice = savedDeliveryNotices.get(notepadListBox.getSelectedIndex());
			loadDeliveryNotice(deliveryNotice);
		} else {
			Xfashion.fireError(errorMessages.noNotepadToOpenSelected());
		}
	}

	private void loadDeliveryNotice(DeliveryNoticeDTO deliveryNotice) {
		Xfashion.eventBus.fireEvent(new OpenDeliveryNoticeEvent(deliveryNotice));
		resetListBoxes();
		hide();
	}

}
