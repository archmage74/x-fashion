package com.xfashion.client.notepad;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.notepad.NotepadDTO;

public class OpenNotepadPopup extends NotepadActionPopup {

	public OpenNotepadPopup() {
		super();
	}

	@Override
	protected boolean validateOnShow() {
		if (notepad.getArticles().size() != 0) {
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
		if (listBox.getSelectedIndex() != -1) {
			NotepadDTO stored = savedNotepads.get(listBox.getSelectedIndex());
			load(stored);
		} else {
			Xfashion.fireError(errorMessages.noNotepadToOpenSelected());
		}
	}

	@Override
	protected String getActionButtonText() {
		return textMessages.open();
	}
	
	private void load(NotepadDTO notepad) {
		savedNotepads = null;
		Xfashion.eventBus.fireEvent(new OpenNotepadEvent(notepad));
		hide();
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

}
