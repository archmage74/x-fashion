package com.xfashion.server.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.user.UserService;
import com.xfashion.client.util.IdCounterService;
import com.xfashion.server.AddedArticle;
import com.xfashion.server.PMF;
import com.xfashion.server.PriceChange;
import com.xfashion.server.RemovedArticle;
import com.xfashion.server.SoldArticle;
import com.xfashion.server.StockArticle;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.server.util.IdCounterServiceImpl;
import com.xfashion.server.util.IdCounterType;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.RemovedArticleDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserCountry;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;
 
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	private static final long serialVersionUID = 1L;

	private static User adminUser;
	static {
		adminUser = new User(new UserDTO(ROOT_USERNAME, ROOT_USERNAME, "archmage74@gmail.com", UserCountry.AT, UserRole.DEVELOPER));
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
			if (user != null && user.validatePassword(password)) {
				dto = user.createDTO();
			}
			this.getThreadLocalRequest().getSession().setAttribute(SESSION_USER, dto);
		} catch (UserNotFoundException e) {
			dto = null;
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
		} catch (UserNotFoundException e) {
			dto = null;
		} finally {
			pm.close();
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	private User readUserByUsername(PersistenceManager pm, String username) throws UserNotFoundException {
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
				throw new UserNotFoundException();
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

		String msgBody = createResetPasswordMailBody(user, rp, base);
		log.info("created reset link=" + msgBody);

		try {
			Message msg = createResetPasswordMail(user, session, msgBody);
			Transport.send(msg);
		} catch (AddressException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private Message createResetPasswordMail(UserDTO user, Session session, String msgBody) throws MessagingException, UnsupportedEncodingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("xfashionadm@gmail.com", "XFashion"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), user.getUsername()));
		msg.setSubject("XFashion Datenbank ACCOUNT AKTIVIERUNG");
		msg.setText(msgBody);
		return msg;
	}

	private String createResetPasswordMailBody(UserDTO user, ResetPasswordDTO rp, String base) {
		StringBuffer sb = new StringBuffer();
		sb.append("Willkommen bei XFashion\n");
		sb.append("Dein Benutzername lautet: ");
		sb.append(user.getUsername());
		sb.append("\n");
		sb.append("\n");
		sb.append("Bitte clicke auf folgenden Link um dein persönliches Passwort festzulegen:\n");
		sb.append(base);
		sb.append("/resetpassword/");
		sb.append(rp.getKey());
		sb.append("\n");
		sb.append("\n");
		sb.append("Mit freundlichen Grüßen\n");
		sb.append("Das XFashion Team\n");
		String msgBody = sb.toString();
		return msgBody;
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
		Shop shop = readOwnStock(pm);
		Notepad notepad = new Notepad(dto);
		shop.getNotepads().add(notepad);
		return notepad;
	}

	@Override
	public Set<NotepadDTO> readOwnNotepads() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<NotepadDTO> notepads = null;
		try {
			Shop shop = readOwnStock(pm);
			notepads = shop.createNotepadDTOs();
		} finally {
			pm.close();
		}
		return notepads;
	}

	@Override
	public NotepadDTO readNotepad(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		NotepadDTO dto = null;
		try {
			Notepad notepad = readNotepad(pm, keyString);
			dto = notepad.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	public Notepad readNotepad(PersistenceManager pm, String keyString) {
		Notepad notepad = pm.getObjectById(Notepad.class, KeyFactory.stringToKey(keyString));
		return notepad;
	}

	public Shop readOwnStock(PersistenceManager pm) {
		UserDTO userDTO = (UserDTO) this.getThreadLocalRequest().getSession().getAttribute(SESSION_USER);
		if (userDTO == null) {
			throw new RuntimeException("no user logged in");
		}
		User user = readUser(pm, userDTO.getKey());
		return user.getShop();
	}

	public Shop readShop(PersistenceManager pm, String shopKey) {
		Shop shop = pm.getObjectById(Shop.class, KeyFactory.stringToKey(shopKey));
		return shop;
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
	
	@Override
	public void deleteNotepad(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteNotepad(pm, keyString);
		} finally {
			pm.close();
		}
	}
	
	private void deleteNotepad(PersistenceManager pm, String keyString) {
		Notepad notepad = readNotepad(pm, keyString);
		pm.deletePersistent(notepad);
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
			setShopsToDeliveryNoticeDTOs(pm, dto);
		} finally {
			pm.close();
		}
		return dto;
	}

	public void setShopsToDeliveryNoticeDTOs(PersistenceManager pm, DeliveryNoticeDTO... dtos) {
		HashMap<String, ShopDTO> shops = new HashMap<String, ShopDTO>();
		for (DeliveryNoticeDTO dto : dtos) {
			if (dto.getSourceShopKey() != null) {
				dto.setSourceShop(readCashedShopDTO(pm, shops, dto.getSourceShopKey()));
			}
			dto.setTargetShop(readCashedShopDTO(pm, shops, dto.getTargetShopKey()));
		}
	}
	
	private ShopDTO readCashedShopDTO(PersistenceManager pm, Map<String, ShopDTO> cache, String shopKey) {
		ShopDTO dto = cache.get(shopKey);
		if (dto == null) {
			Shop source = pm.getObjectById(Shop.class, KeyFactory.stringToKey(shopKey));
			dto = source.createDTO();
			cache.put(shopKey, dto);
		}
		return dto;
	}

	public DeliveryNotice createDeliveryNotice(PersistenceManager pm, DeliveryNoticeDTO dto) {
		Shop shop = readOwnStock(pm);
		DeliveryNotice deliveryNotice = new DeliveryNotice(dto);
		shop.getDeliveryNotices().add(deliveryNotice);
		return deliveryNotice;
	}

	@Override
	public Set<DeliveryNoticeDTO> readOwnDeliveryNotices() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Set<DeliveryNoticeDTO> dtos = null;
		try {
			Shop shop = readOwnStock(pm);
			dtos = shop.createDeliveryNoticeDTOs();
			setShopsToDeliveryNoticeDTOs(pm, dtos.toArray(new DeliveryNoticeDTO[] { }));
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public DeliveryNoticeDTO readDeliveryNotice(String keyString) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DeliveryNoticeDTO dto = null;
		try {
			DeliveryNotice deliveryNotice = readDeliveryNotice(pm, keyString);
			dto = deliveryNotice.createDTO();
			setShopsToDeliveryNoticeDTOs(pm, dto);
		} finally {
			pm.close();
		}
		return dto;
	}
	
	public DeliveryNotice readDeliveryNotice(PersistenceManager pm, String keyString) {
		DeliveryNotice deliveryNotice = pm.getObjectById(DeliveryNotice.class, KeyFactory.stringToKey(keyString));
		return deliveryNotice;
	}

	@Override
	public DeliveryNoticeDTO readDeliveryNoticeById(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DeliveryNoticeDTO dto = null;
		try {
			DeliveryNotice deliveryNotice = readDeliveryNoticeById(pm, id);
			if (deliveryNotice != null) {
				dto = deliveryNotice.createDTO();
				setShopsToDeliveryNoticeDTOs(pm, dto);
			}
		} finally {
			pm.close();
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public DeliveryNotice readDeliveryNoticeById(PersistenceManager pm, Long id) {
		DeliveryNotice deliveryNotice = null;
		Query userQuery = pm.newQuery(DeliveryNotice.class);
		userQuery.setFilter("id == idParam");
		userQuery.declareParameters("Long idParam");
		List<DeliveryNotice> deliveryNotices = (List<DeliveryNotice>) userQuery.execute(id);
		if (deliveryNotices.size() == 1) {
			deliveryNotice = deliveryNotices.get(0);
		}
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

	// *****************
	// Shop-Stock
	// *****************
	@Override
	public ArticleAmountDTO createStockEntry(ArticleAmountDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			StockArticle articleAmount = createStockEntry(pm, dto);
			dto = articleAmount.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	public StockArticle createStockEntry(PersistenceManager pm, ArticleAmountDTO dto) {
		Transaction tx = pm.currentTransaction();
		StockArticle articleAmount = null;
		try {
			tx.begin();
			Shop shop = readOwnStock(pm);
			for (StockArticle aa : shop.getArticles()) {
				if (aa.getArticleTypeKey().equals(dto.getArticleTypeKey())) {
					throw new RuntimeException("Article is already in stock");
				}
			}
			articleAmount = new StockArticle(dto);
			shop.getArticles().add(articleAmount);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return articleAmount;
	}

	@Override
	public List<ArticleAmountDTO> readOwnStock() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleAmountDTO> dtos = null;
		try {
			Shop shop = readOwnStock(pm);
			dtos = shop.createStock();
		} finally {
			pm.close();
		}
		return dtos;
	}

	public StockArticle readStockEntry(PersistenceManager pm, String keyString) {
		StockArticle articleAmount = pm.getObjectById(StockArticle.class, KeyFactory.stringToKey(keyString));
		return articleAmount;
	}

	@Override 
	public List<ArticleAmountDTO> readStockOfUser(String userKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleAmountDTO> dtos = null;
		try {
			User user = readUser(pm, userKey);
			Shop shop = user.getShop();
			dtos = shop.createStock();
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public void updateStockEntry(ArticleAmountDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			updateStockEntry(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void updateStockEntry(PersistenceManager pm, ArticleAmountDTO dto) {
		StockArticle articleAmount = readStockEntry(pm, dto.getKey());
		articleAmount.updateFromDTO(dto);
	}

	@Override
	public Collection<ArticleAmountDTO> addStockEntries(Collection<ArticleAmountDTO> dtos) {
		Map<String, ArticleAmountDTO> dtoMap = new HashMap<String, ArticleAmountDTO>();
		for (ArticleAmountDTO dto : dtos) {
			dtoMap.put(dto.getArticleTypeKey(), dto);
		}
		Shop shop = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			shop = addStockEntries(pm, dtoMap);
		} catch (Exception e) {
			log.warning("error while adding articles to stock: " + e.getMessage());
		} finally {
			pm.close();
		}
		if (shop != null) {
			dtos = shop.createStock();
		}
		
		return dtos;
	}

	private Shop addStockEntries(PersistenceManager pm, Map<String, ArticleAmountDTO> dtos) {
		Transaction tx = pm.currentTransaction();
		Shop shop = null;
		try {
			tx.begin();
			shop = readOwnStock(pm);
			addStockEntriesToShop(pm, shop, dtos);
			pm.makePersistent(shop);
			tx.commit();
		} catch (Exception e) {
			log.warning("error while adding articles to stock: " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return shop;
	}

	@Override
	public Collection<ArticleAmountDTO> addStockEntriesToUser(String userKey, Collection<ArticleAmountDTO> dtos) {
		Map<String, ArticleAmountDTO> dtoMap = new HashMap<String, ArticleAmountDTO>();
		for (ArticleAmountDTO dto : dtos) {
			dtoMap.put(dto.getArticleTypeKey(), dto);
		}
		Shop shop = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			shop = addStockEntriesToUser(pm, userKey, dtoMap);
		} catch (Exception e) {
			log.warning("error while adding articles to stock: " + e.getMessage());
		} finally {
			pm.close();
		}
		if (shop != null) {
			dtos = shop.createStock();
		}
		
		return dtos;
	}

	private Shop addStockEntriesToUser(PersistenceManager pm, String userKey, Map<String, ArticleAmountDTO> dtos) {
		Transaction tx = pm.currentTransaction();
		Shop shop = null;
		try {
			tx.begin();
			User user = readUser(pm, userKey);
			shop = user.getShop();
			addStockEntriesToShop(pm, shop, dtos);
			pm.makePersistent(shop);
			tx.commit();
		} catch (Exception e) {
			log.warning("error while adding articles to stock: " + e.getMessage());
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return shop;
	}

	private void addStockEntriesToShop(PersistenceManager pm, Shop shop, Map<String, ArticleAmountDTO> dtos) {
		Collection<ArticleAmountDTO> toAdd = new ArrayList<ArticleAmountDTO>(dtos.values());

		for (StockArticle stockArticle : shop.getArticles()) {
			ArticleAmountDTO dto = dtos.get(KeyFactory.keyToString(stockArticle.getArticleTypeKey()));
			if (dto != null) {
				stockArticle.setAmount(stockArticle.getAmount() + dto.getAmount());
				toAdd.remove(dto);
			}
		}
		pm.flush();

		for (ArticleAmountDTO dto : toAdd) {
			StockArticle stockArticle = new StockArticle(dto);
			shop.addArticle(stockArticle);
			if (stockArticle.getKey() == null) {
				log.warning("added stockArticle, key is still null");
			}
		}
		pm.flush();
		
		for (ArticleAmountDTO dto : dtos.values()) {
			shop.addAddedArticle(new AddedArticle(dto));
		}
		pm.flush();
	}
	
	@Override
	public Collection<ArticleAmountDTO> sellArticlesFromStock(Collection<SoldArticleDTO> dtos) {
		Collection<ArticleAmountDTO> stock = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Shop shop = removeStockEntries(pm, dtos);
			stock = shop.createStock();
		} finally {
			pm.close();
		}
		return stock;
	}

	private Shop removeStockEntries(PersistenceManager pm, Collection<SoldArticleDTO> dtos) {
		Map<String, ArticleAmountDTO> dtoMap = new HashMap<String, ArticleAmountDTO>();
		for (SoldArticleDTO dto : dtos) {
			ArticleAmountDTO storedDTO = dtoMap.get(dto.getArticleTypeKey());
			if (storedDTO == null) {
				ArticleAmountDTO articleAmountDTO = new ArticleAmountDTO(dto.getArticleTypeKey(), dto.getAmount());
				dtoMap.put(dto.getArticleTypeKey(), articleAmountDTO);
			} else {
				storedDTO.increaseAmount(dto.getAmount());
			}
		}

		Collection<ArticleAmountDTO> notInStock = dtoMap.values();
		Shop shop = null;
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			shop = readOwnStock(pm);
			Collection<StockArticle> toRemove = new HashSet<StockArticle>();

			for (StockArticle articleAmount : shop.getArticles()) {
				ArticleAmountDTO dto = dtoMap.get(KeyFactory.keyToString(articleAmount.getArticleTypeKey()));
				if (dto != null) {
					Integer newAmount = articleAmount.getAmount() - dto.getAmount();
					if (newAmount < 0) {
						throw new RuntimeException("Could not sell articles because there are not enough articles in stock");
					} else if (newAmount == 0) {
						toRemove.add(articleAmount);
					} else {
						articleAmount.setAmount(articleAmount.getAmount() - dto.getAmount());
					}
					notInStock.remove(dto);
				}
			}
			if (notInStock.size() > 0) {
				throw new RuntimeException("Could not sell articles because they are not in stock");
			}
			shop.getArticles().removeAll(toRemove);

			for (SoldArticleDTO soldArticleDTO : dtos) {
				createSoldArticle(pm, shop, soldArticleDTO);
			}

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return shop;
	}

	private SoldArticle createSoldArticle(PersistenceManager pm, Shop shop, SoldArticleDTO dto) {
		SoldArticle soldArticle = new SoldArticle(dto);
		shop.getSoldArticles().add(soldArticle);
		return soldArticle;
	}

	@Override
	public void deleteStockEntry(ArticleAmountDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deleteStockEntry(pm, dto);
		} finally {
			pm.close();
		}
	}

	private void deleteStockEntry(PersistenceManager pm, ArticleAmountDTO dto) {
		StockArticle articleAmount = readStockEntry(pm, dto.getKey());
		pm.deletePersistent(articleAmount);
	}

	@Override
	public SoldArticleDTO readSoldArticle(String keyString) throws IllegalArgumentException {
		SoldArticleDTO dto = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			SoldArticle soldArticle = readSoldArticle(pm, keyString);
			dto = soldArticle.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private SoldArticle readSoldArticle(PersistenceManager pm, String keyString) {
		SoldArticle soldArticle = pm.getObjectById(SoldArticle.class, KeyFactory.stringToKey(keyString));
		return soldArticle;
	}

	@Override
	public List<SoldArticleDTO> readSoldArticles(int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SoldArticleDTO> dtos = new ArrayList<SoldArticleDTO>();
		try {
			Collection<SoldArticle> soldArticles = readSoldArticles(pm, from, to);
			for (SoldArticle soldArticle : soldArticles) {
				dtos.add(soldArticle.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private List<SoldArticle> readSoldArticles(PersistenceManager pm, int from, int to) {
		Query soldArticleQuery = pm.newQuery(SoldArticle.class);
		soldArticleQuery.setRange(from, to);
		soldArticleQuery.setOrdering("sellDate desc");
		List<SoldArticle> soldArticles = (List<SoldArticle>) soldArticleQuery.execute();
		return soldArticles;
	}

	@Override
	public List<SoldArticleDTO> readSoldArticlesOfShop(String shopKey, int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<SoldArticleDTO> dtos = new ArrayList<SoldArticleDTO>();
		try {
			Shop shop = readShop(pm, shopKey);
			int cnt = (int) from;
			List<SoldArticle> soldArticles = shop.getSoldArticles(); 
			while (cnt < to && cnt < soldArticles.size()) {
				dtos.add(soldArticles.get(cnt).createDTO());
				cnt++;
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public List<SoldArticleDTO> readOwnSoldArticles(int from, int to) throws IllegalArgumentException {
		UserDTO userDTO = (UserDTO) this.getThreadLocalRequest().getSession().getAttribute(SESSION_USER); 
		return readSoldArticlesOfShop(userDTO.getShop().getKeyString(), from, to);
	}

	@Override
	public List<AddedArticleDTO> readWareInput(int from, int to) throws IllegalArgumentException {
		List<AddedArticleDTO> dtos = new ArrayList<AddedArticleDTO>();
		return dtos;
	}
	
	@Override
	public List<AddedArticleDTO> readWareInputOfShop(String shopKey, int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<AddedArticleDTO> dtos = new ArrayList<AddedArticleDTO>();
		try {
			Shop shop = readShop(pm, shopKey);
			int cnt = (int) from;
			List<AddedArticle> addedArticles = shop.getAddedArticles(); 
			while (cnt < to && cnt < addedArticles.size()) {
				dtos.add(addedArticles.get(cnt).createDTO());
				cnt++;
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<AddedArticleDTO> readOwnWareInput(int from, int to) throws IllegalArgumentException {
		UserDTO userDTO = (UserDTO) this.getThreadLocalRequest().getSession().getAttribute(SESSION_USER); 
		return readWareInputOfShop(userDTO.getShop().getKeyString(), from, to);
	}
	
	@Override
	public ArticleAmountDTO removeFromStock(ArticleAmountDTO dto, int amount) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			StockArticle articleAmount = removeFromStock(pm, dto, amount);
			if (articleAmount != null) {
				dto = articleAmount.createDTO();
			} else {
				dto = null;
			}
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private StockArticle removeFromStock(PersistenceManager pm, ArticleAmountDTO dto, int amount) {
		Transaction tx = pm.currentTransaction();
		StockArticle foundArticle = null;
		try {
			tx.begin();
			Shop shop = readOwnStock(pm);
			Key key = KeyFactory.stringToKey(dto.getKey());
			for (StockArticle articleAmount : shop.getArticles()) {
				if (key.equals(articleAmount.getKey())) {
					if (articleAmount.getAmount() < amount) {
						throw new RuntimeException("Cannot remove " + amount + " because only " + articleAmount.getAmount() + " are present.");
					}
					articleAmount.setAmount(articleAmount.getAmount() - amount);

					RemovedArticle removedArticle = new RemovedArticle();
					removedArticle.setArticleTypeKey(articleAmount.getArticleTypeKey());
					removedArticle.setAmount(amount);
					shop.addRemovedArticle(removedArticle);

					if (articleAmount.getAmount() <= 0) {
						shop.getArticles().remove(articleAmount);
					} else {
						foundArticle = articleAmount;
					}
					break;
				}
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return foundArticle;
	}

	@Override
	public List<RemovedArticleDTO> readOwnRemovedArticles(int from, int to) throws IllegalArgumentException {
		UserDTO userDTO = (UserDTO) this.getThreadLocalRequest().getSession().getAttribute(SESSION_USER); 
		return readRemovedArticlesOfShop(userDTO.getShop().getKeyString(), from, to);
	}

	@Override
	public List<RemovedArticleDTO> readRemovedArticlesOfShop(String shopKey, int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<RemovedArticleDTO> dtos = new ArrayList<RemovedArticleDTO>();
		try {
			Shop shop = readShop(pm, shopKey);
			int cnt = (int) from;
			List<RemovedArticle> removedArticles = shop.getRemovedArticles(); 
			while (cnt < to && cnt < removedArticles.size()) {
				dtos.add(removedArticles.get(cnt).createDTO());
				cnt++;
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	// Shop-PriceChanges
	@Override
	public void createPriceChangeForShop(String shopKey, PriceChangeDTO dto) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Shop shop = readShop(pm, shopKey);
			PriceChange priceChange = new PriceChange(dto);
			priceChange.setAccepted(false);
			priceChange.setChangeDate(dto.getChangeDate());
			shop.getPriceChanges().add(priceChange);
			pm.makePersistent(shop);
			// dto = priceChange.createDTO();
		} finally {
			pm.close();
		}
	}

	@Override
	public Collection<PriceChangeDTO> readOwnPriceChanges() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Collection<PriceChangeDTO> dtos = new ArrayList<PriceChangeDTO>();
		try {
			Shop shop = readOwnStock(pm);
			for (PriceChange priceChange : shop.getPriceChanges()) {
				dtos.add(priceChange.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@Override
	public Collection<PriceChangeDTO> updatePriceChanges(Collection<PriceChangeDTO> dtos) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Collection<PriceChangeDTO> storedDtos = new ArrayList<PriceChangeDTO>();
		try {
			for (PriceChangeDTO dto : dtos) {
				PriceChange priceChange = readPriceChange(pm, dto.getKeyString());
				priceChange.updateFromDTO(dto);
				storedDtos.add(priceChange.createDTO());
			}
		} finally {
			pm.close();
		}
		return storedDtos;
	}

	private PriceChange readPriceChange(PersistenceManager pm, String keyString) {
		PriceChange priceChange = pm.getObjectById(PriceChange.class, keyString);
		return priceChange;
	}

	@Override
	public void deletePriceChanges(Collection<PriceChangeDTO> dtos) throws IllegalArgumentException {
		Set<String> keys = new HashSet<String>();
		for (PriceChangeDTO dto : dtos) {
			keys.add(dto.getKeyString());
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			deletePriceChanges(pm, keys);
		} finally {
			pm.close();
		}
	}
	
	private void deletePriceChanges(PersistenceManager pm, Set<String> deleteKeys) {
		ArrayList<PriceChange> toRemove = new ArrayList<PriceChange>();
		Shop shop = readOwnStock(pm);
		for (PriceChange priceChange : shop.getPriceChanges()) {
			if (deleteKeys.contains(priceChange.getKeyString())) {
				toRemove.add(priceChange);
			}
		}
		shop.getPriceChanges().removeAll(toRemove);
	}

}
