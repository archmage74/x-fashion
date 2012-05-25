package com.xfashion.server.util;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.xfashion.client.util.IdCounterService;
import com.xfashion.server.PMF;

public class IdCounterServiceImpl implements IdCounterService {

	@Override
	public Long getNewId(IdCounterType type) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long id = null;
		try {
			id = getNewId(pm, type);
		} finally {
			pm.close();
		}
		return id;
	}

	private Long getNewId(PersistenceManager pm, IdCounterType type) {
		Transaction tx = pm.currentTransaction();
		Long id = null;
		try {
			tx.begin();
			IdCounter idCounter = null;
			try {
				idCounter = pm.getObjectById(IdCounter.class, type.name());
			} catch (JDOObjectNotFoundException e) {
				// the needed idCounter does not exist, so we have to make a new one
			}
			if (idCounter == null) {
				id = type.startValue();
				idCounter = new IdCounter(type.name(), id);
				pm.makePersistent(idCounter);
			} else {
				id = idCounter.getIdCounter() + 1;
				idCounter.setIdCounter(id);
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return id;
	}
	
}
