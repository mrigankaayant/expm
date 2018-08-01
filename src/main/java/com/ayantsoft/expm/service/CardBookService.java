package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.dto.CardBookDto;
import com.ayantsoft.expm.hibernate.dao.CardBookDao;
import com.ayantsoft.expm.hibernate.pojo.CardBook;

@ManagedBean
@ApplicationScoped
public class CardBookService implements Serializable{

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -7609532482416888408L;
	
	@ManagedProperty("#{cardBookDao}")
	private CardBookDao cardBookDao;
	
	
	public void saveCardBook(CardBook cardBook){
		cardBookDao.save(cardBook);
	}
	
	public List<CardBookDto> findCardBookDetailsByDateRange(Date startDate,Date endDate){
		return cardBookDao.findCardBookDetailsByDateRange(startDate, endDate);
	}


	public CardBookDao getCardBookDao() {
		return cardBookDao;
	}

	public void setCardBookDao(CardBookDao cardBookDao) {
		this.cardBookDao = cardBookDao;
	}

}
