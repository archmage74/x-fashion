package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.promo.PromoService;
import com.xfashion.shared.PromoDTO;

public class PromoServiceImpl extends RemoteServiceServlet implements PromoService {

	private static final long serialVersionUID = 1L;
	
	@Override
	public PromoDTO createPromo(PromoDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Promo promo = createPromo(pm, dto);
			dto = promo.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private Promo createPromo(PersistenceManager pm, PromoDTO dto) {
		Transaction tx = pm.currentTransaction();
		Promo promo = null;
		try {
			tx.begin();
			Promos promos = readAllPromos(pm);
			promo = new Promo(dto);
			Long id = generatePromoId(promos);
			Long promoId = 300000000000L + id;
			promo.setEan(promoId);
			promos.getPromos().add(promo);
			pm.makePersistent(promos);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return promo;
	}

	private Long generatePromoId(Promos promos) {
		Long newId = (promos.getIdCounter() + 1L);
		promos.setIdCounter(newId);
		return new Long(newId);
	}

	public PromoDTO readPromo(String promoKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PromoDTO dto = null;
		try {
			Promo promo = readPromo(pm, promoKey);
			dto = promo.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

	
	
	@Override
	public List<PromoDTO> readActivePromos() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<PromoDTO> dtos = new ArrayList<PromoDTO>();
		try {
			Promos promos = readAllPromos(pm);
			for (Promo promo : promos.getPromos()) {
				if (promo.getActivated()) {
					dtos.add(promo.createDTO());
				}
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@Override
	public List<PromoDTO> readAllPromos() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<PromoDTO> dtos = new ArrayList<PromoDTO>();
		try {
			Promos promos = readAllPromos(pm);
			for (Promo promo : promos.getPromos()) {
				dtos.add(promo.createDTO());
			}
		} finally {
			pm.close();
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	private Promos readAllPromos(PersistenceManager pm) {
		Query query = pm.newQuery(Promos.class);
		Promos item;
		List<Promos> items = (List<Promos>) query.execute();
		if (items.size() == 0) {
			item = new Promos();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}
	
	private Promo readPromo(PersistenceManager pm, String keyString) {
		Promo promo = pm.getObjectById(Promo.class, KeyFactory.stringToKey(keyString));
		return promo;
	}
	
	@Override
	public PromoDTO updatePromo(PromoDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Promo promo = readPromo(pm, dto.getKey());
			promo.updateFromDTO(dto);
			dto = promo.createDTO();
		} finally {
			pm.close();
		}
		return dto;
	}

}
