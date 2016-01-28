package com.returnsoft.payment.service.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.payment.eao.PaymentEao;
import com.returnsoft.payment.entity.Payment;
import com.returnsoft.payment.exception.PaymentNotFoundException;
import com.returnsoft.payment.exception.ServiceException;
import com.returnsoft.payment.service.PaymentService;
import com.returnsoft.util.FacesUtil;
@Stateless
public class PaymentServiceImpl implements PaymentService {

	@EJB
	private PaymentEao paymentEao;

	

	@Override
	public void add(Payment payment) throws ServiceException {
		try {

			paymentEao.add(payment);

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}

	}

	@Override
	public Payment edit(Payment payment) throws ServiceException {
		try {

			Payment paymentFound = paymentEao.findById(payment.getId());

			if (paymentFound == null) {
				throw new PaymentNotFoundException();
			}

			payment = paymentEao.edit(payment);

			return payment;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}

	}
	@Override
	public void delete(Payment payment) throws ServiceException {
		try {

			Payment paymentFound = paymentEao.findById(payment.getId());

			if (paymentFound == null) {
				throw new PaymentNotFoundException();
			}

			paymentEao.delete(paymentFound);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}

	}

	@Override
	public List<Payment> findList(Integer employeeId, Date date) throws ServiceException {
		try {

			return paymentEao.findList(employeeId, date);

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && e.getMessage().trim().length() > 0) {
				throw new ServiceException(e.getMessage(), e);
			} else {
				throw new ServiceException();
			}
		}
	}

}
