package com.xfashion.server.user;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.SoldArticle;
import com.xfashion.server.notepad.ArticleAmount;
import com.xfashion.server.notepad.Notepad;
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
	private String name;

	@Persistent
	private String description;
	
	@Persistent
	Set<Notepad> notepads;
	
	@Persistent
	Set<DeliveryNotice> deliveryNotices;
	
	@Persistent
	Set<ArticleAmount> articles;
	
	@Persistent
	Set<SoldArticle> soldArticles;
	
	public Shop() {
		notepads = new HashSet<Notepad>();
		deliveryNotices = new HashSet<DeliveryNotice>();
		articles = new HashSet<ArticleAmount>();
		soldArticles = new HashSet<SoldArticle>();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Set<SoldArticle> getSoldArticles() {
		return soldArticles;
	}

	public ShopDTO createDTO() {
		ShopDTO dto = new ShopDTO();
		dto.setKeyString(getKeyAsString());
		dto.setName(getName());
		dto.setDescription(getDescription());
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
		setName(dto.getName());
		setDescription(dto.getDescription());
	}

	public Set<ArticleAmountDTO> createStock() {
		Set<ArticleAmountDTO> dtos = new HashSet<ArticleAmountDTO>();
		for (ArticleAmount articleAmount : getArticles()) {
			dtos.add(articleAmount.createDTO());
		}
		return dtos;
	}

}
