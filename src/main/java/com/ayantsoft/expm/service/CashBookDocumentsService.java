package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.CashBookDocumentDao;
import com.ayantsoft.expm.hibernate.pojo.CashDocuments;




@ManagedBean
@ApplicationScoped
public class CashBookDocumentsService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4995392626789820516L;
	
	@ManagedProperty("#{cashBookDocumentDao}")
	private CashBookDocumentDao cashBookDocumentDao;
	
	public void saveCashBookDocuments(CashDocuments cashDocuments){
		cashBookDocumentDao.save(cashDocuments);
	}

	public List<CashDocuments> documentsByCashId(int cashId){
		return cashBookDocumentDao.documentsByCashId(cashId);
	}
	
	public boolean deleteCashDocById(int docId){
		return cashBookDocumentDao.deleteDocumentById(docId);
	}

	
	public CashBookDocumentDao getCashBookDocumentDao() {
		return cashBookDocumentDao;
	}

	public void setCashBookDocumentDao(CashBookDocumentDao cashBookDocumentDao) {
		this.cashBookDocumentDao = cashBookDocumentDao;
	}

	
	
}
