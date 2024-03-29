package com.xfashion.client.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.RemovedArticleDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.UserDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	public static final String SESSION_USER = "currentUser";
	
	public static final String ROOT_USERNAME = "root";
	
	public static final int KEEP_ALIVE_TIMEOUT = 5 * 60 * 1000; // 5 minutes
	
	UserDTO createUser(UserDTO user);
	
	UserDTO login(String username, String password);
	
	void keepAlive(String username);
	
	UserDTO getOwnUser();
	
	UserDTO readUserByUsername(String username);
	
	List<UserDTO> readUsers();
	
	void updateUser(UserDTO user);
	
	void updatePassword(UserDTO user, String password);
	
	void deleteUser(UserDTO user);

	void createResetPassword(UserDTO user);
	
	ResetPasswordDTO readResetPassword(String keyString);
	
	void deleteResetPassword(ResetPasswordDTO resetPassword);

	
	// Notepad
	NotepadDTO createNotepad(NotepadDTO notepad); 
	
	Set<NotepadDTO> readOwnNotepads();
	
	NotepadDTO readNotepad(String keyString);
	
	NotepadDTO updateOwnNotepad(NotepadDTO notepad);

	void deleteNotepad(String keyString);
	
	
	// DeliveryNotice
	DeliveryNoticeDTO createDeliveryNotice(DeliveryNoticeDTO deliverNotice);
	
	Set<DeliveryNoticeDTO> readOwnDeliveryNotices();

	DeliveryNoticeDTO readDeliveryNotice(String keyString);
	
	DeliveryNoticeDTO readDeliveryNoticeById(Long id);
	
	DeliveryNoticeDTO updateDeliveryNotice(DeliveryNoticeDTO deliveryNotice);

	
	// Shop-Stock
	ArticleAmountDTO createStockEntry(ArticleAmountDTO articleAmount) throws IllegalArgumentException;
	
	List<ArticleAmountDTO> readOwnStock() throws IllegalArgumentException;
	
	List<ArticleAmountDTO> readStockOfUser(String userKey) throws IllegalArgumentException;
	
	Collection<ArticleAmountDTO> addStockEntries(Collection<ArticleAmountDTO> dtos) throws IllegalArgumentException;

	Collection<ArticleAmountDTO> addStockEntriesToUser(String userKey, Collection<ArticleAmountDTO> dtos) throws IllegalArgumentException;
		
	void updateStockEntry(ArticleAmountDTO articleAmount) throws IllegalArgumentException;
	
	Collection<ArticleAmountDTO> sellArticlesFromStock(Collection<SoldArticleDTO> articles);

	void deleteStockEntry(ArticleAmountDTO articleAmount) throws IllegalArgumentException;
	
	List<AddedArticleDTO> readWareInput(int from, int to) throws IllegalArgumentException;

	List<AddedArticleDTO> readWareInputOfShop(String shopKey, int from, int to) throws IllegalArgumentException;

	List<AddedArticleDTO> readOwnWareInput(int from, int to) throws IllegalArgumentException;
	
	ArticleAmountDTO removeFromStock(ArticleAmountDTO dto, int amount) throws IllegalArgumentException;
	
	List<RemovedArticleDTO> readOwnRemovedArticles(int from, int to) throws IllegalArgumentException;
	
	public List<RemovedArticleDTO> readRemovedArticlesOfShop(String shopKey, int from, int to) throws IllegalArgumentException;

	
	// Shop-PriceChanges
	void createPriceChangeForShop(String shopKey, PriceChangeDTO dto) throws IllegalArgumentException;
	
	Collection<PriceChangeDTO> readOwnPriceChanges() throws IllegalArgumentException;

	Collection<PriceChangeDTO> updatePriceChanges(Collection<PriceChangeDTO> dtos) throws IllegalArgumentException;

	void deletePriceChanges(Collection<PriceChangeDTO> dtos) throws IllegalArgumentException;

}
