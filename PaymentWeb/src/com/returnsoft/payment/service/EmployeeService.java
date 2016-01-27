package com.returnsoft.payment.service;

import java.util.List;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.exception.ServiceException;

public interface EmployeeService {
	
	public List<Employee> getAll() throws ServiceException;
	
	public Employee findById(Integer employeeId)
			throws ServiceException;

}
