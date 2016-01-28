package com.returnsoft.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "objective")
public class Objective implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2876744890575271393L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "obj_id")
	private Integer id;
	
	@Column(name = "obj_percent")
	private Double percent;
	
	@Column(name = "obj_percent_commission")
	private Double percentCommission;
	
	@Column(name = "obj_percent_incentive")
	private Double percentIncentive;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="obj_emp_id")
	private Employee employee;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getPercentIncentive() {
		return percentIncentive;
	}

	public void setPercentIncentive(Double percentIncentive) {
		this.percentIncentive = percentIncentive;
	}

	public Double getPercentCommission() {
		return percentCommission;
	}

	public void setPercentCommission(Double percentCommission) {
		this.percentCommission = percentCommission;
	}

	
	
	

}
