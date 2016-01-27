package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.entity.Payment;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.EmployeeService;
import com.returnsoft.payment.service.ObjectiveService;
import com.returnsoft.payment.service.PaymentService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;

@ManagedBean
@ViewScoped
public class AddPaymentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -446959390580199803L;
	
	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private EmployeeService employeeService;
	
	@EJB
	private PaymentService paymentService;
	
	@EJB
	private ObjectiveService objectiveService;
	
	private Payment paymentSelected;
	
	private List<SelectItem> employees;
	private String employeeSelected;
	
	private Objective objectiveDetected;
	
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
			
			paymentSelected = new Payment();
			

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
	
	public void onChangeEmployee(){
		try {
			employeeSelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:employee_input");

			if (employeeSelected != null && employeeSelected.length() > 0) {
				Integer empId = Integer.parseInt(employeeSelected);
				Employee emp = employeeService.findById(empId);
				paymentSelected.setEmployee(emp);
			} else {
				paymentSelected.setEmployee(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void onKeyUpSale(){
		try {
			//System.out.println("ingreso a blur");
			
			
			String sales = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:sales");
			System.out.println("sales:"+sales);
			
			
			List<Objective> objectivesFound = paymentSelected.getEmployee().getObjectives();
			
			
			BigDecimal salesAmount = new BigDecimal(sales);
			BigDecimal objective = paymentSelected.getEmployee().getObjective().setScale(2,RoundingMode.HALF_UP);
			BigDecimal incentive = paymentSelected.getEmployee().getIncentive().setScale(2,RoundingMode.HALF_UP);
			BigDecimal basic = paymentSelected.getEmployee().getBasic().setScale(2,RoundingMode.HALF_UP);
			Double currentPercent = salesAmount.divide(objective,2,RoundingMode.HALF_UP).doubleValue()*100;
			
			objectiveDetected = null;
			System.out.println("objectives:"+objectivesFound.size());
			System.out.println("currentPercent:"+currentPercent);
			
			for (Objective objectiveFound : objectivesFound) {
				System.out.println("percentStart:"+objectiveFound.getPercentStart());
				System.out.println("percentEnd:"+objectiveFound.getPercentEnd());
				if (currentPercent >= objectiveFound.getPercentStart() && currentPercent <= objectiveFound.getPercentEnd()) {
					objectiveDetected = objectiveService.findById(objectiveFound.getId());
					break;
				}
			}
			BigDecimal commissionAmount = new BigDecimal(0);
			BigDecimal incentiveAmount =  new BigDecimal(0);
			
			if (objectiveDetected!=null) {
				
				System.out.println("salesAmount:"+objectiveDetected.getId());
				System.out.println("salesAmount:"+objectiveDetected.getCommission());
				System.out.println("salesAmount:"+salesAmount);
				
				commissionAmount = salesAmount.multiply(new BigDecimal(objectiveDetected.getCommission()/100)).setScale(2,RoundingMode.HALF_UP);
				incentiveAmount =  incentive.multiply(new BigDecimal(objectiveDetected.getIncentive()/100)).setScale(2,RoundingMode.HALF_UP);
				
				
			}else{
				commissionAmount = new BigDecimal(0);
				incentiveAmount =  new BigDecimal(0);
				
				facesUtil.sendErrorMessage("No se encontró objetivo");
				
			}
			
			BigDecimal totalAmount = basic.add(commissionAmount).add(incentiveAmount).setScale(2,RoundingMode.HALF_UP);

			paymentSelected.setCommission(commissionAmount);
			paymentSelected.setIncentive(incentiveAmount);
			paymentSelected.setTotal(totalAmount);	
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	
	public void add() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (employeeSelected != null && employeeSelected.length() > 0) {
				Employee employee = new Employee();
				Integer employeeId = Integer.parseInt(employeeSelected);
				employee.setId(employeeId);
				paymentSelected.setEmployee(employee);
				paymentService.add(paymentSelected);
				
				employeeSelected = "";
				paymentSelected = new Payment();

				//userSelected.setAreas(new ArrayList<Area>());
				
				
				facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");
				

			} else {
				facesUtil.sendErrorMessage("Debe seleccionar tipo de usuario");
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	

	public Payment getPaymentSelected() {
		return paymentSelected;
	}

	public void setPaymentSelected(Payment paymentSelected) {
		this.paymentSelected = paymentSelected;
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

	public Objective getObjectiveDetected() {
		return objectiveDetected;
	}

	public void setObjectiveDetected(Objective objectiveDetected) {
		this.objectiveDetected = objectiveDetected;
	}
	
	

}
