package com.xfashion.server.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.AddedArticle;
import com.xfashion.server.PriceChange;
import com.xfashion.server.RemovedArticle;
import com.xfashion.server.SoldArticle;
import com.xfashion.server.StockArticle;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.UserCountry;

@PersistenceCapable
public class Shop {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent 
	private String shortName;
	
	@Persistent
	private String name;

	@Persistent
	private String street;
	
	@Persistent
	private String housenumber;
	
	@Persistent
	private String postalcode;
	
	@Persistent
	private String city;
	
	@Persistent
	private String country;
	
	@Persistent
	Set<Notepad> notepads;
	
	@Persistent
	Set<DeliveryNotice> deliveryNotices;
	
	@Persistent
	List<StockArticle> articles;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sellDate desc"))
	List<SoldArticle> soldArticles;
	
	@Persistent(mappedBy = "shop")
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="addDate desc"))
	List<AddedArticle> addedArticles;
	
	@Persistent(mappedBy = "shop")
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="removeDate desc"))
	List<RemovedArticle> removedArticles;
	
	@Persistent
	Set<PriceChange> priceChanges;
	
	public Shop() {
		notepads = new HashSet<Notepad>();
		deliveryNotices = new HashSet<DeliveryNotice>();
		articles = new ArrayList<StockArticle>();
		soldArticles = new ArrayList<SoldArticle>();
		addedArticles = new ArrayList<AddedArticle>();
		removedArticles = new ArrayList<RemovedArticle>();
	}
	
	public Shop(ShopDTO dto) {
		this();
		updateFromDTO(dto);
	}
	
	public Key getKey() {
		return key;
	}
    
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}

	public String getShortName() {
		if (shortName == null) {
			return name;
		} else {
			return shortName;
		}
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Notepad> getNotepads() {
		return notepads;
	}

	public List<StockArticle> getArticles() {
		return articles;
	}
	
	public void addArticle(StockArticle articleAmount) {
		articles.add(articleAmount);
	}
	
	public void setArticles(List<StockArticle> articles) {
		this.articles = articles;
	}

	public Set<DeliveryNotice> getDeliveryNotices() {
		return deliveryNotices;
	}
	
	public List<SoldArticle> getSoldArticles() {
		return soldArticles;
	}

	public List<AddedArticle> getAddedArticles() {
		return addedArticles;
	}
	
	public void addAddedArticle(AddedArticle addedArticle) {
		addedArticles.add(addedArticle);
		addedArticle.setShop(this);
	}

	public List<RemovedArticle> getRemovedArticles() {
		return removedArticles;
	}

	public void addRemovedArticle(RemovedArticle removedArticle) {
		removedArticles.add(removedArticle);
		removedArticle.setShop(this);
	}

	public Set<PriceChange> getPriceChanges() {
		return priceChanges;
	}

	public void setPriceChanges(Set<PriceChange> priceChanges) {
		this.priceChanges = priceChanges;
	}

	public ShopDTO createDTO() {
		ShopDTO dto = new ShopDTO();
		dto.setKeyString(getKeyAsString());
		dto.setShortName(getShortName());
		dto.setName(getName());
		dto.setStreet(getStreet());
		dto.setHousenumber(getHousenumber());
		dto.setPostalcode(getPostalcode());
		dto.setCity(getCity());
		if (getCountry() != null) {
			dto.setCountry(UserCountry.valueOf(getCountry()));
		}
		return dto;
	}

	public Set<NotepadDTO> createNotepadDTOs() {
		Set<NotepadDTO> dtos = new HashSet<NotepadDTO>();
		for (Notepad notepad : notepads) {
			dtos.add(notepad.createDTO());
		}
		return dtos;
	}

	public Set<DeliveryNoticeDTO> createDeliveryNoticeDTOs() {
		Set<DeliveryNoticeDTO> dtos = new HashSet<DeliveryNoticeDTO>();
		for (DeliveryNotice deliveryNotice : deliveryNotices) {
			dtos.add(deliveryNotice.createDTO());
		}
		return dtos;
	}

	public void updateFromDTO(ShopDTO dto) {
		setShortName(dto.getShortName());
		setName(dto.getName());
		setStreet(dto.getStreet());
		setHousenumber(dto.getHousenumber());
		setPostalcode(dto.getPostalcode());
		setCity(dto.getCity());
		setCountry(dto.getCountry().name());
	}

	public List<ArticleAmountDTO> createStock() {
		List<ArticleAmountDTO> dtos = new ArrayList<ArticleAmountDTO>();
		for (StockArticle articleAmount : getArticles()) {
			dtos.add(articleAmount.createDTO());
		}
		return dtos;
	}
	
	public List<AddedArticleDTO> createWareInput() {
		List<AddedArticleDTO> dtos = new ArrayList<AddedArticleDTO>();
		for (AddedArticle addedArticle : getAddedArticles()) {
			dtos.add(addedArticle.createDTO());
		}
		return dtos;
	}

}
