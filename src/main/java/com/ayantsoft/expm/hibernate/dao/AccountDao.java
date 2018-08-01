package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.expm.hibernate.pojo.Account;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class AccountDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1574083814474100281L;


	public void save(Account account){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(account);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}



	public List<Account> findAccountList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Account> accountList = null;
		try{
			Criteria criteria = session.createCriteria(Account.class,"ac");
			criteria.addOrder(Order.asc("ac.accountName"));
			accountList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return accountList;
	}


	public Account findAccountById(int accountId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Account account = null;
		try{
			Criteria criteria = session.createCriteria(Account.class,"acc");
			criteria.add(Restrictions.eq("acc.accountId",accountId));
			account = (Account) criteria.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return account;
	}




}
