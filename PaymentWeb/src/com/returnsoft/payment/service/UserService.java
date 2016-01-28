package com.returnsoft.payment.service;

import java.util.List;

import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.exception.ServiceException;

public interface UserService {

	public User findById(Integer idUser) throws ServiceException;

	public User doLogin(String username, String password) throws ServiceException;

	public void add(User user) throws ServiceException;

	public User edit(User user) throws ServiceException;
	
	public List<User> findList(String username, String names) throws ServiceException;

}
