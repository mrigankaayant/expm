package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

import com.ayantsoft.expm.dto.CashBookDto;
import com.ayantsoft.expm.hibernate.pojo.CashBook;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class CashBookDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3878546794439362914L;
	
	
	public void save(CashBook cashBook){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(cashBook);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}
	
	
	public List<CashBook> findCashBookList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CashBook> cashBookList = null;
		try{
			Criteria criteria = session.createCriteria(CashBook.class,"cb");
			criteria.createAlias("cb.user", "u");
			criteria.add(Restrictions.like("cb.txnType", "DEBIT"));
			cashBookList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return cashBookList;
	}


	public CashBook findCashBooktById(int txnId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		CashBook cashBook = null;
		try{
			Criteria criteria = session.createCriteria(CashBook.class,"cb");
			criteria.createAlias("cb.cashDocumentses", "cd",JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.eq("cb.txnId",txnId));
			cashBook = (CashBook) criteria.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return cashBook;
	}
	
	
	public BigDecimal findTotalCredit(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalCredit = null;
		try{
			String hql = "SELECT sum(cashBook.credit) "
					   + "FROM CashBook cashBook "
					   + "INNER JOIN cashBook.paymentDetail pd "
					   + "WHERE pd.modeOfPayment is 'CASH' and pd.isCancel is null or pd.isCancel is false";
			Query query = session.createQuery(hql);
			totalCredit = (BigDecimal) query.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return totalCredit;
	}
	
	
	public BigDecimal findTotalDebit(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalDebit = null;
		try{
			String hql = "SELECT sum(cashBook.debit) "
					   + "FROM CashBook cashBook ";
			Query query = session.createQuery(hql);
			totalDebit = (BigDecimal) query.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return totalDebit;
	}
	
	
	public List<CashBookDto> findCashBookDetailsByDateRange(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CashBookDto> results = null;
		try{
			String hql = "SELECT cashBook.txnId as txnId, "
					   + "cashBook.description as description, "
					   + "cashBook.txnType as txnType, "
					   + "cashBook.entryDate as entryDate, "
					   + "cashBook.credit as credit, "
					   + "cashBook.debit as debit, "
					   + "pd.paymentId as paymentDetailsId "
					   + "FROM CashBook cashBook "
					   + "LEFT OUTER JOIN cashBook.paymentDetail pd "
					   + "WHERE (pd.modeOfPayment is 'CASH' and (pd.isCancel is null or pd.isCancel is false) and (cashBook.entryDate BETWEEN :sDate and :eDate)) "
					   + "or (cashBook.txnType is 'DEBIT' and (cashBook.entryDate BETWEEN :sDate and :eDate)) ";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			query.setParameter("eDate",endDate);
			query.setResultTransformer(Transformers.aliasToBean(CashBookDto.class));
			results = query.list();
			
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return results;
	}
	
	
	public BigDecimal findTotalCreditByDateRange(Date startDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalCredit = null;
		try{
			String hql = "SELECT sum(cashBook.credit) "
					   + "FROM CashBook cashBook "
					   + "INNER JOIN cashBook.paymentDetail pd "
					   + "WHERE (pd.modeOfPayment is 'CASH' and pd.isCancel is null or pd.isCancel is false) and (cashBook.entryDate < :sDate)";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			totalCredit = (BigDecimal) query.uniqueResult();
			if(totalCredit == null){
				totalCredit = new BigDecimal(0.00);
			}
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return totalCredit;
	}
	
	
	public BigDecimal findTotalDebitByDateRange(Date startDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BigDecimal totalDebit = null;
		try{
			String hql = "SELECT sum(cashBook.debit) "
					   + "FROM CashBook cashBook "
					   + "WHERE cashBook.entryDate < :sDate";
			Query query = session.createQuery(hql);
			query.setParameter("sDate",startDate);
			totalDebit = (BigDecimal) query.uniqueResult();
			if(totalDebit == null){
				totalDebit = new BigDecimal(0.00);
			}
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return totalDebit;
	}
	

}
