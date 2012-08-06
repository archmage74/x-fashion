package com.xfashion.client.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.UserDTO;

public class SelectUserListBox extends ListBox implements HasSelectionHandlers<UserDTO> {
	
	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	protected UserDTO selectedUser;
	
	protected List<UserDTO> users;
	
	public SelectUserListBox() {
		this(null);
	}
	
	public SelectUserListBox(UserDTO selectedUser) {
		super();
		this.selectedUser = selectedUser;
		this.users = new ArrayList<UserDTO>();
	}
	
	public void init() {
		loadUsers();
		this.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				handleUserSelection();
			}
		});
	}
	
	protected void handleUserSelection() {
		SelectionEvent.fire(this, users.get(getSelectedIndex()));
	}

	@Override
	public HandlerRegistration addSelectionHandler(final SelectionHandler<UserDTO> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}

	private void updateUsers(Collection<UserDTO> loadedUsers) {
		this.clear();
		users.clear();
		ArrayList<UserDTO> sortedUsers = new ArrayList<UserDTO>(loadedUsers);
		Collections.sort(sortedUsers, new UserByShortName());
		for (UserDTO user : sortedUsers) {
			if (!UserService.ROOT_USERNAME.equals(user.getUsername())) {
				addItem(user.getShop().getShortName());
				users.add(user);
				if (selectedUser != null && user.getKey().equals(selectedUser.getKey())) {
					setSelectedIndex(getItemCount() - 1);
				}
			}
		}
	}

	private void loadUsers() {
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

}
