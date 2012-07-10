package com.xfashion.server;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.xfashion.shared.PromoDTO;

@PersistenceCapable
public class Promos {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Set<Promo> promos;

	@Persistent
	Long idCounter;

	public Promos() {
		this.promos = new HashSet<Promo>();
		this.idCounter = 0L;
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Set<PromoDTO> getDtos() {
		Set<PromoDTO> dtos = new HashSet<PromoDTO>(getPromos().size());
		for (Promo o : getPromos()) {
			PromoDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public Set<Promo> getPromos() {
		return promos;
	}

	public void setPromos(Set<Promo> promos) {
		this.promos = promos;
	}

	public Long getIdCounter() {
		return idCounter;
	}

	public void setIdCounter(Long idCounter) {
		this.idCounter = idCounter;
	}

}
