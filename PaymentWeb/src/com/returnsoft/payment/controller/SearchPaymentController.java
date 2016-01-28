package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.entity.Payment;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.EmployeeService;
import com.returnsoft.payment.service.PaymentService;

import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;
@ManagedBean
@ViewScoped
public class SearchPaymentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5049694663787165148L;
	
	
	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private EmployeeService employeeService;
	
	@EJB
	private PaymentService paymentService;
	
	private List<SelectItem> employees;
	private String employeeSelected;
	
	private Date date;
	
	private Payment paymentSelected;
	private List<Payment> payments;
	
	public String initialize() {
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			List<Employee> employeesDto = employeeService.getAll();
			employees = new ArrayList<SelectItem>();
			for (Employee employeeDto : employeesDto) {
				SelectItem item = new SelectItem();
				item.setValue(employeeDto.getId().toString());
				item.setLabel(employeeDto.getName());
				employees.add(item);
			}
			

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

	public void search() {

		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			Integer employeeId = null;
			if (employeeSelected != null && !employeeSelected.equals("")) {
				employeeId = Integer.parseInt(employeeSelected);
			}

			

			
			  payments = paymentService.findList(employeeId, date);
			 

					  paymentSelected = null;

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
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
			options.put("contentHeight", 500);
			options.put("contentWidth", 1000);

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(paymentSelected.getId()));
			paramMap.put("paymentId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_payment", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			Payment paymentReturn = (Payment) event.getObject();
			
			int i = 0;
			for (Payment payment : payments) {
				//Payment payement = payments.get(i);
				if (payment.getId().equals(paymentReturn.getId())) {
					payments.set(i, paymentReturn);
					paymentSelected = paymentReturn;
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
	
	
	public void delete(){
		try {
			
			paymentService.delete(paymentSelected);
			
			payments.remove(paymentSelected);
			
			/*int i = 0;
			for (Payment payment : payments) {
				//Payment payement = payments.get(i);
				if (payment.getId().equals(paymentSelected.getId())) {
					payments.set(i, paymentReturn);
					paymentSelected = paymentReturn;
					break;
				}
				i++;
			}*/
			
			facesUtil.sendConfirmMessage("Se eliminó satisfactoriamente.");
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	
	

	public List<SelectItem> getEmployees() {
		return employees;
	}

	public void setEmployees(List<SelectItem> employees) {
		this.employees = employees;
	}

	public String getEmployeeSelected() {
		return employeeSelected;
	}

	public void setEmployeeSelected(String employeeSelected) {
		this.employeeSelected = employeeSelected;
	}

	public Payment getPaymentSelected() {
		return paymentSelected;
	}

	public void setPaymentSelected(Payment paymentSelected) {
		this.paymentSelected = paymentSelected;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}





	

	
	
	
	

}
