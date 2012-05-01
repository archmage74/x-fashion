package com.xfashion.client.user;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.UserDTO;

public class LoginEvent extends Event<LoginHandler> {

	public static Type<LoginHandler> TYPE = new Type<LoginHandler>();
	
	private UserDTO user;
	
	public LoginEvent(UserDTO user) {
		setUser(user);
	}
	
	@Override
	public Type<LoginHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(LoginHandler handler) {
		handler.onLogin(this);
	}
	
	public UserDTO getUser() {
		return user;
	}
	
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
