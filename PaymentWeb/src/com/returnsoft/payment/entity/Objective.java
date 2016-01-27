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
	
	@Column(name = "obj_percent_start")
	private Double percentStart;
	
	@Column(name = "obj_percent_end")
	private Double percentEnd;
	
	@Column(name = "obj_commission")
	private Double commission;
	
	@Column(name = "obj_incentive")
	private Double incentive;
	
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

	public Double getPercentStart() {
		return percentStart;
	}

	public void setPercentStart(Double percentStart) {
		this.percentStart = percentStart;
	}

	public Double getPercentEnd() {
		return percentEnd;
	}

	public void setPercentEnd(Double percentEnd) {
		this.percentEnd = percentEnd;
	}



	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getIncentive() {
		return incentive;
	}

	public void setIncentive(Double incentive) {
		this.incentive = incentive;
	}
	
	
	

}
