package com.xfashion.client.notepad;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.OpenDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.OpenNotepadEvent;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class OpenNotepadPopup extends NotepadActionPopup {

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
				Xfashion.fireError("entering of EAN codes not supported yet");
			}
		}
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

	private void loadDeliveryNotice() {
		if (notepadListBox.getSelectedIndex() != -1) {
			DeliveryNoticeDTO deliveryNotice = savedDeliveryNotices.get(notepadListBox.getSelectedIndex());
			Xfashion.eventBus.fireEvent(new OpenDeliveryNoticeEvent(deliveryNotice));
			resetListBoxes();
			hide();
		} else {
			Xfashion.fireError(errorMessages.noNotepadToOpenSelected());
		}
	}

}
