package com.xfashion.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.UserMessages;
import com.xfashion.shared.UserDTO;

public class UserProfile {
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
	
	private Panel panel;
	
	private Panel loginPanel;
	
	private Panel profilePanel;
	
	private TextBox usernameTextBox;
	private TextBox passwordTextBox;
	private Button loginButton;

	private UserMessages userMessages;

	public UserProfile() {
		userMessages = GWT.create(UserMessages.class);
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = new SimplePanel();
		}
		updatePanel();
		return panel;
	}
	
	private void updatePanel() {
		panel.clear();
		if (UserManagement.user == null) {
			checkServerLogin();
		} else {
			showProfilePanel();
		}
	}

	private void checkServerLogin() {
		AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				if (result == null) {
					showLoginPanel();
				} else {
					loginSuccess(result);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		}; 
		userService.getOwnUser(callback);
	}
	
	private void showLoginPanel() {
		if (loginPanel == null) {
			loginPanel = createLoginPanel();
		}
		panel.add(loginPanel);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
	        public void execute () {
	        	usernameTextBox.setFocus(true);
	        }
	    });
	}
	
	private void showProfilePanel() {
		if (profilePanel == null) {
			profilePanel = createProfilePanel();
		}
		panel.add(profilePanel);
	}
	
	private Panel createLoginPanel() {
		VerticalPanel vp = new VerticalPanel();

		Label header = createHeader(userMessages.userLoginHeader());
		vp.add(header);

		Grid loginGrid = createLoginForm();
		vp.add(loginGrid);
		
		Button loginButton = createLoginButton();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp.add(loginButton);

		return vp;
	}
	
	private Grid createLoginForm() {
		Grid grid = new Grid(2, 2);
		
		Label usernameLabel = new Label(userMessages.username() + ":");
		usernameTextBox = new TextBox();
		usernameTextBox.setWidth("150px");
		grid.setWidget(0, 0, usernameLabel);
		grid.setWidget(0, 1, usernameTextBox);
		
		Label passwordLabel = new Label(userMessages.password() + ":");
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setWidth("150px");
		grid.setWidget(1, 0, passwordLabel);
		grid.setWidget(1, 1, passwordTextBox);
		
		return grid;
	}
	
	private Button createLoginButton() {
		loginButton = new Button(userMessages.login());
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendLogin();
			}
		});
		return loginButton;
	}
	
	private void sendLogin() {
		loginButton.setEnabled(false);
		AsyncCallback<UserDTO> callback = new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				if (result == null) {
					Xfashion.fireError(userMessages.loginFailed());
					loginButton.setEnabled(true);
				} else {
					loginSuccess(result);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
				loginButton.setEnabled(true);
				
			}
		}; 
		userService.login(usernameTextBox.getValue(), passwordTextBox.getValue(), callback);
	}
	
	private void loginSuccess(UserDTO result) {
		UserManagement.user = result;
		Xfashion.eventBus.fireEvent(new LoginEvent(result));
		scheduleSessionKeepAlive();
		updatePanel();
	}

	private void scheduleSessionKeepAlive() {
		Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand () {
	        public boolean execute () {
	        	sendKeepAlive();
	        	return true;
	        }
	    }, UserService.KEEP_ALIVE_TIMEOUT);
	}
	
	private void sendKeepAlive() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// nothing more to do, keep alive was successful
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO: should we report an error? maybe just if 2 or 3 fail?
			}
		}; 
		userService.keepAlive(UserManagement.user.getUsername(), callback);
	}

	private Panel createProfilePanel() {
		VerticalPanel vp = new VerticalPanel();
		
		Label header = createHeader(userMessages.userProfileHeader());
		vp.add(header);
		
		UserDetails userDetails = new UserDetails();
		Panel up = userDetails.createUserDetails();
		vp.add(up);
		userDetails.selectUser(UserManagement.user);
		
		return vp;
	}
	
	private Label createHeader(String text) {
		Label l = new Label(text);
		l.setStyleName("contentHeader");
		return l;
	}

}
