package com.xfashion.shared;

import java.io.Serializable;

import com.xfashion.shared.notepad.NotepadDTO;

public class DeliveryNoticeDTO extends DTO implements Serializable {

	private static final long serialVersionUID = -42480250174206491L;

	private Long id;
	
	private NotepadDTO notepad;
	
	private String targetShopKey;

	private ShopDTO targetShop;

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

}
