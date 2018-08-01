package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.AccountDao;
import com.ayantsoft.expm.hibernate.dao.BankDao;
import com.ayantsoft.expm.hibernate.pojo.Account;
import com.ayantsoft.expm.hibernate.pojo.Bank;

@ManagedBean
@ApplicationScoped
public class BankService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4777830943814224949L;
	
	
	@ManagedProperty("#{bankDao}")
	private BankDao bankDao;
	
	
	public void saveBank(Bank bank){
		bankDao.save(bank);
	}
	
	public List<Bank> getBankList(){
		return bankDao.findBankList();
	}
	
	public Bank getBankById(int bankId){
		return bankDao.findBankById(bankId);
	}
	
    public List<Bank> getBankListByVendorId(int vendorId){
    	return bankDao.findBankListByVendorId(vendorId);
    }
    
    public Bank findPrimaryBankByVendorId(int vendorId){
    	return bankDao.findPrimaryBankByVendorId(vendorId);
    }
    
    public void deleteBankByVendorId(Bank bank){
    	bankDao.deleteBankByVendorId(bank);
    }
    
    
    public boolean findPrimaryAccountByVendorId(int vendorId){
    	return bankDao.findPrimaryAccountByVendorId(vendorId);
    }

	
	//setter and getter
	

	public BankDao getBankDao() {
		return bankDao;
	}


	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

}
