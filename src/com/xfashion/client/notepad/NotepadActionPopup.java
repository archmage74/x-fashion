package com.xfashion.client.notepad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.notepad.NotepadDTO;

public abstract class NotepadActionPopup {

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	protected NotepadDTO notepad;
	protected List<NotepadDTO> savedNotepads;

	protected DialogBox dialogBox;
	protected ListBox listBox;
	protected TextBox nameTextBox;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;

	protected abstract void performAction();
	
	protected abstract String getActionButtonText();

	public NotepadActionPopup() {
		super();
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
	}

	public void show(NotepadDTO notepad) {
		this.notepad = notepad;
		if (dialogBox == null) {
			dialogBox = create();
			postCreate();
		}
		if (!validateOnShow()) {
			return;
		}
		dialogBox.center();
		if (savedNotepads == null) {
			loadNotepads();
		}
	}

	public void hide() {
		if (dialogBox != null && dialogBox.isShowing()) {
			savedNotepads = null;
			listBox.clear();
			dialogBox.hide();
		}
	}

	public DialogBox create() {
		dialogBox = new DialogBox();

		VerticalPanel vp = new VerticalPanel();

		vp.add(createListBox());
		vp.add(createNameTextBox());
		HorizontalPanel nav = new HorizontalPanel();
		nav.add(createOkButton());
		nav.add(createCancelButton());

		vp.add(nav);
		dialogBox.add(vp);

		return dialogBox;
	}

	/**
	 * overload to perform actions before showing
	 */
	protected boolean validateOnShow() {
		return true;
	}

	/**
	 * overload to add special handlers to widgets
	 */
	protected void postCreate() {

	}

	private Widget createOkButton() {
		Button okButton = new Button(getActionButtonText());
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				performAction();
			}
		});
		return okButton;
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

	protected Widget createNameTextBox() {
		nameTextBox = new TextBox();
		nameTextBox.setWidth("120px");
		return nameTextBox;
	}

	protected ListBox createListBox() {
		listBox = new ListBox();
		listBox.setWidth("400px");
		listBox.setVisibleItemCount(14);
		return listBox;
	}

	private void updateListBox(Collection<NotepadDTO> notepads) {
		listBox.clear();
		for (NotepadDTO notepad : notepads) {
			listBox.addItem(notepad.getName() + " - " + notepad.getCreationDate(), notepad.getKey());
			savedNotepads.add(notepad);
		}
	}

	private void loadNotepads() {
		savedNotepads = new ArrayList<NotepadDTO>();
		AsyncCallback<Set<NotepadDTO>> callback = new AsyncCallback<Set<NotepadDTO>>() {
			@Override
			public void onSuccess(Set<NotepadDTO> result) {
				updateListBox(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readOwnNotepads(callback);
	}

}