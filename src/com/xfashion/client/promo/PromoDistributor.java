package com.xfashion.client.promo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xfashion.shared.PromoDTO;

public class PromoDistributor {
	
	private static PromoDistributor promoProvider;
	
	private List<PromoDTO> promos;
	
	private Map<String, PromoDTO> promoMap;
	
	private PromoDistributor() {
		promos = new ArrayList<PromoDTO>();
		promoMap = new HashMap<String, PromoDTO>();
	}
	
	public static PromoDistributor getInstance() {
		if (promoProvider == null) {
			promoProvider = new PromoDistributor();
		}
		return promoProvider;
	}

	public List<PromoDTO> getPromos() {
		return new ArrayList<PromoDTO>(promos);
	}

	public void setPromos(List<PromoDTO> promos) {
		this.promos.clear();
		this.promos.addAll(promos);
		this.promoMap.clear();
		for (PromoDTO promo : promos) {
			promoMap.put(promo.getKey(), promo);
		}
	}
	
	public PromoDTO getPromoPerKey(String key) {
		return promoMap.get(key);
	}
	
}
