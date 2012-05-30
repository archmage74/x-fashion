package com.xfashion.client.user;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public interface UserServiceAsync {

	void createUser(UserDTO user, AsyncCallback<UserDTO> callback);
	
	void login(String username, String password, AsyncCallback<UserDTO> callback);
	
	void readUserByUsername(String username, AsyncCallback<UserDTO> callback);

	void readUsers(AsyncCallback<List<UserDTO>> callback);

	void updateUser(UserDTO user, AsyncCallback<Void> callback);
	
	void updatePassword(UserDTO user, String password, AsyncCallback<Void> callback);

	void deleteUser(UserDTO user, AsyncCallback<Void> callback);
	
	void createResetPassword(UserDTO user, AsyncCallback<Void> callback);

	void readResetPassword(String keyString, AsyncCallback<ResetPasswordDTO> callback);

	void deleteResetPassword(ResetPasswordDTO resetPassword, AsyncCallback<Void> callback);

	void createNotepad(NotepadDTO notepad, AsyncCallback<NotepadDTO> callback);
	
	void readOwnNotepads(AsyncCallback<Set<NotepadDTO>> callback);
	
	void readDeliveryNoticeById(Long id, AsyncCallback<DeliveryNoticeDTO> callback);

	void updateOwnNotepad(NotepadDTO notepad, AsyncCallback<NotepadDTO> callback);

	void createDeliveryNotice(DeliveryNoticeDTO deliverNotice, AsyncCallback<DeliveryNoticeDTO> callback);

	void readOwnDeliveryNotices(AsyncCallback<Set<DeliveryNoticeDTO>> callback);

	void updateDeliveryNotice(DeliveryNoticeDTO notepad, AsyncCallback<DeliveryNoticeDTO> callback);
	
}
