package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.expm.hibernate.pojo.CashDocuments;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;
@ManagedBean
@ApplicationScoped
public class CashBookDocumentDao implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8824493444690173621L;

	public void save(CashDocuments cashDocuments){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(cashDocuments);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	public List<CashDocuments> documentsByCashId(int cashId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CashDocuments> documents = null;
		try{
			
			Criteria criteria = session.createCriteria(CashDocuments.class,"cd");
			criteria.add(Restrictions.eq("cd.cashBook.txnId",cashId));
			documents = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return documents;
	}
	
	public boolean deleteDocumentById(int documentId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean isDelete = false;
		try{
			session.beginTransaction();
			String hql = "DELETE "
					   + "FROM CashDocuments c "
					   + "WHERE c.id = :cId ";
			Query query = session.createQuery(hql);
			query.setParameter("cId",documentId);
			query.executeUpdate();
			session.getTransaction().commit();
			isDelete = true;
		}catch(HibernateException he){
			throw he;
		}finally{
			session.close();
		}
		
		return isDelete;
	}
}
