package com.xfashion.client.notepad;

import java.util.Date;

import com.xfashion.client.Xfashion;
import com.xfashion.client.notepad.event.SaveDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.SaveNotepadEvent;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class SaveNotepadPopup extends NotepadActionPopup {

	public SaveNotepadPopup() {
		super();
	}

	@Override
	protected void performAction() {
		switch (currentType) {
		case TYPE_NOTEPAD:
			saveNotepad();
			break;
		case TYPE_DELIVERY_NOTICE:
			saveDeliveryNotice();
		}
	}
	
	@Override
	protected String getActionButtonText() {
		return textMessages.save();
	}
	
	@Override
	protected boolean validateOnShow() {
		if (currentNotepad.getArticles().size() == 0) {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
			return false;
		}
		return true;
	}
	
	private void save(NotepadDTO notepad) {
		Xfashion.eventBus.fireEvent(new SaveNotepadEvent(notepad));
		hide();
	}
	
	private void save(DeliveryNoticeDTO deliveryNotice) {
		Xfashion.eventBus.fireEvent(new SaveDeliveryNoticeEvent(deliveryNotice));
		hide();
	}
	
	private void saveNotepad() {
		if (notepadListBox.getSelectedIndex() != -1) {
			NotepadDTO stored = savedNotepads.get(notepadListBox.getSelectedIndex());
			currentNotepad.setKey(stored.getKey());
			currentNotepad.setName(stored.getName());
			currentNotepad.setCreationDate(new Date());
			save(currentNotepad);
		} else {
			if (nameTextBox.getValue() != null && nameTextBox.getValue().length() > 0) {
				currentNotepad.setKey(null);
				currentNotepad.setName(nameTextBox.getValue());
				currentNotepad.setCreationDate(new Date());
				save(currentNotepad);
			} else {
				Xfashion.fireError(errorMessages.noNotepadNameSpecified());
			}
		}
	}

	private void saveDeliveryNotice() {
		if (notepadListBox.getSelectedIndex() != -1) {
			DeliveryNoticeDTO deliveryNotice = savedDeliveryNotices.get(notepadListBox.getSelectedIndex());
			currentNotepad.setKey(deliveryNotice.getNotepad().getKey());
			currentNotepad.setName(deliveryNotice.getNotepad().getName());
			currentNotepad.setCreationDate(new Date());
			deliveryNotice.setNotepad(currentNotepad);
			save(deliveryNotice);
		} else {
			currentNotepad.setKey(null);
			currentNotepad.setName(nameTextBox.getValue());
			currentNotepad.setCreationDate(new Date());
			DeliveryNoticeDTO deliveryNotice = new DeliveryNoticeDTO();
			deliveryNotice.setNotepad(currentNotepad);
			deliveryNotice.setTargetShop(knownUsers.get(userListBox.getSelectedIndex()).getShop());
			save(deliveryNotice);
		}
		
	}
}
