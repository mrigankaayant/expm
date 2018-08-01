package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.expm.hibernate.pojo.ChequeNo;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class ChequeNoDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7609873380735326278L;
	
	public void save(List<ChequeNo> chequeNos){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			for(ChequeNo chequeNo:chequeNos){
				session.save(chequeNo);
			}
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public void save(ChequeNo chequeNo){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(chequeNo);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public List<ChequeNo> findChequeList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ChequeNo> chequeList = null;
		try{
			
			Criteria criteria = session.createCriteria(ChequeNo.class,"c");
			criteria.createAlias("c.bankInternal", "b");
			chequeList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return chequeList; 
	}
	
	
	public List<ChequeNo> findChequeListByBankId(int bankId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<ChequeNo> chequeList = null;
		try{
			Criteria criteria = session.createCriteria(ChequeNo.class,"c");
			criteria.createAlias("c.bankInternal", "b");
			Criterion c1 = Restrictions.eq("b.id", bankId);
			Criterion c2 = Restrictions.eq("c.isIssued",false);
			criteria.add(Restrictions.and(c1, c2));
			chequeList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return chequeList; 
	}
	
	
	public ChequeNo findChequeNoById(String checkNo){
		Session session = HibernateUtil.getSessionFactory().openSession();
		ChequeNo chequeNo = null;
		try{
			
			Criteria criteria = session.createCriteria(ChequeNo.class,"c");
			criteria.createAlias("c.bankInternal", "b");
			criteria.add(Restrictions.eq("c.chequeNo",checkNo));
			chequeNo = (ChequeNo) criteria.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return chequeNo; 
	}

}
