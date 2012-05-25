package com.xfashion.server.util;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class IdCounter {

	@PrimaryKey
	@Persistent
	String id;
	
	@Persistent
	Long idCounter;

	public IdCounter(String id) {
		this(id, 0L);
	}
	
	public IdCounter(String id, Long initId) {
		this.id = id;
		this.idCounter = new Long(initId);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getIdCounter() {
		return idCounter;
	}

	public void setIdCounter(Long idCounter) {
		this.idCounter = idCounter;
	}
	
}
