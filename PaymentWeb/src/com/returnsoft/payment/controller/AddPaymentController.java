package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

	public void onChangeEmployee() {
		try {
			employeeSelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:employee_input");

			paymentSelected = new Payment();
			
			if (employeeSelected != null && employeeSelected.length() > 0) {
				Integer empId = Integer.parseInt(employeeSelected);
				Employee emp = employeeService.findById(empId);
				paymentSelected.setEmployee(emp);
				
			} else {
				paymentSelected.setEmployee(null);
			}
			
			objectiveDetected=null;
			

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void onKeyUpSale() {
		try {
			// System.out.println("ingreso a blur");

			if (paymentSelected.getEmployee() == null || paymentSelected.getEmployee().getId() == null) {
				throw new Exception("Seleccione empleado");
			}

			String sales = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:sales");

			System.out.println("sales:" + sales);

			BigDecimal basic = paymentSelected.getEmployee().getBasic().setScale(2, RoundingMode.HALF_UP);
			BigDecimal objective = paymentSelected.getEmployee().getObjective().setScale(2, RoundingMode.HALF_UP);
			Double percentCommission = paymentSelected.getEmployee().getPercentCommission();
			BigDecimal salesAmount = new BigDecimal(sales);
			BigDecimal commissionAmount = salesAmount.multiply(new BigDecimal(percentCommission)).setScale(4,
					RoundingMode.HALF_UP);
			BigDecimal incentiveTotal = paymentSelected.getEmployee().getIncentive().setScale(2, RoundingMode.HALF_UP);
			BigDecimal incentiveAmount = new BigDecimal(0);
			
			
			
			
			List<Objective> objectivesFound = paymentSelected.getEmployee().getObjectives();

			if (objectivesFound != null && objectivesFound.size() > 0) {

				Double currentPercent = salesAmount.divide(objective, 4, RoundingMode.HALF_UP).doubleValue();

				/////////////////
				Collections.sort(objectivesFound, new Comparator<Objective>() {
					@Override
					public int compare(Objective p1, Objective p2) {
						return p1.getPercent().compareTo(p2.getPercent());
					}

				});

				objectiveDetected = null;
				for (int i = 0; i < objectivesFound.size(); i++) {
					Objective objectiveFound = objectivesFound.get(i);
					if (i + 1 < objectivesFound.size()) {
						Objective objectiveFound2 = objectivesFound.get(i + 1);
						if (currentPercent >= objectiveFound.getPercent()
								&& currentPercent < objectiveFound2.getPercent()) {
							objectiveDetected = objectiveService.findById(objectiveFound.getId());
							break;
						}
					} else {
						if (currentPercent >= objectiveFound.getPercent()) {
							objectiveDetected = objectiveService.findById(objectiveFound.getId());
							break;
						}
					}
				}

				if (objectiveDetected != null) {
					incentiveAmount = incentiveTotal.multiply(new BigDecimal(objectiveDetected.getPercentIncentive())).setScale(4,
							RoundingMode.HALF_UP);
				} else {
					
					incentiveAmount = new BigDecimal(0);
					facesUtil.sendConfirmMessage("No se encontró objetivo");
				}
				
			} else {
				incentiveAmount = new BigDecimal(0);
				facesUtil.sendConfirmMessage("No se encontró objetivo");
			}
			
			//////////////////////////////////////

			BigDecimal totalAmount = basic.add(commissionAmount).add(incentiveAmount).setScale(2, RoundingMode.HALF_UP);
			
			paymentSelected.setBasic(basic);
			paymentSelected.setObjective(objective);
			paymentSelected.setIncentiveTotal(incentiveTotal);
			paymentSelected.setPercentCommision(percentCommission);
			
			paymentSelected.setSale(salesAmount);
			if (objectiveDetected!=null) {
				paymentSelected.setPercentIncentive(objectiveDetected.getPercentIncentive());
				paymentSelected.setPercent(objectiveDetected.getPercent());	
			}
			paymentSelected.setIncentive(incentiveAmount);
			paymentSelected.setCommission(commissionAmount);
			
			paymentSelected.setTotal(totalAmount);
			
			
			paymentSelected.setCreatedAt(new Date());
			paymentSelected.setCreatedBy(sessionBean.getUser());

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

				// userSelected.setAreas(new ArrayList<Area>());

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
