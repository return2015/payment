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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_date")
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pay_emp_id")
	private Employee employee;
	
	@Column(name = "pay_basic")
	private BigDecimal basic;
	
	@Column(name = "pay_objective")
	private BigDecimal objective;
	
	
	
	@Column(name = "pay_incentive_total")
	private BigDecimal incentiveTotal;
	
	@Column(name = "pay_percent_commission")
	private Double percentCommision;
	
	
	/////////////
	
	@Column(name = "pay_sale")
	private BigDecimal sale;
	
	@Column(name = "pay_percent")
	private Double percent;
	
	@Column(name = "pay_percent_incentive")
	private Double percentIncentive;
	
	@Column(name = "pay_incentive")
	private BigDecimal incentive;
	
	@Column(name = "pay_commission")
	private BigDecimal commission;
	
	@Column(name = "pay_total")
	private BigDecimal total;
	
	
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_created_at")
	private Date createdAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pay_created_by")
	private User createdBy;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	



	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getIncentiveTotal() {
		return incentiveTotal;
	}

	public void setIncentiveTotal(BigDecimal incentiveTotal) {
		this.incentiveTotal = incentiveTotal;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	


	
	
	
	
	

}
