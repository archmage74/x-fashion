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
import com.xfashion.server.SoldArticle;
import com.xfashion.server.notepad.ArticleAmount;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;
import com.xfashion.shared.ShopDTO;

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
	Set<Notepad> notepads;
	
	@Persistent
	Set<DeliveryNotice> deliveryNotices;
	
	@Persistent
	Set<ArticleAmount> articles;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="sellDate desc"))
	List<SoldArticle> soldArticles;
	
	@Persistent(mappedBy = "shop")
	@Order(extensions = @Extension(vendorName="datanucleus",key="list-ordering", value="addDate desc"))
	List<AddedArticle> addedArticles;
	
	@Persistent
	Set<PriceChange> priceChanges;
	
	public Shop() {
		notepads = new HashSet<Notepad>();
		deliveryNotices = new HashSet<DeliveryNotice>();
		articles = new HashSet<ArticleAmount>();
		soldArticles = new ArrayList<SoldArticle>();
		addedArticles = new ArrayList<AddedArticle>();
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

	public Set<Notepad> getNotepads() {
		return notepads;
	}

	public Set<ArticleAmount> getArticles() {
		return articles;
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
	}

	public Set<ArticleAmountDTO> createStock() {
		Set<ArticleAmountDTO> dtos = new HashSet<ArticleAmountDTO>();
		for (ArticleAmount articleAmount : getArticles()) {
			dtos.add(articleAmount.createDTO());
		}
		return dtos;
	}
	
	public Set<AddedArticleDTO> createWareInput() {
		Set<AddedArticleDTO> dtos = new HashSet<AddedArticleDTO>();
		for (AddedArticle addedArticle : getAddedArticles()) {
			dtos.add(addedArticle.createDTO());
		}
		return dtos;
	}

}
