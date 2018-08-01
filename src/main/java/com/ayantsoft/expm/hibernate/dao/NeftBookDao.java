package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.primefaces.model.SortOrder;

import com.ayantsoft.expm.dto.CardBookDto;
import com.ayantsoft.expm.dto.NeftBookDto;
import com.ayantsoft.expm.hibernate.pojo.Expense;
import com.ayantsoft.expm.hibernate.pojo.NeftBook;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class NeftBookDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2922882245452650044L;

	public void save(NeftBook neftBook){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(neftBook);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}
	
	
	
	
	public List<NeftBookDto> findNeftBookDetailsByDateRange(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<NeftBookDto> results = null;
		try{
			String hql = "SELECT neftBook.txnId as txnId, "
					   + "pd.paymentId as paymentId, "
					   + "entryby.username as username, "
					   + "neftBook.payeeName as payeeName, "
					   + "neftBook.payeeBankName as payeeBankName, "
					   + "neftBook.payeeBankIfsc as payeeBankIfsc, "
					   + "neftBook.payeeBankBranch as payeeBankBranch, "
					   + "neftBook.amount as amount, "
					   + "neftBook.neftStatus as neftStatus, "
					   + "neftBook.neftDate as neftDate, "
					   + "neftBook.entryDate as entryDate "
					   + "FROM NeftBook neftBook "
					   + "INNER JOIN neftBook.userByEntryBy entryby "
					   + "LEFT OUTER JOIN neftBook.paymentDetail pd "
					   + "WHERE (pd.modeOfPayment is 'NEFT' and (pd.isCancel is null or pd.isCancel is false) and (neftBook.entryDate  BETWEEN :sDate and :eDate)) ";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			query.setParameter("eDate",endDate);
			query.setResultTransformer(Transformers.aliasToBean(NeftBookDto.class));
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
