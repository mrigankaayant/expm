package com.ayantsoft.expm.jsf.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ayantsoft.expm.hibernate.pojo.Role;
import com.ayantsoft.expm.hibernate.pojo.User;
import com.ayantsoft.expm.service.LoginService;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5193516905082013677L;
	
	
	private String username;
	private String password;
	private boolean admin;
	private boolean loginCheck;
	private User user;

	@ManagedProperty("#{loginService}")
	private LoginService loginService;

	public String login(){
		admin = false;

		user = loginService.login(username, password);

		if(user != null){
			if(hasRole("ADMIN")){
				admin=true;
			}
		}

		if(user != null){
			loginCheck = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,"Sucess : ", "Welcome, " + user.getUsername()  +"Login Success"));
			if(hasRole("ACCOUNT")){
				return "/account/account.xhtml?faces-redirect=true&i=0";
			}else if(hasRole("ADMIN")){
				return "/admin/admin.xhtml?faces-redirect=true&i=1";
			}else{
				return null;
			}
		}else{
			loginCheck = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error:", "Login Failed, Username Or Password Incorrect."));
			return null;
		}
	}

	public boolean hasRole(String role){
		for(Role r: user.getRoles()){
			if(r.getRoleName().equals(role)){
				return true;
			}
		}
		return false;
	}


	public String logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.clear();
		user = null;
		loginCheck = false;
		return "/login.xhtml?faces-redirect=true";
	}
	
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
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

	public boolean getLoginCheck() {
		return loginCheck;
	}
	public void setLoginCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}