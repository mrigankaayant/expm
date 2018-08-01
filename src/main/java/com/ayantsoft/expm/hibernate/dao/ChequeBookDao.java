package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.ayantsoft.expm.dto.ChequeBookDto;
import com.ayantsoft.expm.dto.NeftBookDto;
import com.ayantsoft.expm.hibernate.pojo.ChequeBook;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class ChequeBookDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2541604440791820561L;
	
	

	public void save(ChequeBook chequeBook){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(chequeBook);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public List<ChequeBookDto> findChequeBookDetailsByDateRange(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ChequeBookDto> results = null;
		try{
			String hql = "SELECT chequeBook.chequeNo as chequeNo, "
					   + "pd.paymentId as paymentId, "
					   + "u.username as username, "
					   + "chequeBook.payeeName as payeeName, "
					   + "chequeBook.payableDate as payableDate, "
					   + "chequeBook.amount as amount, "
					   + "chequeBook.isAccountPayable as isAccountPayable, "
					   + "chequeBook.chequeStatus as chequeStatus, "
					   + "chequeBook.entryDate as entryDate "
					   + "FROM ChequeBook chequeBook "
					   + "INNER JOIN chequeBook.user u "
					   + "LEFT OUTER JOIN chequeBook.paymentDetail pd "
					   + "WHERE (pd.modeOfPayment is 'CHEQUE' and (pd.isCancel is null or pd.isCancel is false) and (chequeBook.entryDate  BETWEEN :sDate and :eDate)) ";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			query.setParameter("eDate",endDate);
			query.setResultTransformer(Transformers.aliasToBean(ChequeBookDto.class));
			results = query.list();
			
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return results;
	}

}
