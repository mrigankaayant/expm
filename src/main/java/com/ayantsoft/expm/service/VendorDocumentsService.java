package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.PaymentDocumentsDao;
import com.ayantsoft.expm.hibernate.dao.VendorDocumentsDao;
import com.ayantsoft.expm.hibernate.pojo.VendorDocuments;

@ManagedBean
@ApplicationScoped
public class VendorDocumentsService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7168486742850775587L;
	
	
	@ManagedProperty("#{vendorDocumentsDao}")
	private VendorDocumentsDao vendorDocumentsDao;
	
	
	public void saveVendorDocuments(VendorDocuments vendorDocuments){
		vendorDocumentsDao.save(vendorDocuments);
	}
	
	
	public List<VendorDocuments> documentsByVendorId(int vendorId){
		return vendorDocumentsDao.documentsByVendorId(vendorId);
	}
	
	
	public boolean deleteVendorDocById(int docId){
		return vendorDocumentsDao.deleteDocumentById(docId);
	}


	public VendorDocumentsDao getVendorDocumentsDao() {
		return vendorDocumentsDao;
	}


	public void setVendorDocumentsDao(VendorDocumentsDao vendorDocumentsDao) {
		this.vendorDocumentsDao = vendorDocumentsDao;
	}
}
