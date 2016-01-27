package com.returnsoft.payment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.exception.EaoException;


@Stateless
public class UserEao{
	
	@PersistenceContext
	private EntityManager em;
	
	public void add(User user) throws EaoException {
		try {
			
			em.persist(user);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public User edit(User user) throws EaoException {
		try {
			
			user = em.merge(user);
			em.flush();
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	public User findById(Integer userId) throws EaoException {
		try {
			User user = em.find(User.class, userId);
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	
	
	public User findByDocumentNumber(String documentNumber) throws EaoException {
		try {
			TypedQuery<User> q = em
					.createQuery(
							"SELECT u FROM User u join u.person p WHERE p.documentNumber = :documentNumber",
							User.class);
			q.setParameter("documentNumber", documentNumber);
			User user = q.getSingleResult();

			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public User findByUsername(String username) throws EaoException {
		try {
			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.login = :username", User.class);
			q.setParameter("username", username);
			User user = q.getSingleResult();

			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	
	public List<User> findList(String username, String names) throws EaoException {
		try {
			
			String query = 
					"SELECT u FROM User u WHERE u.id>0 ";
			
			if (username!=null && username.length()>0) {
				query+=" and u.username=:username";
			}
			
			if (names!=null && names.length()>0) {
				query+=" and (u.firstname like :names or u.lastname like :names) ";
			}
			
			TypedQuery<User> q = em.createQuery(
					query, User.class);
			if (username!=null && username.length()>0) {
				q.setParameter("username", username);
			}
			
			if (names!=null && names.length()>0) {
				q.setParameter("names", "%"+names+"%");
			}
			
			List<User> users = q.getResultList();

			return users;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}



	
}
