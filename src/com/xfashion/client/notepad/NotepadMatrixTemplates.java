package com.xfashion.client.notepad;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface NotepadMatrixTemplates extends SafeHtmlTemplates {

	@Template("<div class=\"matrixCell matrixBo matrixRi\">" +
			"<button class=\"matrixButton matrixNotepadButton\">+1</button>" +
			"<button class=\"matrixButton matrixNotepadButton\">+10</button></div>")
	SafeHtml removeFromNotepadCell();

}
