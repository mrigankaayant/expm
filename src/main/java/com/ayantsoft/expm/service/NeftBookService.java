package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.expm.dto.NeftBookDto;
import com.ayantsoft.expm.hibernate.dao.NeftBookDao;
import com.ayantsoft.expm.hibernate.pojo.NeftBook;

@ManagedBean
@ApplicationScoped
public class NeftBookService implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 8353760598085124227L;
	
	@ManagedProperty("#{neftBookDao}")
	private NeftBookDao neftBookDao;
	
	
	public void saveNeftBook(NeftBook neftBook){
		neftBookDao.save(neftBook);
	}
	
	public List<NeftBookDto> findNeftBookDetailsByDateRange(Date startDate,Date endDate){
		return neftBookDao.findNeftBookDetailsByDateRange(startDate, endDate);
	}
	
	public NeftBookDao getNeftBookDao() {
		return neftBookDao;
	}

	public void setNeftBookDao(NeftBookDao neftBookDao) {
		this.neftBookDao = neftBookDao;
	}
	
	

}
