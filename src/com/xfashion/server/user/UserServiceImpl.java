package com.xfashion.server.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.user.UserService;
import com.xfashion.server.PMF;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());
	
	private static final long serialVersionUID = 1L;

	private static User adminUser; 
	static {
		adminUser = new User(new UserDTO("root", "root", "archmage74@gmail.com"));
		adminUser.setPassword("37e73865cdc6a4eead2fb25e19c2ca96");
	}

	@Override
	public UserDTO createUser(UserDTO dto) {
		if (dto.getUsername().equals(adminUser.getUsername())) {
			throw new RuntimeException("username not allowed");
		}
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
			user.encodeAndSetPassword(dto.getPassword());
		}
		user = pm.makePersistent(user);
		return user;
	}
	
	@Override 
	public UserDTO login(String username, String password) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserDTO dto = null;
		try {
			User user = readUserByUsername(pm, username);
			if (user.validatePassword(password)) {
				dto = user.createDTO();
			}
		} finally {
			pm.close();
		}
		return dto;
	}
	
	@Override 
	public UserDTO readUserByUsername(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserDTO dto = null;
		try {
			User user = readUserByUsername(pm, username);
			dto = user.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	private User readUserByUsername(PersistenceManager pm, String username) {
		User user;
		if (adminUser.getUsername().equals(username)) {
			user = adminUser;
		} else {
			Query userQuery = pm.newQuery(User.class);
			userQuery.setFilter("username == usernameParam");
			userQuery.declareParameters("String usernameParam");
			List<User> users = (List<User>) userQuery.execute(username);
			if (users.size() == 1) {
				user = users.get(0);
			} else {
				throw new RuntimeException("Could not read user '" + username + "', query returned " + users.size() + " entities");
			}
		}
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
		User user = pm.getObjectById(User.class, dto.getId());
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
		User user = pm.getObjectById(User.class, dto.getId());
		user.encodeAndSetPassword(password);
		pm.makePersistent(user);
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
		User user = pm.getObjectById(User.class, dto.getId());
		pm.deletePersistent(user);
	}
	
	@Override
	public void createResetPassword(UserDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ResetPasswordDTO rpDto = new ResetPasswordDTO(dto.getUsername());
			ResetPassword rp = createResetPassword(pm, rpDto);
			rpDto = rp.createDTO();
			sendResetPassword(dto, rpDto);
		} finally {
			pm.close();
		}
	}
	
	private ResetPassword createResetPassword(PersistenceManager pm, ResetPasswordDTO dto) {
		ResetPassword rp = new ResetPassword(dto);
		return pm.makePersistent(rp);
	}
	
	@Override
	public ResetPasswordDTO readResetPassword(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ResetPasswordDTO dto = null;
		try {
			ResetPassword rp = readResetPassword(pm, id);
			dto = rp.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private ResetPassword readResetPassword(PersistenceManager pm, Long id) {
		return pm.getObjectById(ResetPassword.class, id);
	}
	
	@Override
	public void deleteResetPassword(ResetPasswordDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteResetPassword(pm, dto);
		} finally {
			pm.close();
		}
	}
	
	private void deleteResetPassword(PersistenceManager pm, ResetPasswordDTO dto) {
		ResetPassword rp = pm.getObjectById(ResetPassword.class, dto.getId());
		pm.deletePersistent(rp);
	}
	
	private void sendResetPassword(UserDTO user, ResetPasswordDTO rp) {
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        HttpServletRequest request = getThreadLocalRequest();
        String all = request.getRequestURL().toString();
        String base = all.substring(0, all.indexOf(request.getServletPath()));
        
        String msgBody = base + "/resetpassword/" + rp.getId();
        log.info("created reset link=" + msgBody);
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("xfashionadm@gmail.com", "XFashion"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), user.getUsername()));
            msg.setSubject("XFashion Account");
            msg.setText(msgBody);
            Transport.send(msg);
        } catch (AddressException e) {
			throw new RuntimeException(e);
        } catch (MessagingException e) {
			throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
