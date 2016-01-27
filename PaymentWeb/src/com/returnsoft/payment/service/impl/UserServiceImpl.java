package com.returnsoft.payment.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.returnsoft.payment.eao.UserEao;
import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.exception.ServiceException;
import com.returnsoft.payment.exception.UserInactiveException;
import com.returnsoft.payment.exception.UserNotFoundException;
import com.returnsoft.payment.exception.UserWrongPasswordException;
import com.returnsoft.payment.service.UserService;

@Stateless
public class UserServiceImpl implements UserService {
	
	@EJB
	private UserEao userEao;
	
	@Override
	public User findById(Integer userId)
			throws ServiceException {
		try {
			User user = userEao.findById(userId);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}
	

	@Override
	public User doLogin(String username, String password) throws ServiceException {
		try {

			User user = userEao.findByUsername(username);

			if (user==null) {
				throw new UserNotFoundException();
			}
			
			if (!user.getPassword().equals(password)) {
				throw new UserWrongPasswordException();
			}
			
			if (user.getIsActive().equals(false)) {
				throw new UserInactiveException();
			}

			

			return user;

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage()!=null && e.getMessage().trim().length()>0) {
				throw new ServiceException(e.getMessage(), e);	
			}else{
				throw new ServiceException();
			}
		}
	}

}
