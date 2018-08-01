package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.ayantsoft.expm.hibernate.pojo.PaymentsDocuments;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class PaymentDocumentsDao implements Serializable{

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -5396811222268784028L;
	
	
	public void save(PaymentsDocuments paymentsDocuments){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(paymentsDocuments);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}

}
