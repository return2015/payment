package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.EmployeeService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;
@ManagedBean
@ViewScoped
public class SearchEmployeeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836436208587775490L;
	
	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private EmployeeService employeeService;
	
	private List<Employee> employees;
	private Employee employeeSelected;
	
	
	public String initialize() {
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			employees = employeeService.getAll();
			

			return null;
		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}
	}
	
	
	public void edit() {
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() < 1) {
				throw new UserLoggedNotFoundException();
			}
			
			System.out.println("Ingreso a edit");
			
			
			
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("resizable", true);
			options.put("contentHeight", 400);
			options.put("contentWidth", 700);

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(employeeSelected.getId()));
			paramMap.put("employeeId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_employee", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			Employee employeeReturn = (Employee) event.getObject();
			
			int i = 0;
			for (Employee employee : employees) {
				//Payment payement = payments.get(i);
				if (employee.getId().equals(employeeReturn.getId())) {
					employees.set(i, employeeReturn);
					employeeSelected = employeeReturn;
					break;
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
		
	}


	public List<Employee> getEmployees() {
		return employees;
	}


	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}


	public Employee getEmployeeSelected() {
		return employeeSelected;
	}


	public void setEmployeeSelected(Employee employeeSelected) {
		this.employeeSelected = employeeSelected;
	}
	
	

}
