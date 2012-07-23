package com.xfashion.server;

import java.util.HashSet;
import java.util.Set;

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
	private Set<PriceChange> priceChanges;

	public PriceChanges() {
		priceChanges = new HashSet<PriceChange>();
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Set<PriceChange> getPriceChanges() {
		return priceChanges;
	}

	public void setPriceChanges(Set<PriceChange> priceChanges) {
		this.priceChanges = priceChanges;
	}

}
