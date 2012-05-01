package com.xfashion.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.UserMessages;
import com.xfashion.shared.UserDTO;

public class UserDetails {

	private UserServiceAsync userService;
	
	private UserMessages userMessages;
	private ErrorMessages errorMessages;
	
	private UserManagement userManagement;
	
	private UserDTO currentUser;
	
	private TextBox usernameTextBox;
	private TextArea detailsTextBox;
	private TextBox emailTextBox;
	private CheckBox enabledCheckBox;
	private Button sendPasswordButton;
	
	public UserDetails() {
		currentUser = null;
		errorMessages = GWT.create(ErrorMessages.class);
		userService = (UserServiceAsync) GWT.create(UserService.class);
		userMessages = GWT.create(UserMessages.class);
	}
	
	public UserManagement getUserManagement() {
		return userManagement;
	}

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

	public Panel createUserDetails() {
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
	
	public void selectUser(UserDTO user) {
		currentUser = user;
		usernameTextBox.setEnabled(false);
		sendPasswordButton.setEnabled(true);
		updateDetails(user);
	}
	
	public void prepareCreateUser() {
		currentUser = null;
		if (userManagement == null) {
			Xfashion.fireError(errorMessages.createUserNoUserManagement());
		}
		UserDTO newUser = new UserDTO();
		usernameTextBox.setEnabled(true);
		sendPasswordButton.setEnabled(false);
		updateDetails(newUser);
	}

	public void updateDetails(UserDTO user) {
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
	
	private void saveUser() {
		try {
			if (currentUser == null) {
				saveNewUser(); 
			} else {
				saveExistingUser();
			}
		} catch (CreateUserException e) {
			Xfashion.fireError(e.getMessage());
		}
	}
	
	private void saveNewUser() throws CreateUserException {
		UserDTO user = new UserDTO();
		updateUserFromDetails(user);
		AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				if (userManagement != null) {
					userManagement.addUser(result);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.createUser(user, callback);
	}

	private void saveExistingUser() throws CreateUserException {
		updateUserFromDetails(currentUser);
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// updateUserList(users);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
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
			Xfashion.fireError(userMessages.errorNoUserSelected());
			return;
		}
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Xfashion.fireError(userMessages.passwordSent());
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.createResetPassword(user, callback);
	}

}