package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.expm.hibernate.pojo.BankInternal;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class BankInternalDao implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 1844464062847552343L;
	
	public void save(BankInternal bankInternal){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(bankInternal);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}	
	}
	
	
	public List<BankInternal> findBankList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BankInternal> bankInternals = null;
		try{
			
			Criteria criteria = session.createCriteria(BankInternal.class);
			bankInternals = criteria.list();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return bankInternals;
	}
	
	
	public BankInternal findBankInternalById(int id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BankInternal bankInternal = null;
		try{
			Criteria criteria = session.createCriteria(BankInternal.class,"b");
			criteria.add(Restrictions.eq("b.id",id));
			bankInternal = (BankInternal) criteria.uniqueResult();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return bankInternal;
	}

}
