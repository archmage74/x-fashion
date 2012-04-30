package com.xfashion.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.UserDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	UserDTO createUser(UserDTO user);
	
	List<UserDTO> readUsers();
	
	void updateUser(UserDTO user);
	
	void updatePassword(UserDTO user, String password);
	
	void generateAndSendPassword(UserDTO user);
	
	void deleteUser(UserDTO user);
}
