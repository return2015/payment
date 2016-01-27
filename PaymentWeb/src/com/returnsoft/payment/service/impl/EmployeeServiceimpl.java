package com.returnsoft.payment.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.payment.eao.EmployeeEao;
import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.exception.ServiceException;
import com.returnsoft.payment.service.EmployeeService;
@Stateless
public class EmployeeServiceimpl implements EmployeeService {

	@EJB
	private EmployeeEao employeeEao;
	
	@Override
	public Employee findById(Integer employeeId)
			throws ServiceException {
		try {
			Employee employee = employeeEao.findById(employeeId);
			return employee;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}

	@Override
	public List<Employee> getAll() throws ServiceException {
		try {

			return employeeEao.getAll();

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
