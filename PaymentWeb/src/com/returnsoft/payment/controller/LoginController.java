package com.returnsoft.payment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.service.UserService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;



@Named
@RequestScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	@EJB
	private UserService userService;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;

	public LoginController() {
		
		facesUtil = new FacesUtil();
	}

	public String doLogin() {

		try {
			
			// BUSCA USUARIO Y CLAVE
			User user = userService.doLogin(username, password);
			
		
			
			sessionBean.setUser(user);

			return "home?faces-redirect=true";


		} catch (Exception e) {

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}

	}

	public String doLogout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();

			return "login?faces-redirect=true";
		} catch (Exception e) {

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			
			return "login?faces-redirect=true";
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
