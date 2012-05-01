package com.xfashion.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	UserDTO createUser(UserDTO user);
	
	UserDTO login(String username, String password);
	
	UserDTO readUserByUsername(String username);
	
	List<UserDTO> readUsers();
	
	void updateUser(UserDTO user);
	
	void updatePassword(UserDTO user, String password);
	
	void deleteUser(UserDTO user);

	void createResetPassword(UserDTO user);
	
	ResetPasswordDTO readResetPassword(Long id);
	
	void deleteResetPassword(ResetPasswordDTO resetPassword);
	
}
