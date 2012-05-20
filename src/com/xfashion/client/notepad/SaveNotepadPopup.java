package com.xfashion.client.notepad;

import java.util.Date;

import com.xfashion.client.Xfashion;
import com.xfashion.shared.notepad.NotepadDTO;

public class SaveNotepadPopup extends NotepadActionPopup {

	public SaveNotepadPopup() {
		super();
	}

	@Override
	protected void performAction() {
		if (listBox.getSelectedIndex() != -1) {
			NotepadDTO stored = savedNotepads.get(listBox.getSelectedIndex());
			notepad.setKey(stored.getKey());
			notepad.setName(stored.getName());
			notepad.setCreationDate(new Date());
			save();
		} else {
			if (nameTextBox.getValue() != null && nameTextBox.getValue().length() > 0) {
				notepad.setKey(null);
				notepad.setName(nameTextBox.getValue());
				notepad.setCreationDate(new Date());
				save();
			} else {
				Xfashion.fireError(errorMessages.noNotepadNameSpecified());
			}
		}
	}

	@Override
	protected String getActionButtonText() {
		return textMessages.save();
	}
	
	@Override
	protected boolean validateOnShow() {
		if (notepad.getArticles().size() == 0) {
			Xfashion.fireError(errorMessages.noArticlesInNotepad());
			return false;
		}
		return true;
	}
	
	private void save() {
		Xfashion.eventBus.fireEvent(new SaveNotepadEvent(notepad));
		hide();
	}

}
