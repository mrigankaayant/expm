package com.ayantsoft.expm.service;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.PaymentDocumentsDao;
import com.ayantsoft.expm.hibernate.pojo.PaymentsDocuments;

@ManagedBean
@ApplicationScoped
public class PaymentDocumentsService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3001958729722408170L;
	
	@ManagedProperty("#{paymentDocumentsDao}")
	private PaymentDocumentsDao paymentDocumentsDao;
	
	public void savePaymentDocuments(PaymentsDocuments paymentsDocuments){
		paymentDocumentsDao.save(paymentsDocuments);
	}

	public PaymentDocumentsDao getPaymentDocumentsDao() {
		return paymentDocumentsDao;
	}

	public void setPaymentDocumentsDao(PaymentDocumentsDao paymentDocumentsDao) {
		this.paymentDocumentsDao = paymentDocumentsDao;
	}

}
