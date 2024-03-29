package com.xfashion.client.notepad.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Xfashion;
import com.xfashion.client.dialog.YesNoCallback;
import com.xfashion.client.dialog.YesNoPopup;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.EanFormatException;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.UserDTO;

public abstract class NotepadActionPopup {

	private static final String LISTBOX_WIDTH = "400px";

	protected static final int TYPE_NOTEPAD = 0;
	protected static final int TYPE_DELIVERY_NOTICE = 1;

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	protected NotepadDTO currentNotepad;
	protected List<NotepadDTO> savedNotepads;
	protected List<DeliveryNoticeDTO> savedDeliveryNotices;
	protected List<UserDTO> knownUsers;
	protected int currentType;

	protected DialogBox dialogBox;
	protected ListBox notepadListBox;
	protected Label nameLabel;
	protected TextBox nameTextBox;
	protected ListBox userListBox;
	protected Button deleteButton;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected BarcodeHelper barcodeHelper;

	protected abstract void performAction();

	protected abstract String getActionButtonText();

	public NotepadActionPopup() {
		super();
		currentType = TYPE_NOTEPAD;
		textMessages = GWT.create(TextMessages.class);
		errorMessages = GWT.create(ErrorMessages.class);
		barcodeHelper = new BarcodeHelper();
	}

	public void show(NotepadDTO notepad) {
		this.currentNotepad = notepad;
		if (dialogBox == null) {
			dialogBox = create();
			postCreate();
		}
		if (!validateOnShow()) {
			return;
		}
		dialogBox.center();
		refresh();
	}

	public void hide() {
		if (dialogBox != null && dialogBox.isShowing()) {
			resetListBoxes();
			dialogBox.hide();
		}
	}

