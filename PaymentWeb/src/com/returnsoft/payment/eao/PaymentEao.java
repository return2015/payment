package com.returnsoft.payment.eao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.payment.entity.Payment;
import com.returnsoft.payment.exception.EaoException;
@Stateless
public class PaymentEao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void add(Payment payment) throws EaoException {
		try {
			
			em.persist(payment);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public Payment edit(Payment payment) throws EaoException {
		try {
			
			payment = em.merge(payment);
			em.flush();
			
			return payment;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	public Payment findById(Long paymentId) throws EaoException {
		try {
			Payment payment = em.find(Payment.class, paymentId);
			
			return payment;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public List<Payment> findList(Integer employeeId, Date date) throws EaoException {
		try {
			
			String query = 
					"SELECT p FROM Payment p left join p.employee e WHERE p.id>0 ";
			
			if (employeeId!=null && employeeId>0) {
				query+=" and e.id=:employeeId";
			}
			
			if (date!=null) {
				query+=" and p.date between :dateStart and :dateEnd ";
			}
			
			TypedQuery<Payment> q = em.createQuery(
					query, Payment.class);
			
			if (employeeId!=null && employeeId>0) {
				q.setParameter("employeeId", employeeId);
			}
			
			if (date!=null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("dateStart", sdf2.parse(sdf.format(date)+" 00:00:00"));
				q.setParameter("dateEnd", sdf2.parse(sdf.format(date)+" 23:59:59"));
			}
			
			List<Payment> payments = q.getResultList();

			return payments;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	


}
