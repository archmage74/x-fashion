package com.xfashion.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;

public interface UserServiceAsync {

	void createUser(UserDTO user, AsyncCallback<UserDTO> callback);
	
	void login(String username, String password, AsyncCallback<UserDTO> callback);
	
	void readUserByUsername(String username, AsyncCallback<UserDTO> callback);

	void readUsers(AsyncCallback<List<UserDTO>> callback);

	void updateUser(UserDTO user, AsyncCallback<Void> callback);
	
	void updatePassword(UserDTO user, String password, AsyncCallback<Void> callback);

	void deleteUser(UserDTO user, AsyncCallback<Void> callback);
	
	void createResetPassword(UserDTO user, AsyncCallback<Void> callback);

	void readResetPassword(Long id, AsyncCallback<ResetPasswordDTO> callback);

	void deleteResetPassword(ResetPasswordDTO resetPassword, AsyncCallback<Void> callback);

}