	public DialogBox create() {
		dialogBox = new DialogBox();

		VerticalPanel vp = new VerticalPanel();

		vp.add(createListBox());
		vp.add(createNamePanel());
		vp.add(createTypeListBox());
		vp.add(createUserListBox());

		HorizontalPanel nav = new HorizontalPanel();
		nav.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		nav.add(createOkButton());
		nav.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		nav.add(createDeleteButton());
		nav.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
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

	private Widget createDeleteButton() {
		deleteButton = new Button(textMessages.delete());
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				requestDelete();
			}
		});
		return deleteButton;
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

	private Widget createTypeListBox() {
		final ListBox typeListBox = new ListBox();
		typeListBox.setWidth(LISTBOX_WIDTH);
		typeListBox.addItem(textMessages.notepadTypeNotepad());
		typeListBox.addItem(textMessages.notepadTypeDeliveryNotice());
		typeListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				changeType(typeListBox.getSelectedIndex());
			}
		});
		return typeListBox;
	}

	private Widget createUserListBox() {
		userListBox = new ListBox();
		userListBox.setWidth(LISTBOX_WIDTH);
		return userListBox;
	}

	protected Panel createNamePanel() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(createNameLabel());
		hp.add(createNameTextBox());
		return hp;
	}
	
	protected Label createNameLabel() {
		nameLabel = new Label(textMessages.notepadDefaultNameLabel() + ":");
		return nameLabel;
	}
	
	protected Widget createNameTextBox() {
		nameTextBox = new TextBox();
		nameTextBox.setWidth("120px");
		return nameTextBox;
	}

	protected ListBox createListBox() {
		notepadListBox = new ListBox();
		notepadListBox.setWidth(LISTBOX_WIDTH);
		notepadListBox.setVisibleItemCount(14);
		return notepadListBox;
	}

	private void updateListBoxNotepads(Collection<NotepadDTO> notepads) {
		NotepadByCreationDateDesc comparator = new NotepadByCreationDateDesc();
		List<NotepadDTO> sorted = new ArrayList<NotepadDTO>(notepads);
		Collections.sort(sorted, comparator);
		notepadListBox.clear();
		for (NotepadDTO dto : sorted) {
			String notepadName = dto.getName();
			Date creationDate = dto.getCreationDate();
			notepadListBox.addItem(textMessages.notepadListBoxLine(notepadName, creationDate));
			savedNotepads.add(dto);
		}
	}

	private void updateListBoxDeliveryNotices(Collection<DeliveryNoticeDTO> deliveryNotices) {
		DeliveryNoticeByCreationDateDesc comparator = new DeliveryNoticeByCreationDateDesc();
		List<DeliveryNoticeDTO> sorted = new ArrayList<DeliveryNoticeDTO>(deliveryNotices);
		Collections.sort(sorted, comparator);
		notepadListBox.clear();
		for (DeliveryNoticeDTO deliveryNotice : sorted) {
			String deliveryNoticeId = "";
			try {
				deliveryNoticeId = barcodeHelper.generateDeliveryNoticeEan(deliveryNotice.getId());
			} catch (EanFormatException e) {
				deliveryNoticeId = "???";
			}
			String shopName = deliveryNotice.getTargetShop().getShortName();
			Date creationDate = deliveryNotice.getNotepad().getCreationDate();
			notepadListBox.addItem(textMessages.deliveryNoticeListBoxLine(deliveryNoticeId, shopName, creationDate));
			savedDeliveryNotices.add(deliveryNotice);
		}
	}

	private void updateUsers(Collection<UserDTO> users) {
		userListBox.clear();
		knownUsers.clear();
		for (UserDTO user : users) {
			if (!UserService.ROOT_USERNAME.equals(user.getUsername())) {
				userListBox.addItem(user.getShop().getShortName());
				knownUsers.add(user);
			}
		}
	}

	private void loadNotepads() {
		savedNotepads = new ArrayList<NotepadDTO>();
		AsyncCallback<Set<NotepadDTO>> callback = new AsyncCallback<Set<NotepadDTO>>() {
			@Override
			public void onSuccess(Set<NotepadDTO> result) {
				updateListBoxNotepads(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readOwnNotepads(callback);
	}

	private void loadDeliveryNotices() {
		savedDeliveryNotices = new ArrayList<DeliveryNoticeDTO>();
		AsyncCallback<Set<DeliveryNoticeDTO>> callback = new AsyncCallback<Set<DeliveryNoticeDTO>>() {
			@Override
			public void onSuccess(Set<DeliveryNoticeDTO> result) {
				updateListBoxDeliveryNotices(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readOwnDeliveryNotices(callback);
	}

	private void loadUsers() {
		knownUsers = new ArrayList<UserDTO>();
		AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onSuccess(List<UserDTO> result) {
				updateUsers(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readUsers(callback);
	}

	private void changeType(int type) {
		currentType = type;
		refresh();
	}

	private void refresh() {
		switch (currentType) {
		case TYPE_NOTEPAD:
			loadNotepads();
			deleteButton.setEnabled(true);
			break;
		case TYPE_DELIVERY_NOTICE:
			loadDeliveryNotices();
			loadUsers();
			deleteButton.setEnabled(false);
			break;
		}
	}

	protected void resetListBoxes() {
		savedNotepads = null;
		savedDeliveryNotices = null;
		notepadListBox.clear();
		knownUsers = null;
		userListBox.clear();
	}
	
	private void requestDelete() {
		if (notepadListBox.getSelectedIndex() == -1) {
			Xfashion.fireError(errorMessages.noNotepadToDeleteSelected());
			return;
		}
		final NotepadDTO notepad = savedNotepads.get(notepadListBox.getSelectedIndex());
		String notepadName = notepad.getName();
		Date creationDate = notepad.getCreationDate();
		String desc = textMessages.notepadListBoxLine(notepadName, creationDate);

		YesNoPopup confirm = new YesNoPopup(textMessages.confirmDeleteNotepad(desc), new YesNoCallback() {
			@Override
			public void yes() {
				deleteNotepad(notepad);
			}
			@Override
			public void no() {
				// nothing to do
			}
		});
		confirm.createDialogBox().center();
	}

	private void deleteNotepad(NotepadDTO notepad) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				loadNotepads();
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.deleteNotepad(notepad.getKey(), callback);
	}
	
}
