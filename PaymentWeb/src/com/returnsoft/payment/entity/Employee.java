package com.returnsoft.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2954289236144212601L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Integer id;

	@Column(name = "emp_name")
	private String name;
	
	@Column(name = "emp_basic")
	private BigDecimal basic;
	
	@Column(name = "emp_objective")
	private BigDecimal objective;
	
	@Column(name = "emp_incentive")
	private BigDecimal incentive;
	
	@OneToMany(mappedBy="employee",fetch=FetchType.LAZY)
	private List<Objective> objectives;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBasic() {
		return basic;
	}

	public void setBasic(BigDecimal basic) {
		this.basic = basic;
	}

	public BigDecimal getObjective() {
		return objective;
	}

	public void setObjective(BigDecimal objective) {
		this.objective = objective;
	}

	public List<Objective> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<Objective> objectives) {
		this.objectives = objectives;
	}

	public BigDecimal getIncentive() {
		return incentive;
	}

	public void setIncentive(BigDecimal incentive) {
		this.incentive = incentive;
	}
	
	
	
	

}
