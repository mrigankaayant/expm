package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ayantsoft.expm.dto.PaymentDetailsDto;
import com.ayantsoft.expm.hibernate.dao.PaymentDetailsDao;
import com.ayantsoft.expm.hibernate.pojo.PaymentDetail;

@ManagedBean
@ViewScoped
public class PaymentDetailsService implements Serializable{

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4999777132909792363L;
	
	
	@ManagedProperty("#{paymentDetailsDao}")
	private PaymentDetailsDao paymentDetailsDao;
	
	
	public List<PaymentDetail> findPaymentListByExpenseId(int expenseId){
		return paymentDetailsDao.findPaymentListByExpenseId(expenseId);
	}
	
	public void save(PaymentDetail paymentDetails){
		paymentDetailsDao.save(paymentDetails);
	}
	
	
	public double findTotalPaidAmountByExpenseId(int expenseId){
		return paymentDetailsDao.findTotalPaidAmountByExpenseId(expenseId);
	}
	
	
	public boolean checkPaymentByVendorId(int expenseId){
		return paymentDetailsDao.checkPaymentByVendorId(expenseId);
	}
	
	public PaymentDetail findPaymentDetailsByPaymentId(Integer paymentDetailsId){
		return paymentDetailsDao.findPaymentDetailsByPaymentId(paymentDetailsId); 
	}
	
	
	public List<PaymentDetailsDto> findPaymentDetailsByAccountId(int accountId,Date startDate,Date endDate){
		return paymentDetailsDao.findPaymentDetailsByAccountId(accountId, startDate, endDate);
	}
	

	public PaymentDetailsDao getPaymentDetailsDao() {
		return paymentDetailsDao;
	}

	public void setPaymentDetailsDao(PaymentDetailsDao paymentDetailsDao) {
		this.paymentDetailsDao = paymentDetailsDao;
	}

}
