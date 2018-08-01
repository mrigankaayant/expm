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

import com.ayantsoft.expm.dto.CardBookDto;
import com.ayantsoft.expm.hibernate.pojo.CardBook;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CardBookDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 2967417125096777189L;
	
	
	public void save(CardBook cardBook){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(cardBook);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}
	
	
	public List<CardBookDto> findCardBookDetailsByDateRange(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CardBookDto> results = null;
		try{
			String hql = "SELECT cardBook.txnId as txnId, "
					   + "pd.paymentId as paymentDetailsId, "
					   + "u.username as username, "
					   + "cardBook.amount as amount, "
					   + "cardBook.cardSwipedDate as cardSwipedDate, "
					   + "cardBook.cardSwipedBy as cardSwipedBy, "
					   + "cardBook.entryDate as entryDate, "
					   + "bic.cardNo as cardNo, "
					   + "bic.cardHolderName as cardHolderName, "
					   + "bic.cardType as cardType, "
					   + "bi.bankName as bankName, "
					   + "bi.accountNo as accountNo "
					   
					   + "FROM CardBook cardBook "
					   + "INNER JOIN cardBook.bankInternalCard bic "
					   + "INNER JOIN bic.bankInternal bi "
					   + "INNER JOIN cardBook.user u "
					   + "LEFT OUTER JOIN cardBook.paymentDetail pd "
					   + "WHERE (pd.modeOfPayment is 'CARD' and (pd.isCancel is null or pd.isCancel is false) and (cardBook.entryDate  BETWEEN :sDate and :eDate)) ";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			query.setParameter("eDate",endDate);
			query.setResultTransformer(Transformers.aliasToBean(CardBookDto.class));
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
