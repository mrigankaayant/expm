package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ayantsoft.expm.hibernate.pojo.ExpenseDocuments;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class ExpenseDocumentsDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5775301014258869459L;
	
	
	public void save(ExpenseDocuments expenseDocuments){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(expenseDocuments);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}

}
