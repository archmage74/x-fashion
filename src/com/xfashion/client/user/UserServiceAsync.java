package com.xfashion.client.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.RemovedArticleDTO;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;
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

	void readResetPassword(String keyString, AsyncCallback<ResetPasswordDTO> callback);

	void deleteResetPassword(ResetPasswordDTO resetPassword, AsyncCallback<Void> callback);

	void createNotepad(NotepadDTO notepad, AsyncCallback<NotepadDTO> callback);
	
	void readOwnNotepads(AsyncCallback<Set<NotepadDTO>> callback);
	
	void readDeliveryNoticeById(Long id, AsyncCallback<DeliveryNoticeDTO> callback);

	void updateOwnNotepad(NotepadDTO notepad, AsyncCallback<NotepadDTO> callback);

	void createDeliveryNotice(DeliveryNoticeDTO deliverNotice, AsyncCallback<DeliveryNoticeDTO> callback);

	void readOwnDeliveryNotices(AsyncCallback<Set<DeliveryNoticeDTO>> callback);

	void updateDeliveryNotice(DeliveryNoticeDTO notepad, AsyncCallback<DeliveryNoticeDTO> callback);

	
	// Shop-Stock
	void createStockEntry(ArticleAmountDTO articleAmount, AsyncCallback<ArticleAmountDTO> callback);

	void readOwnStock(AsyncCallback<List<ArticleAmountDTO>> callback);

	void readStockOfUser(String userKey, AsyncCallback<List<ArticleAmountDTO>> callback);
	
	void addStockEntries(Collection<ArticleAmountDTO> dtos, AsyncCallback<Collection<ArticleAmountDTO>> callback);
	
	void addStockEntriesToUser(String userKey, Collection<ArticleAmountDTO> dtos, AsyncCallback<Collection<ArticleAmountDTO>> callback);

	void updateStockEntry(ArticleAmountDTO articleAmount, AsyncCallback<Void> callback);

	void sellArticlesFromStock(Collection<SoldArticleDTO> articles, AsyncCallback<Collection<ArticleAmountDTO>> callback);

	void deleteStockEntry(ArticleAmountDTO articleAmount, AsyncCallback<Void> callback);

	void readSoldArticles(int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);
	
	void readSoldArticlesOfShop(String shopKey, int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);

	void readOwnSoldArticles(int from, int to, AsyncCallback<List<SoldArticleDTO>> callback);
	
	void readWareInput(int from, int to, AsyncCallback<List<AddedArticleDTO>> callback);
	
	void readWareInputOfShop(String userKey, int from, int to, AsyncCallback<List<AddedArticleDTO>> callback);
	
	void readOwnWareInput(int from, int to, AsyncCallback<List<AddedArticleDTO>> callback);

	void removeOneFromStock(ArticleAmountDTO dto, AsyncCallback<ArticleAmountDTO> callback);

	void readOwnRemovedArticles(int from, int to, AsyncCallback<List<RemovedArticleDTO>> callback);

	void readRemovedArticlesOfShop(String shopKey, int from, int to, AsyncCallback<List<RemovedArticleDTO>> callback);
	
	
	// Shop-PriceChanges
	void createPriceChangeForShop(String shopKey, PriceChangeDTO dto, AsyncCallback<Void> callback);

	void readOwnPriceChanges(AsyncCallback<Collection<PriceChangeDTO>> callback);

	void updatePriceChanges(Collection<PriceChangeDTO> dtos, AsyncCallback<Collection<PriceChangeDTO>> callback);

	void deletePriceChanges(Collection<PriceChangeDTO> dtos, AsyncCallback<Void> callback);

}
