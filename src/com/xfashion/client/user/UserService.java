package com.xfashion.client.user;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.notepad.NotepadDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	public static final String SESSION_USER = "currentUser";
	
	UserDTO createUser(UserDTO user);
	
	UserDTO login(String username, String password);
	
	UserDTO readUserByUsername(String username);
	
	List<UserDTO> readUsers();
	
	void updateUser(UserDTO user);
	
	void updatePassword(UserDTO user, String password);
	
	void deleteUser(UserDTO user);

	void createResetPassword(UserDTO user);
	
	ResetPasswordDTO readResetPassword(String keyString);
	
	void deleteResetPassword(ResetPasswordDTO resetPassword);
	
	NotepadDTO createNotepad(NotepadDTO notepad); 
	
	Set<NotepadDTO> readOwnNotepads();
	
	NotepadDTO updateOwnNotepad(NotepadDTO notepad);

	DeliveryNoticeDTO createDeliveryNotice(DeliveryNoticeDTO deliverNotice);
	
	Set<DeliveryNoticeDTO> readOwnDeliveryNotices();
	
	DeliveryNoticeDTO updateDeliveryNotice(DeliveryNoticeDTO deliveryNotice);
}
