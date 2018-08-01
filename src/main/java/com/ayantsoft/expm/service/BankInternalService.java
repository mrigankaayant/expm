package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.BankInternalDao;
import com.ayantsoft.expm.hibernate.pojo.BankInternal;

@ManagedBean
@ApplicationScoped
public class BankInternalService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5507038966907158220L;
	
	@ManagedProperty("#{bankInternalDao}")
	private BankInternalDao bankInternalDao;
	
	
	public void saveBankInternal(BankInternal bankInternal){
		bankInternalDao.save(bankInternal);
	}
	
	public List<BankInternal> findBankList(){
		return bankInternalDao.findBankList();
	}
	
	public BankInternal findBankInternalById(int id){
		return bankInternalDao.findBankInternalById(id);
	}
	

	public BankInternalDao getBankInternalDao() {
		return bankInternalDao;
	}

	public void setBankInternalDao(BankInternalDao bankInternalDao) {
		this.bankInternalDao = bankInternalDao;
	}

}
