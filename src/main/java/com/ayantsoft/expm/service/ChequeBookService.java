package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.dto.ChequeBookDto;
import com.ayantsoft.expm.hibernate.dao.ChequeBookDao;
import com.ayantsoft.expm.hibernate.pojo.ChequeBook;

@ManagedBean
@ApplicationScoped
public class ChequeBookService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2292491374067922379L;
	
	
	@ManagedProperty("#{chequeBookDao}")
	private ChequeBookDao chequeBookDao;
	
	
	public void saveChequeBook(ChequeBook chequeBook){
		chequeBookDao.save(chequeBook);
	}
	
	public List<ChequeBookDto> findChequeBookDetailsByDateRange(Date startDate,Date endDate){
		return chequeBookDao.findChequeBookDetailsByDateRange(startDate, endDate);
	}

	public ChequeBookDao getChequeBookDao() {
		return chequeBookDao;
	}


	public void setChequeBookDao(ChequeBookDao chequeBookDao) {
		this.chequeBookDao = chequeBookDao;
	}
}
