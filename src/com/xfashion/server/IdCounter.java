package com.xfashion.server;

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
