package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.AccountDao;
import com.ayantsoft.expm.hibernate.pojo.Account;


@ManagedBean
@ApplicationScoped
public class AccountService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8165420935395677877L;
	
	
	@ManagedProperty("#{accountDao}")
	private AccountDao accountDao;

	
	public void saveAccount(Account account){
		accountDao.save(account);
	}
	
	public List<Account> getAccountList(){
		return accountDao.findAccountList();
	}
	
	public Account getAccountById(int accountId){
		return accountDao.findAccountById(accountId);
	}

	
	// setter and getter
	
	public AccountDao getAccountDao() {
		return accountDao;
	}


	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

}
