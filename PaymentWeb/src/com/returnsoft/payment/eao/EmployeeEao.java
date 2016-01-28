package com.returnsoft.payment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.exception.EaoException;

@Stateless
public class EmployeeEao {
	
	@PersistenceContext
	private EntityManager em;
	public Employee edit(Employee employee) throws EaoException {
		try {
			
			employee = em.merge(employee);
			em.flush();
			
			return employee;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public void add(Employee employee) throws EaoException {
		try {
			
			em.persist(employee);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public Employee findById(Integer employeeId) throws EaoException {
		try {
			Employee employee = em.find(Employee.class, employeeId);
			
			return employee;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<Employee> getAll() throws EaoException {
		try {
			
			String query = 
					"SELECT e FROM Employee e WHERE e.id>0 ";
			
			TypedQuery<Employee> q = em.createQuery(
					query, Employee.class);
			
			
			List<Employee> employees = q.getResultList();

			return employees;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
