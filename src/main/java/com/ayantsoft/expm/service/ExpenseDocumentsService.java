package com.ayantsoft.expm.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.ExpenseDocumentsDao;
import com.ayantsoft.expm.hibernate.pojo.ExpenseDocuments;

@ManagedBean
@ApplicationScoped
public class ExpenseDocumentsService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8164968055296087329L;
	
	
	@ManagedProperty("#{expenseDocumentsDao}")
	private ExpenseDocumentsDao expenseDocumentsDao;
	
	
	public void saveExpenseDocuments(ExpenseDocuments expenseDocuments){
		expenseDocumentsDao.save(expenseDocuments);
	}


	public ExpenseDocumentsDao getExpenseDocumentsDao() {
		return expenseDocumentsDao;
	}


	public void setExpenseDocumentsDao(ExpenseDocumentsDao expenseDocumentsDao) {
		this.expenseDocumentsDao = expenseDocumentsDao;
	}
}
