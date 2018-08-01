package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.ayantsoft.expm.hibernate.dao.ChequeNoDao;
import com.ayantsoft.expm.hibernate.pojo.ChequeNo;

@ManagedBean
@ApplicationScoped
public class ChequeNoService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8601041225454726155L;
	
	@ManagedProperty("#{chequeNoDao}")
	private ChequeNoDao chequeNoDao;
	
	public void saveChequeNos(List<ChequeNo> chequeNos){
		chequeNoDao.save(chequeNos);
	}
	
	public List<ChequeNo> findChequeList(){
		return chequeNoDao.findChequeList();
	}
	
	public void save(ChequeNo chequeNo){
		chequeNoDao.save(chequeNo);
	}
	
	
	public List<ChequeNo> findChequeListByBankId(int bankId){
		return chequeNoDao.findChequeListByBankId(bankId);
	}
	
	
	public ChequeNo findChequeNoById(String checkNo){
		return chequeNoDao.findChequeNoById(checkNo);
	}

	public ChequeNoDao getChequeNoDao() {
		return chequeNoDao;
	}

	public void setChequeNoDao(ChequeNoDao chequeNoDao) {
		this.chequeNoDao = chequeNoDao;
	}

}
