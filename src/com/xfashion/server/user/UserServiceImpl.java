package com.xfashion.server.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.user.UserService;
import com.xfashion.server.PMF;
import com.xfashion.shared.UserDTO;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final long serialVersionUID = 1L;

	@Override
	public UserDTO createUser(UserDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			User user = createUser(pm, dto);
			dto = user.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	public User createUser(PersistenceManager pm, UserDTO dto) {
		User user = new User(dto);
		if (dto.getPassword() != null) {
			setPasswordOfUser(user, dto.getPassword());
		}
		user = pm.makePersistent(user);
		return user;
	}

	@Override
	public List<UserDTO> readUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		try {
			List<User> users = readUsers(pm);
			for (User u : users) {
				dtos.add(u.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@SuppressWarnings("unchecked")
	private List<User> readUsers(PersistenceManager pm) {
		Query query = pm.newQuery(User.class);
		List<User> users = (List<User>) query.execute();
		return users;
	}

	@Override
	public void updateUser(UserDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateUser(pm, dto);
		} finally {
			pm.close();
		}
	}
	
	private void updateUser(PersistenceManager pm, UserDTO dto) {
		User user = pm.getObjectById(User.class, dto.getUsername());
		user.updateFromDTO(dto);
		pm.makePersistent(user);
	}
	
	@Override 
	public void updatePassword(UserDTO dto, String password) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updatePassword(pm, dto, password);
		} finally {
			pm.close();
		}
	}
	
	private void updatePassword(PersistenceManager pm, UserDTO dto, String password) {
		User user = pm.getObjectById(User.class, dto.getUsername());
		setPasswordOfUser(user, password);
		pm.makePersistent(user);
	}
	
	@Override
	public void generateAndSendPassword(UserDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String password = "1234";
			updatePassword(pm, dto, password);
			sendPassword(dto, password);
		} finally {
			pm.close();
		}
	}
	
	@Override
	public void deleteUser(UserDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteUser(pm, dto);
		} finally {
			pm.close();
		}
	}
	
	private void deleteUser(PersistenceManager pm, UserDTO dto) {
		User user = pm.getObjectById(User.class, dto.getUsername());
		pm.deletePersistent(user);
	}
	
	private void setPasswordOfUser(User user, String password) {
		byte[] bytes = password.getBytes();
		String hash = new String(bytes);
		user.setPassword(hash);
	}
	
	private void sendPassword(UserDTO dto, String password) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = password;

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("xfashionadm@gmail.com", "XFashion"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(dto.getEmail(), dto.getUsername()));
            msg.setSubject("XFashion Account");
            msg.setText(msgBody);
            Transport.send(msg);
        } catch (AddressException e) {
        } catch (MessagingException e) {
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
