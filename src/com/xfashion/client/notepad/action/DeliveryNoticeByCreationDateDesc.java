package com.xfashion.client.notepad.action;

import java.util.Comparator;

import com.xfashion.shared.DeliveryNoticeDTO;

public class DeliveryNoticeByCreationDateDesc implements Comparator<DeliveryNoticeDTO> {
	
	NotepadByCreationDateDesc notepadComparator;
	
	public DeliveryNoticeByCreationDateDesc() {
		notepadComparator = new NotepadByCreationDateDesc();
	}

	@Override
	public int compare(DeliveryNoticeDTO o1, DeliveryNoticeDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getNotepad() == null) {
			return 1;
		}
		if (o2 == null || o2.getNotepad() == null) {
			return -1;
		}
		return notepadComparator.compare(o1.getNotepad(), o2.getNotepad());
	}
	
}
