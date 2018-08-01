package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.ayantsoft.expm.hibernate.pojo.BankInternalCard;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class BankInternalCardDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5393954551979696340L;
	
	public List<BankInternalCard> findCardByBankId(int bankId){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BankInternalCard> bankInternalCards = null;
		try{
			Criteria criteria = session.createCriteria(BankInternalCard.class,"b");
			criteria.createAlias("b.bankInternal","bi");
			criteria.add(Restrictions.eq("bi.id",bankId));
			bankInternalCards = criteria.list();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return bankInternalCards;
		
	}
	
	
	public BankInternalCard findBankInternalCardById(int bankInternalCardId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		BankInternalCard bankInternalCard = null;
		try{
			Criteria criteria = session.createCriteria(BankInternalCard.class,"b");
			criteria.createAlias("b.bankInternal","bi");
			criteria.add(Restrictions.eq("b.id",bankInternalCardId));
			bankInternalCard = (BankInternalCard) criteria.uniqueResult();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return bankInternalCard;
	}
	
	
	
	public void save(BankInternalCard bankInternalCard){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(bankInternalCard);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public List<BankInternalCard> findAllBankInternalCards(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<BankInternalCard> bankInternalCards = null;
		try{
			Criteria criteria = session.createCriteria(BankInternalCard.class,"b");
			criteria.createAlias("b.bankInternal","bi");
			bankInternalCards = criteria.list();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return bankInternalCards;
	}
	

}
