package com.ayantsoft.expm.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.LoginDao;
import com.ayantsoft.expm.hibernate.pojo.User;


@ManagedBean
@ApplicationScoped
public class LoginService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2965695704412482786L;
	
	
	@ManagedProperty("#{loginDao}")
	private LoginDao loginDao;
	
	public User login(String username, String password){
		return loginDao.loginfromDb(username, password);
	}
	
	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
}