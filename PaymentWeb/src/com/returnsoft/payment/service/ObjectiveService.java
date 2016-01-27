package com.returnsoft.payment.service;

import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.exception.ServiceException;

public interface ObjectiveService {
	
	public Objective findById(Integer objectiveId)
			throws ServiceException;

}
