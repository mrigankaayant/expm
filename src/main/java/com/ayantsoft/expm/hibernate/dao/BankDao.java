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

import com.ayantsoft.expm.hibernate.pojo.Bank;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class BankDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1419753554959051185L;
	
	
	public void save(Bank bank){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(bank);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}


	public List<Bank> findBankList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Bank> bankList = null;
		try{
			Criteria criteria = session.createCriteria(Bank.class,"b");
			criteria.createAlias("b.vendor", "vendor");
			bankList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return bankList;
	}



	public Bank findBankById(int bankId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Bank bank = null;
		try{
			Criteria criteria = session.createCriteria(Bank.class,"b");
			criteria.createAlias("b.vendor", "vendor");
			criteria.add(Restrictions.eq("b.bankId",bankId));
			bank = (Bank) criteria.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return bank;
	} 
	
	
	public List<Bank> findBankListByVendorId(int vendorId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Bank> bankList = null;
		try{
			Criteria criteria = session.createCriteria(Bank.class,"b");
			criteria.add(Restrictions.eq("b.vendor.venderId",vendorId));
			bankList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return bankList;
	}
	
	
	public void deleteBankByVendorId(Bank bank){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.delete(bank);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	
	public boolean findPrimaryAccountByVendorId(int vendorId){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		boolean isExist = false;
		try{
			Criteria criteria = session.createCriteria(Bank.class,"b");
			Criterion c1 = Restrictions.eq("b.vendor.venderId", vendorId);
			Criterion c2 = Restrictions.eq("b.primaryAccount", true);
			criteria.add(Restrictions.and(c1,c2));
			List<Bank> results = criteria.list();
			if(results != null && results.size() >0){
				isExist = true;
			}
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return isExist;
	}
	
	
	public Bank findPrimaryBankByVendorId(int vendorId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Bank bank = null;
		try{
			Criteria criteria = session.createCriteria(Bank.class,"b");
			Criterion c1 = Restrictions.eq("b.vendor.venderId",vendorId);
			Criterion c2 = Restrictions.eq("b.primaryAccount", true);
			criteria.add(Restrictions.and(c1,c2));
			bank = (Bank) criteria.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return bank;
	}


}
