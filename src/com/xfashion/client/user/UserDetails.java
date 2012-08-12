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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.UserMessages;
import com.xfashion.shared.UserCountry;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class UserDetails {

	private UserServiceAsync userService;

	private UserMessages userMessages;
	private ErrorMessages errorMessages;

	private UserManagement userManagement;

	private UserDTO currentUser;

	private TextBox usernameTextBox;
	private TextBox shortNameTextBox;
	private TextBox shopNameTextBox;
	private TextBox streetTextBox;
	private TextBox housenumberTextBox;
	private TextBox postalcodeTextBox;
	private TextBox cityTextBox;
	private TextBox emailTextBox;
	private ListBox countryListBox;
	private ListBox roleListBox;
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
		Grid grid = new Grid(9, 2);

		int row = 0;

		addUsernameLine(grid, row++);
		addShortNameLine(grid, row++);
		addShopNameLine(grid, row++);
		addStreetAndHousenumberLine(grid, row++);
		addPostalcodeAndCityLine(grid, row++);
		addEmailLine(grid, row++);
		addCountryLine(grid, row++);
		addRoleLine(grid, row++);
		addEnabledLine(grid, row++);

		p.add(grid);

		Panel nav = createNavPanel();
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
		usernameTextBox.setEnabled(true);
		sendPasswordButton.setEnabled(false);
		updateDetails(currentUser);
	}

	public void updateDetails(UserDTO user) {
		if (user == null) {
			usernameTextBox.setValue("");
			clearShopTextBoxes();
			emailTextBox.setValue("");
			enabledCheckBox.setValue(true);
			countryListBox.setSelectedIndex(0);
		} else {
			usernameTextBox.setValue(noNullString(user.getUsername()));
			if (user.getShop() != null) {
				shortNameTextBox.setValue(noNullString(user.getShop().getShortName()));
				shopNameTextBox.setValue(noNullString(user.getShop().getName()));
				streetTextBox.setValue(noNullString(user.getShop().getStreet()));
				housenumberTextBox.setValue(noNullString(user.getShop().getHousenumber()));
				postalcodeTextBox.setValue(noNullString(user.getShop().getPostalcode()));
				cityTextBox.setValue(noNullString(user.getShop().getCity()));
				for (int i = 0; i < countryListBox.getItemCount(); i++) {
					if (countryListBox.getValue(i).equals(user.getShop().getCountry().name())) {
						countryListBox.setSelectedIndex(i);
						break;
					}
				}
			} else {
				shopNameTextBox.setValue("");
				clearShopTextBoxes();
			}
			emailTextBox.setValue(noNullString(user.getEmail()));
			enabledCheckBox.setValue(user.getEnabled());
			for (int i = 0; i < roleListBox.getItemCount(); i++) {
				if (roleListBox.getValue(i).equals(user.getRole().name())) {
					roleListBox.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	private void clearShopTextBoxes() {
		shortNameTextBox.setValue("");
		shopNameTextBox.setValue("");
		streetTextBox.setValue("");
		housenumberTextBox.setValue("");
		postalcodeTextBox.setValue("");
		cityTextBox.setValue("");
	}

	private void addUsernameLine(Grid grid, int row) {
		usernameTextBox = addLine(grid, row, userMessages.username());
	}

	private void addShortNameLine(Grid grid, int row) {
		shortNameTextBox = addLine(grid, row, userMessages.shortName());
	}

	private void addShopNameLine(Grid grid, int row) {
		shopNameTextBox = addLine(grid, row, userMessages.shopName());
	}

	private void addEmailLine(Grid grid, int row) {
		emailTextBox = addLine(grid, row, userMessages.email());
		if (!UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			emailTextBox.setEnabled(false);
		}
	}

	private TextBox addLine(Grid grid, int row, String labelText) {
		Label label = new Label(labelText + ":");
		grid.setWidget(row, 0, label);
		TextBox textBox = new TextBox();
		textBox.setWidth("150px");
		grid.setWidget(row, 1, textBox);
		row++;
		return textBox;
	}

	private void addStreetAndHousenumberLine(Grid grid, int row) {
		Label label = new Label(userMessages.streetAndHousenumber() + ":");
		grid.setWidget(row, 0, label);
		streetTextBox = new TextBox();
		streetTextBox.setWidth("110px");
		housenumberTextBox = new TextBox();
		housenumberTextBox.setWidth("40px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(streetTextBox);
		hp.add(housenumberTextBox);
		grid.setWidget(row, 1, hp);
	}

	private void addPostalcodeAndCityLine(Grid grid, int row) {
		Label label = new Label(userMessages.postalcodeAndCity() + ":");
		grid.setWidget(row, 0, label);
		postalcodeTextBox = new TextBox();
		postalcodeTextBox.setWidth("40px");
		cityTextBox = new TextBox();
		cityTextBox.setWidth("110px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(postalcodeTextBox);
		hp.add(cityTextBox);
		grid.setWidget(row, 1, hp);
	}

	private void addCountryLine(Grid grid, int row) {
		Label countryLabel = new Label(userMessages.country() + ":");
		grid.setWidget(row, 0, countryLabel);
		countryListBox = new ListBox();
		countryListBox.addItem(UserCountry.AT.longName(), UserCountry.AT.name());
		countryListBox.addItem(UserCountry.DE.longName(), UserCountry.DE.name());
		grid.setWidget(row, 1, countryListBox);
		row++;
		if (!UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			countryListBox.setEnabled(false);
		}
	}

	private void addRoleLine(Grid grid, int row) {
		Label roleLabel = new Label(userMessages.role() + ":");
		grid.setWidget(row, 0, roleLabel);
		roleListBox = new ListBox();
		roleListBox.addItem(UserRole.SHOP.name(), UserRole.SHOP.name());
		roleListBox.addItem(UserRole.ADMIN.name(), UserRole.ADMIN.name());
		roleListBox.addItem(UserRole.DEVELOPER.name(), UserRole.DEVELOPER.name());
		grid.setWidget(row, 1, roleListBox);
		row++;
		if (!UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			roleListBox.setEnabled(false);
		}
	}

	private void addEnabledLine(Grid grid, int row) {
		Label enabledLabel = new Label(userMessages.enabled() + ":");
		grid.setWidget(row, 0, enabledLabel);
		enabledCheckBox = new CheckBox();
		grid.setWidget(row, 1, enabledCheckBox);
		row++;
		if (!UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			enabledCheckBox.setEnabled(false);
		}
	}

	private Panel createNavPanel() {
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
		return nav;
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
		user.getShop().setShortName(shortNameTextBox.getValue());
		user.getShop().setName(shopNameTextBox.getValue());
		user.getShop().setStreet(streetTextBox.getValue());
		user.getShop().setHousenumber(housenumberTextBox.getValue());
		user.getShop().setPostalcode(postalcodeTextBox.getValue());
		user.getShop().setCity(cityTextBox.getValue());
		user.setEmail(emailTextBox.getValue());
		user.setEnabled(enabledCheckBox.getValue());
		user.setCountry(UserCountry.valueOf(countryListBox.getValue(countryListBox.getSelectedIndex())));
		user.getShop().setCountry(UserCountry.valueOf(countryListBox.getValue(countryListBox.getSelectedIndex())));
		user.setRole(UserRole.valueOf(roleListBox.getValue(roleListBox.getSelectedIndex())));
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
