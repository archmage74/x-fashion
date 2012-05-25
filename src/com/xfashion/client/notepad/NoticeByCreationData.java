package com.xfashion.client.notepad;

import java.util.Comparator;

import com.xfashion.shared.notepad.NotepadDTO;

public class NoticeByCreationData implements Comparator<NotepadDTO> {

	@Override
	public int compare(NotepadDTO arg0, NotepadDTO arg1) {
		return arg0.getCreationDate().compareTo(arg1.getCreationDate());
	}

}
