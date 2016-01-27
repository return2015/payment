package com.returnsoft.payment.service;

import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.exception.ServiceException;

public interface UserService {
	
	public User findById(Integer idUser) throws ServiceException;

	public User doLogin(String username, String password) throws ServiceException;

}
