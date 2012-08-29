package com.xfashion.server.user;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.shared.DeliveryNoticeDTO;

@PersistenceCapable
public class DeliveryNotice {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Long id;
	
	@Persistent
	private Notepad notepad;
	
	@Persistent
	private Key targetShopKey;
	
	@Persistent
	private Key sourceShopKey;

	public DeliveryNotice() {
		
	}
	
	public DeliveryNotice(DeliveryNoticeDTO dto) {
		this();
		updateFromDTO(dto);
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Notepad getNotepad() {
		return notepad;
	}

	public void setNotepad(Notepad notepad) {
		this.notepad = notepad;
	}

	public Key getTargetShopKey() {
		return targetShopKey;
	}
	
	public String getTargetShopKeyAsString() {
		return KeyFactory.keyToString(targetShopKey);
	}

	public void setTargetShopKey(Key targetShopKey) {
		this.targetShopKey = targetShopKey;
	}

	public Key getSourceShopKey() {
		return sourceShopKey;
	}

	public String getSourceShopKeyAsString() {
		if (sourceShopKey != null) {
			return KeyFactory.keyToString(sourceShopKey);
		} else {
			return null;
		}
	}

	public void setSourceShopKey(Key sourceShopKey) {
		this.sourceShopKey = sourceShopKey;
	}

	public DeliveryNoticeDTO createDTO() {
		DeliveryNoticeDTO dto = new DeliveryNoticeDTO();
		dto.setKey(getKeyAsString());
		dto.setId(getId());
		dto.setNotepad(notepad.createDTO());
		dto.setTargetShopKey(getTargetShopKeyAsString());
		dto.setSourceShopKey(getSourceShopKeyAsString());
		return dto;
	}
	
	public DeliveryNoticeDTO createFlatDTO() {
		DeliveryNoticeDTO dto = new DeliveryNoticeDTO();
		dto.setKey(getKeyAsString());
		dto.setId(getId());
		dto.setNotepad(notepad.createFlatDTO());
		dto.setTargetShopKey(getTargetShopKeyAsString());
		dto.setSourceShopKey(getSourceShopKeyAsString());
		return dto;
	}
	
	public void updateFromDTO(DeliveryNoticeDTO dto) {
		if (notepad != null) {
			notepad.updateFromDTO(dto.getNotepad());
		} else {
			notepad = new Notepad(dto.getNotepad());
		}
		setId(dto.getId());
		setTargetShopKey(KeyFactory.stringToKey(dto.getTargetShop().getKeyString()));
		setSourceShopKey(KeyFactory.stringToKey(dto.getSourceShop().getKeyString()));
	}

}
