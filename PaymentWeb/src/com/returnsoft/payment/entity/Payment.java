package com.returnsoft.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "payment")
public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7944963196579933984L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pay_id")
	private Long id;
	
	@Column(name = "pay_obj_percent_start")
	private Double percentStart;
	
	@Column(name = "pay_obj_percent_end")
	private Double percentEnd;
	
	@Column(name = "pay_obj_commision")
	private Double percentCommision;
	
	@Column(name = "pay_obj_incentive")
	private Double percentIncentive;
	
	@Column(name = "pay_basic")
	private BigDecimal basic;
	
	@Column(name = "pay_objective")
	private BigDecimal objective;
	
	
	
	@Column(name = "pay_commision")
	private BigDecimal commission;
	
	@Column(name = "pay_incentive")
	private BigDecimal incentive;
	
	@Column(name = "pay_sale")
	private BigDecimal sale;
	
	@Column(name = "pay_total")
	private BigDecimal total;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_date")
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pay_emp_id")
	private Employee employee;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPercentCommision() {
		return percentCommision;
	}

	public void setPercentCommision(Double percentCommision) {
		this.percentCommision = percentCommision;
	}

	public Double getPercentIncentive() {
		return percentIncentive;
	}

	public void setPercentIncentive(Double percentIncentive) {
		this.percentIncentive = percentIncentive;
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

	

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getIncentive() {
		return incentive;
	}

	public void setIncentive(BigDecimal incentive) {
		this.incentive = incentive;
	}

	public BigDecimal getSale() {
		return sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
	
	
	

}
