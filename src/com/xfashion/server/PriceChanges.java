package com.xfashion.server;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class PriceChanges {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private List<PriceChange> priceChanges;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<PriceChange> getPriceChanges() {
		return priceChanges;
	}

	public void setPriceChanges(List<PriceChange> priceChanges) {
		this.priceChanges = priceChanges;
	}

}
