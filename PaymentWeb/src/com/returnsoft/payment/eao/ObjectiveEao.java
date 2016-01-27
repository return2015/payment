package com.returnsoft.payment.eao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.exception.EaoException;

@Stateless
public class ObjectiveEao {

	@PersistenceContext
	private EntityManager em;

	public Objective findById(Integer objectiveId) throws EaoException {
		try {
			Objective objective = em.find(Objective.class, objectiveId);

			return objective;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
