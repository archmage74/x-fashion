package com.xfashion.shared;

import java.io.Serializable;


public class DeliveryNoticeDTO extends DTO implements Serializable {

	private static final long serialVersionUID = -42480250174206491L;

	private Long id;
	
	private NotepadDTO notepad;
	
	private String targetShopKey;

	private ShopDTO targetShop;

	private String sourceShopKey;

	private ShopDTO sourceShop;

	public NotepadDTO getNotepad() {
		return notepad;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setNotepad(NotepadDTO notepad) {
		this.notepad = notepad;
	}

	public String getTargetShopKey() {
		return targetShopKey;
	}

	public void setTargetShopKey(String targetShopKey) {
		this.targetShopKey = targetShopKey;
	}

	public ShopDTO getTargetShop() {
		return targetShop;
	}

	public void setTargetShop(ShopDTO targetShop) {
		this.targetShop = targetShop;
	}

	public ShopDTO getSourceShop() {
		return sourceShop;
	}

	public void setSourceShop(ShopDTO sourceShop) {
		this.sourceShop = sourceShop;
	}

	public String getSourceShopKey() {
		return sourceShopKey;
	}

	public void setSourceShopKey(String sourceShopKey) {
		this.sourceShopKey = sourceShopKey;
	}

}
