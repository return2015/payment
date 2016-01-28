package com.returnsoft.payment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.returnsoft.payment.entity.Employee;
import com.returnsoft.payment.entity.Objective;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.EmployeeService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;
@ManagedBean
@ViewScoped
public class EditEmployeeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9213842083614892223L;
	
	
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
	
	public String initialize(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			
			String employeeId = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("employeeId");
			
			if (employeeId==null || employeeId.length()==0) {
				throw new Exception("No se envío el empleado");
			}

			System.out.println("employeeId:" + employeeId);
			

			employeeSelected = employeeService.findById(Integer.parseInt(employeeId));
			
			if (employeeSelected==null) {
				throw new Exception("No se encontró el empleado.");
			}
			
			

			//requirementSelected = new Requirement();
			//requirementSelected.setUsers(new ArrayList<RequirementUser>());

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

			//if (recruiterSelected != null && recruiterSelected.length() > 0) {

				//Integer recruiterId = Integer.parseInt(recruiterSelected);

				//if (amount != null && amount.length() > 0) {
					
					//Integer amountInt = Integer.parseInt(amount);
					
					//if (amountInt>0) {
						// VALIDA SI YA EXISTE
						Boolean exist = false;
						for (Objective objective : employeeSelected.getObjectives()) {
							if (objective.getPercent().equals(percent/100)) {
								exist = true;
							}
						}

						if (exist) {
							facesUtil.sendErrorMessage("El objetivo ya esta agregado.");
						} else {
							//User user = userService.findById(Integer.parseInt(recruiterSelected));
							Objective ru = new Objective();
							ru.setPercent(percent/100);
							ru.setPercentCommission(percentCommission/100);
							ru.setPercentIncentive(percentIncentive/100);
							ru.setEmployee(employeeSelected);
							
							//ru.setRequirement(requirementSelected);
							//ru.setRequirementId(requirementSelected.getId());
							//ru.setUserId(user.getId());
							//ru.setAmount(Integer.parseInt(amount));
							
							employeeSelected.getObjectives().add(ru);
							percent = null;
							percentCommission=null;
							percentIncentive=null;
							
							/*Integer total =0;
							for (RequirementUser requirementUser : requirementSelected.getUsers()) {
								total+=requirementUser.getAmount();
							}
							requirementSelected.setAmount(total);*/
							//totalAmount=total+"";
						}
					/*}else{
						facesUtil.sendErrorMessage("La cantidad debe ser mayor a cero.");	
					}*/
					
					

				/*} else {
					facesUtil.sendErrorMessage("Debe ingresar cantidad.");
				}*/

			/*} else {
				facesUtil.sendErrorMessage("Debe seleccionar reclutador.");
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void deleteObjective(Objective ru){
		try {
			System.out.println("ingreso a deleteRecruiter");
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			//System.out.println(ru.getUser().getId());
			if (ru!=null) {
				for (Objective objective : employeeSelected.getObjectives()) {
					if (objective.getId().equals(ru.getId())) {
						employeeSelected.getObjectives().remove(objective);
						break;
					}
				}
				
				
			}else{
				facesUtil.sendErrorMessage("No se encontró reclutador.");	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void edit(){
		
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			employeeSelected = employeeService.edit(employeeSelected);
			
			facesUtil.sendConfirmMessage("Se editó satisfactoriamente");
			
			RequestContext.getCurrentInstance().closeDialog(employeeSelected);
			
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
