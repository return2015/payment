package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.EmployeeService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;
@ManagedBean
@ViewScoped
public class AddEmployeeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6021910729661568622L;
	
	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;
	
	private Employee employeeSelected;
	
	@EJB
	private EmployeeService employeeService;
	
	
	private Double percent;
	private Double percentCommission;
	private Double percentIncentive;
	
	
	public String initialize() {
		try {

			System.out.println("Ingreso a initialize");

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			
			employeeSelected = new Employee();
			employeeSelected.setObjectives(new ArrayList<Objective>());

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
	
	
	public void addObjective() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			// VALIDA SI YA EXISTE
			Boolean exist = false;
			for (Objective objective : employeeSelected.getObjectives()) {
				if (objective.getPercent().equals(percent / 100)) {
					exist = true;
				}
			}

			if (exist) {
				throw new Exception("El objetivo ya esta agregado.");

			}

			Objective ru = new Objective();

			System.out.println("percent" + percent);
			System.out.println(percent / 100);
			ru.setPercent(percent / 100);
			ru.setPercentIncentive(percentIncentive / 100);
			ru.setEmployee(employeeSelected);

			employeeSelected.getObjectives().add(ru);
			percent = null;
			percentIncentive = null;

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void deleteObjective(Objective ru) {
		try {
			System.out.println("ingreso a deleteRecruiter");

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			// System.out.println(ru.getUser().getId());
			if (ru != null) {
				for (Objective objective : employeeSelected.getObjectives()) {
					if (objective.getId().equals(ru.getId())) {
						employeeSelected.getObjectives().remove(objective);
						break;
					}
				}

			} else {
				facesUtil.sendErrorMessage("No se encontró reclutador.");
			}

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
			
			

			// if (trainingSelected != null && trainingSelected.getId() > 0) {

			System.out.println("Ingreso a Add");

			/*
			 * if (recruiterSelected!=null && recruiterSelected.length()>0) {
			 * Integer recruiterId = Integer.parseInt(recruiterSelected); User
			 * recruiter = new User(); recruiter.setId(recruiterId);
			 * requirementSelected.setRecruiter(recruiter); }
			 */

			employeeSelected.setPercentCommission(percentCommission/100);
			employeeService.add(employeeSelected);
			
			/*monthSelected="";
			yearSelected="";
			areaSelected="";
			subAreaSelected="";
			subAreas=new ArrayList<SelectItem>();
			totalAmount="";*/
			employeeSelected = new Employee();
			percentCommission=null;
			employeeSelected.setObjectives(new ArrayList<Objective>());
			
					

			// SE IMPRIME MENSAJE DE CONFIRMACION
			facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");

			// return "search_requirement";

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	


	public Employee getEmployeeSelected() {
		return employeeSelected;
	}


	public void setEmployeeSelected(Employee employeeSelected) {
		this.employeeSelected = employeeSelected;
	}


	public Double getPercent() {
		return percent;
	}


	public void setPercent(Double percent) {
		this.percent = percent;
	}


	public Double getPercentCommission() {
		return percentCommission;
	}


	public void setPercentCommission(Double percentCommission) {
		this.percentCommission = percentCommission;
	}


	public Double getPercentIncentive() {
		return percentIncentive;
	}


	public void setPercentIncentive(Double percentIncentive) {
		this.percentIncentive = percentIncentive;
	}
	

}
