package com.returnsoft.payment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.returnsoft.payment.entity.User;
import com.returnsoft.payment.exception.UserLoggedNotFoundException;
import com.returnsoft.payment.service.UserService;
import com.returnsoft.util.FacesUtil;
import com.returnsoft.util.SessionBean;

@ManagedBean
@ViewScoped
public class SearchUserController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1907750596868263058L;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	private String username;
	
	private String names;
	
	private List<User> users;
	
	private User userSelected;
	
	@EJB
	private UserService userService;
	
	public String initialize(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
		
			
			return null;
			
		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}
	}
	
	public void search(){
		try {
			
			users = userService.findList(username, names);
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void edit() {
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() < 1) {
				throw new UserLoggedNotFoundException();
			}
			
			System.out.println("Ingreso a edit");
			
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("resizable", true);
			options.put("contentHeight", 250);
			options.put("contentWidth", 450);

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(userSelected.getId()));
			paramMap.put("userId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_user", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			User userReturn = (User) event.getObject();
			
			int i = 0;
			for (User user : users) {
				//Sale sale = sales.get(i);
				if (user.getId().equals(userReturn.getId())) {
					users.set(i, userReturn);
					userSelected = userReturn;
					break;
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
		
	}
	


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
	}
	
	

}
