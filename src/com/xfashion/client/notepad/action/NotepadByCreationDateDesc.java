package com.xfashion.client.notepad.action;

import java.util.Comparator;

import com.xfashion.shared.NotepadDTO;

public class NotepadByCreationDateDesc implements Comparator<NotepadDTO> {
	
	@Override
	public int compare(NotepadDTO arg0, NotepadDTO arg1) {
		if (arg0 == arg1) {
			return 0;
		}
		if (arg0 == null || arg0.getCreationDate() == null) {
			return 1;
		}
		if (arg1 == null || arg1.getCreationDate() == null) {
			return -1;
		}
		
		return -1 * arg0.getCreationDate().compareTo(arg1.getCreationDate());
	}
	
}
