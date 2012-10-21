package com.xfashion.client.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.UserMessages;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class UserManagement {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	private UserMessages userMessages;
	
	private Panel panel;
	
	private UserDetails userDetails;
	
	private List<UserDTO> users;
	
	private ListBox userListBox;
	
	private UserDTO currentUser;
	
	public static UserDTO user = null;
	
	public static boolean hasRole(UserRole... roles) {
		if (user == null) {
			return false;
		}
		for (UserRole role : roles) {
			if (role.equals(user.getRole())) {
				return true;
			}
		}
		return false;
	}
	
	public UserManagement() {
		userMessages = GWT.create(UserMessages.class);
		users = new ArrayList<UserDTO>();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = createPanel();
		}
		readUsers();
		return panel;
	}
	
	private Panel createPanel() {
		Panel p = new VerticalPanel();

		Label header = createHeader();
		p.add(header);
		
		Panel content = createContent(); 
		p.add(content);
		
		return p;
	}
	
	private Label createHeader() {
		Label l = new Label(userMessages.userManagementHeader());
		l.setStyleName("contentHeader");
		return l;
	}
	
	private Panel createContent() {
		Panel p = new HorizontalPanel();
		
		Panel userlist = createUserList();
		p.add(userlist);
		
		userDetails = new UserDetails();
		userDetails.setUserManagement(this);
		Panel details = userDetails.createUserDetails();
		p.add(details);
		
		return p;
	}
	
	private Panel createUserList() {
		Panel p = new VerticalPanel();
		
		userListBox = createUserListBox();
		p.add(userListBox);
		
		Button createUserButton = new Button(userMessages.createUser());
		createUserButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				currentUser = null;
				userDetails.prepareCreateUser();
			}
		});
		p.add(createUserButton);
		
		return p;
	}
	
	private ListBox createUserListBox() {
		final ListBox lb = new ListBox();
		lb.setVisibleItemCount(25);
		lb.setWidth("300px");
		lb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				userSelection();
			}
		});
		lb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				userSelection();
			}
		});
		
		return lb;
	}
	
	
	private void readUsers() {
		AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(List<UserDTO> result) {
				users.clear();
				users.addAll(result);
				updateUserList(result);
			}
		};
		userService.readUsers(callback);
	}
	
	private void updateUserList(List<UserDTO> users) {
		userListBox.clear();
		for (UserDTO u : users) {
			if (u.getLastKeepAlive() == null) {
				userListBox.addItem(userMessages.userListBoxLineNeverOnline(u.getUsername()));
			} else if (u.getLastKeepAlive().getTime() + UserService.KEEP_ALIVE_TIMEOUT > new Date().getTime()) {
				userListBox.addItem(userMessages.userListBoxLineOnline(u.getUsername()));
			} else {
				userListBox.addItem(userMessages.userListBoxLineNotOnline(u.getUsername(), u.getLastKeepAlive()));
			}
		}
	}

	private void userSelection() {
		if (userListBox.getSelectedIndex() != -1) {
			currentUser = users.get(userListBox.getSelectedIndex());
		} else {
			currentUser = null;
		}
		userDetails.selectUser(currentUser);
	}
	
	public void addUser(UserDTO user) {
		users.add(user);
		updateUserList(users);
		userListBox.setSelectedIndex(users.size() - 1);
		userSelection();
	}
	
}