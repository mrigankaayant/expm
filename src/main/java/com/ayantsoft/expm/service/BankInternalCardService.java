package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.BankInternalCardDao;
import com.ayantsoft.expm.hibernate.dao.BankInternalDao;
import com.ayantsoft.expm.hibernate.pojo.BankInternalCard;

@ManagedBean
@ApplicationScoped
public class BankInternalCardService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6612177069155869328L;
	
	@ManagedProperty("#{bankInternalCardDao}")
	private BankInternalCardDao bankInternalCardDao;
	
	
	public List<BankInternalCard> findCardByBankId(int bankId){
		return bankInternalCardDao.findCardByBankId(bankId);
	}
	
	public BankInternalCard findBankInternalCardById(int bankInternalCardId){
		return bankInternalCardDao.findBankInternalCardById(bankInternalCardId);
	}

	public void saveBankInternalCard(BankInternalCard bankInternalCard){
		bankInternalCardDao.save(bankInternalCard);
	}
	
	public List<BankInternalCard> findAllBankInternalCards(){
		return bankInternalCardDao.findAllBankInternalCards();
	}

	public BankInternalCardDao getBankInternalCardDao() {
		return bankInternalCardDao;
	}

	public void setBankInternalCardDao(BankInternalCardDao bankInternalCardDao) {
		this.bankInternalCardDao = bankInternalCardDao;
	}

}
