package com.returnsoft.payment.service;

import java.util.Date;
import java.util.List;

import com.returnsoft.payment.entity.Payment;
import com.returnsoft.payment.exception.ServiceException;

public interface PaymentService {
	
	public void add(Payment payment) throws ServiceException;
	
	public Payment edit(Payment payment) throws ServiceException;
	
	public List<Payment> findList(Integer employeeId, Date date) throws ServiceException;

}
