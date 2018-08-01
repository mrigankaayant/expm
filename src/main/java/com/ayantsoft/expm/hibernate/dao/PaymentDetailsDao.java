package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.ayantsoft.expm.dto.PaymentDetailsDto;
import com.ayantsoft.expm.hibernate.pojo.PaymentDetail;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ViewScoped
public class PaymentDetailsDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2514930761056781284L;
	
	
	public void save(PaymentDetail paymentDetails){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(paymentDetails);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	

	public List<PaymentDetail> findPaymentListByExpenseId(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PaymentDetail> paymentDetailList = null;
		try{
			String hql = "FROM PaymentDetail p "
					   + "JOIN FETCH p.expense exp "
					   + "JOIN FETCH p.userByEnrtyBy up "
					   + "LEFT OUTER JOIN FETCH p.paymentsDocumentses pdoc "
					   + "LEFT OUTER JOIN FETCH p.cardBooks cd "
					   + "LEFT OUTER JOIN FETCH cd.bankInternalCard bankInternalCard "
					   + "LEFT OUTER JOIN FETCH bankInternalCard.bankInternal bi "
					   + "WHERE exp.expenseId = :expId";
			
			Query query = session.createQuery(hql);
			query.setParameter("expId", expenseId);
			paymentDetailList = query.list();
			
			
		}catch(HibernateException he){
			he.printStackTrace();
		}finally{
			session.close();
		}

		return paymentDetailList;
	}
	
	
	
	public double findTotalPaidAmountByExpenseId(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		double totalAmount = 0.0;
		try{
			
			Criteria criteria = session.createCriteria(PaymentDetail.class,"pd");
			criteria.createAlias("pd.expense", "expense");
			criteria.add(Restrictions.isNull("pd.isCancel"));
			criteria.setProjection(Projections.sum("pd.amount"));
			criteria.add(Restrictions.eq("expense.expenseId", expenseId));
			BigDecimal result = (BigDecimal) criteria.uniqueResult();
			if(result != null){
				totalAmount = result.doubleValue();
			}
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return totalAmount;
	}
	
	
	public boolean checkPaymentByVendorId(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean isPayment = false;
		try{
			Criteria criteria = session.createCriteria(PaymentDetail.class,"pd");
			criteria.add(Restrictions.eq("pd.expense.expenseId",expenseId));
			List<PaymentDetail> results = criteria.list();
	        if(results != null && results.size() >0){
	        	isPayment = true;
	        }
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return isPayment;
	}
	
	
	
	public PaymentDetail findPaymentDetailsByPaymentId(Integer paymentDetailsId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		PaymentDetail paymentDetail = null;
		try{
			String hql = "FROM PaymentDetail p "
					   + "JOIN FETCH p.userByEnrtyBy uentry "
					   + "JOIN FETCH p.expense exp "
					   + "JOIN FETCH exp.account acc "
					   + "LEFT OUTER JOIN FETCH exp.userByApprovedBy uapproved "
					   + "JOIN FETCH exp.vendor v "
					   + "LEFT OUTER JOIN FETCH p.paymentsDocumentses pdoc "
					   + "WHERE p.paymentId = :pId";
			
			Query query = session.createQuery(hql);
			query.setParameter("pId",paymentDetailsId);
			paymentDetail = (PaymentDetail) query.uniqueResult();
			
		}catch(HibernateException he){
			he.printStackTrace();
		}finally{
			session.close();
		}
		return paymentDetail;
	}
    
    
    
    public List<PaymentDetailsDto> findPaymentDetailsByAccountId(int accountId,Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PaymentDetailsDto> results = null;
		try{
			String hql = "SELECT pd.entryDate as entryDate,pd.modeOfPayment as modeOfPayment,pd.amount as amount,"
					   + "userEntry.username as username,pd.description as description,userEntry.name as name "
					   + "FROM PaymentDetail pd "
					   + "INNER JOIN pd.userByEnrtyBy userEntry "
					   + "INNER JOIN pd.expense ex "
					   + "INNER JOIN ex.account acc "
					   + "WHERE acc.accountId = :accId "
					   + "AND pd.entryDate BETWEEN :sDate and :eDate "
					   + "AND (pd.isCancel is null OR pd.isCancel is false) ";
					    
			Query query = session.createQuery(hql);
			query.setParameter("accId",accountId);
			query.setParameter("sDate",startDate);
			query.setParameter("eDate",endDate);
			query.setResultTransformer(Transformers.aliasToBean(PaymentDetailsDto.class));
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
