package com.xfashion.server.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.user.UserService;
import com.xfashion.client.util.IdCounterService;
import com.xfashion.server.PMF;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.server.util.IdCounterServiceImpl;
import com.xfashion.server.util.IdCounterType;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserCountry;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.notepad.NotepadDTO;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	private static final long serialVersionUID = 1L;
	
	private static User adminUser;
	static {
		adminUser = new User(new UserDTO("root", "root", "archmage74@gmail.com", UserCountry.AT));
		adminUser.setPassword("1c782d2abe3bacd092fe9cdaaf1bba52");
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
			this.getThreadLocalRequest().getSession().setAttribute(SESSION_USER, dto);
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
			if (user == null) {
				dto = null;
			} else {
				dto = user.createDTO();
			}
		} finally {
			pm.close();
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	private User readUserByUsername(PersistenceManager pm, String username) {
		User user;
		Query userQuery = pm.newQuery(User.class);
		userQuery.setFilter("username == usernameParam");
		userQuery.declareParameters("String usernameParam");
		List<User> users = (List<User>) userQuery.execute(username);
		if (users.size() == 1) {
			user = users.get(0);
		} else {
			if (adminUser.getUsername().equals(username)) {
				user = pm.makePersistent(adminUser);
			} else {
				throw new RuntimeException("Could not read user '" + username + "', query returned " + users.size() + " entities");
			}
		}
		return user;
	}

	private User readUser(PersistenceManager pm, String key) {
		User user = pm.getObjectById(User.class, KeyFactory.stringToKey(key));
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
		User user = readUser(pm, dto.getKey());
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
		User user = readUser(pm, dto.getKey());
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
		User user = readUser(pm, dto.getKey());
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
	public ResetPasswordDTO readResetPassword(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ResetPasswordDTO dto = null;
		try {
			ResetPassword rp = readResetPassword(pm, keyString);
			dto = rp.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	private ResetPassword readResetPassword(PersistenceManager pm, String keyString) {
		return pm.getObjectById(ResetPassword.class, KeyFactory.stringToKey(keyString));
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
		ResetPassword rp = readResetPassword(pm, dto.getKey());
		pm.deletePersistent(rp);
	}

	private void sendResetPassword(UserDTO user, ResetPasswordDTO rp) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		HttpServletRequest request = getThreadLocalRequest();
		String all = request.getRequestURL().toString();
		String base = all.substring(0, all.indexOf(request.getServletPath()));

		String msgBody = base + "/resetpassword/" + rp.getKey();
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

	// *******************
	// Notepads
	// *******************
	@Override
	public NotepadDTO createNotepad(NotepadDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Notepad notepad = createNotepad(pm, dto);
			dto = notepad.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	public Notepad createNotepad(PersistenceManager pm, NotepadDTO dto) {
		Shop shop = readOwnShop(pm);
		Notepad notepad = new Notepad(dto);
		shop.getNotepads().add(notepad);
		return notepad;
	}

	@Override
	public Set<NotepadDTO> readOwnNotepads() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<NotepadDTO> notepads = null;
		try {
			Shop shop = readOwnShop(pm);
			notepads = shop.createNotepadDTOs();
		} finally {
			pm.close();
		}
		return notepads;
	}

	public Notepad readNotepad(PersistenceManager pm, String keyString) {
		Notepad notepad = pm.getObjectById(Notepad.class, KeyFactory.stringToKey(keyString));
		return notepad;
	}

	public Shop readOwnShop(PersistenceManager pm) {
		UserDTO userDTO = (UserDTO) this.getThreadLocalRequest().getSession().getAttribute(SESSION_USER);
		if (userDTO == null) {
			throw new RuntimeException("no user logged in");
		}
		User user = readUser(pm, userDTO.getKey());
		return user.getShop();
	}

	@Override
	public NotepadDTO updateOwnNotepad(NotepadDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateNotepad(pm, dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	private void updateNotepad(PersistenceManager pm, NotepadDTO dto) {
		Notepad notepad = readNotepad(pm, dto.getKey());
		notepad.updateFromDTO(dto);
	}

	// *****************
	// DeliveryNotice
	// *****************
	@Override
	public DeliveryNoticeDTO createDeliveryNotice(DeliveryNoticeDTO dto) {
		IdCounterService idCounterService = new IdCounterServiceImpl();
		Long id = idCounterService.getNewId(IdCounterType.DELIVERY_NOTICE);
		dto.setId(id);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			DeliveryNotice deliveryNotice = createDeliveryNotice(pm, dto);
			dto = deliveryNotice.createDTO();
			setTargetShopToDeliveryNoticeDTO(pm, dto);
		} finally {
			pm.close();
		}
		return dto;
	}
	
	public void setTargetShopToDeliveryNoticeDTO(PersistenceManager pm, DeliveryNoticeDTO dto) {
		Shop shop = pm.getObjectById(Shop.class, KeyFactory.stringToKey(dto.getTargetShopKey()));
		dto.setTargetShop(shop.createDTO());
	}

	public DeliveryNotice createDeliveryNotice(PersistenceManager pm, DeliveryNoticeDTO dto) {
		Shop shop = readOwnShop(pm);
		DeliveryNotice deliveryNotice = new DeliveryNotice(dto);
		shop.getDeliveryNotices().add(deliveryNotice);
		return deliveryNotice;
	}

	@Override
	public Set<DeliveryNoticeDTO> readOwnDeliveryNotices() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<DeliveryNoticeDTO> dtos = null;
		try {
			Shop shop = readOwnShop(pm);
			dtos = shop.createDeliveryNoticeDTOs();
			for (DeliveryNoticeDTO dto : dtos) {
				setTargetShopToDeliveryNoticeDTO(pm, dto);
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	public DeliveryNotice readDeliveryNotice(PersistenceManager pm, String keyString) {
		DeliveryNotice deliveryNotice = pm.getObjectById(DeliveryNotice.class, KeyFactory.stringToKey(keyString));
		return deliveryNotice;
	}

	@Override
	public DeliveryNoticeDTO updateDeliveryNotice(DeliveryNoticeDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateDeliveryNotice(pm, dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	private void updateDeliveryNotice(PersistenceManager pm, DeliveryNoticeDTO dto) {
		DeliveryNotice deliveryNotice = readDeliveryNotice(pm, dto.getKey());
		deliveryNotice.updateFromDTO(dto);
	}

}
