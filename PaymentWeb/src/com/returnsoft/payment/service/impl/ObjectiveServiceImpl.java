package com.returnsoft.payment.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.payment.eao.ObjectiveEao;
import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.exception.ServiceException;
import com.returnsoft.payment.service.ObjectiveService;
@Stateless
public class ObjectiveServiceImpl implements ObjectiveService{
	
	@EJB
	private ObjectiveEao objectiveEao;

	@Override
	public Objective findById(Integer objectiveId) throws ServiceException {
		try {
			
			Objective objective = objectiveEao.findById(objectiveId);
			return objective;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}

}
