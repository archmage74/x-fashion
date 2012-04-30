package com.xfashion.client.user;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.ErrorPopup;
import com.xfashion.client.resources.UserMessages;
import com.xfashion.shared.UserDTO;

public class UserManagement {

	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	private UserMessages userMessages;
	
	private Panel panel;
	
	private List<UserDTO> users;
	
	private ListBox userListBox;
	
	private TextBox usernameTextBox;
	private TextArea detailsTextBox;
	private TextBox emailTextBox;
	private CheckBox enabledCheckBox;
	private Button sendPasswordButton;
	private UserDTO currentUser;
	
	
	private ErrorPopup errorPopup;
	
	public UserManagement() {
		userMessages = GWT.create(UserMessages.class);
		users = new ArrayList<UserDTO>();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = createPanel();
		}
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
		
		Panel details = createUserDetails();
		p.add(details);
		
		readUsers();
		
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
				prepareCreateUser();
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
	
	private Panel createUserDetails() {
		Panel p = new VerticalPanel();
		Grid grid = new Grid(4, 2);
		
		Label usernameLabel = new Label(userMessages.username() + ":");
		grid.setWidget(0, 0, usernameLabel);
		usernameTextBox = new TextBox();
		usernameTextBox.setWidth("150px");
		usernameTextBox.setEnabled(false);
		grid.setWidget(0, 1, usernameTextBox);
		
		Label detailsLabel = new Label(userMessages.description() + ":");
		grid.setWidget(1, 0, detailsLabel);
		detailsTextBox = new TextArea();
		detailsTextBox.setWidth("150px");
		detailsTextBox.setHeight("80px");
		grid.setWidget(1, 1, detailsTextBox);
		
		Label emailLabel = new Label(userMessages.email() + ":");
		grid.setWidget(2, 0, emailLabel);
		emailTextBox = new TextBox();
		emailTextBox.setWidth("150px");
		grid.setWidget(2, 1, emailTextBox);
		
		Label enabledLabel = new Label(userMessages.enabled() + ":");
		grid.setWidget(3, 0, enabledLabel);
		enabledCheckBox = new CheckBox();
		grid.setWidget(3, 1, enabledCheckBox);
		
		p.add(grid);
		
		HorizontalPanel nav = new HorizontalPanel();
		sendPasswordButton = new Button(userMessages.sendPassword());
		sendPasswordButton.setTitle(userMessages.sendPasswordHint());
		sendPasswordButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendPassword(currentUser);
			}
		});
		sendPasswordButton.setEnabled(false);
		nav.add(sendPasswordButton);
		
		Button saveButton = new Button(userMessages.save());
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveUser();
			}
		});
		nav.add(saveButton);
		
		p.add(nav);
		
		return p;
	}
	
	private void readUsers() {
		AsyncCallback<List<UserDTO>> callback = new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
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
			userListBox.addItem(u.getUsername());
		}
	}
	
	private void updateDetails(UserDTO user) {
		if (user == null) {
			usernameTextBox.setValue("");
			detailsTextBox.setValue("");
			emailTextBox.setValue("");
			enabledCheckBox.setValue(true);
		} else {
			usernameTextBox.setValue(noNullString(user.getUsername()));
			detailsTextBox.setValue(noNullString(user.getDescription()));
			emailTextBox.setValue(noNullString(user.getEmail()));
			enabledCheckBox.setValue(user.getEnabled());
		}
	}
	
	private String noNullString(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	private void prepareCreateUser() {
		usernameTextBox.setEnabled(true);
		sendPasswordButton.setEnabled(false);
		currentUser = null;
		UserDTO newUser = new UserDTO();
		updateDetails(newUser);
	}

	private void userSelection() {
		usernameTextBox.setEnabled(false);
		sendPasswordButton.setEnabled(true);
		if (userListBox.getSelectedIndex() != -1) {
			currentUser = users.get(userListBox.getSelectedIndex());
		} else {
			currentUser = null;
		}
		updateDetails(currentUser);
	}
	
	private void saveUser() {
		try {
			if (currentUser == null) {
				saveNewUser(); 
			} else {
				saveExistingUser();
			}
		} catch (CreateUserException e) {
			showError(e.getMessage());
		}
	}
	
	private void saveNewUser() throws CreateUserException {
		UserDTO user = new UserDTO();
		updateUserFromDetails(user);
		AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				users.add(result);
				updateUserList(users);
				userListBox.setSelectedIndex(users.size() - 1);
				userSelection();
			}
			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}
		};
		userService.createUser(user, callback);
	}

	private void saveExistingUser() throws CreateUserException {
		updateUserFromDetails(currentUser);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				updateUserList(users);
			}
			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}
		};
		userService.updateUser(currentUser, callback);
	}
	
	private void updateUserFromDetails(UserDTO user) throws CreateUserException {
		if (usernameTextBox.getValue() == null || usernameTextBox.getValue().length() == 0) {
			throw new CreateUserException(userMessages.errorUsernameEmpty());
		}
		if (emailTextBox.getValue() == null || emailTextBox.getValue().length() == 0) {
			throw new CreateUserException(userMessages.errorEmailEmpty());
		}
		user.setUsername(usernameTextBox.getValue());
		user.setDescription(detailsTextBox.getValue());
		user.setEmail(emailTextBox.getValue());
		user.setEnabled(enabledCheckBox.getValue());
	}
	
	private void sendPassword(UserDTO user) {
		if (user == null) {
			showError(userMessages.errorNoUserSelected());
			return;
		}
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				showError(userMessages.passwordSent());
			}
			@Override
			public void onFailure(Throwable caught) {
				showError(caught.getMessage());
			}
		};
		userService.generateAndSendPassword(user, callback);
	}
	
	private void showError(String errorMessage) {
		if (errorPopup == null) {
			errorPopup = new ErrorPopup();
		}
		errorPopup.showPopup(errorMessage);
	}
}
