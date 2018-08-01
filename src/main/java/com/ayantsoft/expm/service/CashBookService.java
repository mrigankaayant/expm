package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.dto.CashBookDto;
import com.ayantsoft.expm.hibernate.dao.CashBookDao;
import com.ayantsoft.expm.hibernate.pojo.CashBook;

@ManagedBean
@ApplicationScoped
public class CashBookService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5568927881597908996L;
	
	@ManagedProperty("#{cashBookDao}")
	private CashBookDao cashBookDao;
	
	
	public void saveCashBook(CashBook cashBook){
		cashBookDao.save(cashBook);
	}
	
	public List<CashBook> getCashBookList(){
		return cashBookDao.findCashBookList();
	}
	
	public CashBook getCashBookById(int txnId){
		return cashBookDao.findCashBooktById(txnId);
	}
	
	public BigDecimal getTotalCredit(){
		return cashBookDao.findTotalCredit();
	}

	public BigDecimal getTotalDebit(){
		return cashBookDao.findTotalDebit();
	}
	
	public List<CashBookDto> findCashBookDetailsByDateRange(Date startDate,Date endDate){
		return cashBookDao.findCashBookDetailsByDateRange(startDate, endDate);
	}
	
	public BigDecimal findTotalCreditByDateRange(Date startDate){
		return cashBookDao.findTotalCreditByDateRange(startDate);
	}

	public BigDecimal findTotalDebitByDateRange(Date startDate){
		return cashBookDao.findTotalDebitByDateRange(startDate);
	}

	public CashBookDao getCashBookDao() {
		return cashBookDao;
	}

	public void setCashBookDao(CashBookDao cashBookDao) {
		this.cashBookDao = cashBookDao;
	}

}
