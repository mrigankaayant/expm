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

import com.ayantsoft.expm.hibernate.pojo.PaymentsDocuments;
import com.ayantsoft.expm.hibernate.pojo.VendorDocuments;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class VendorDocumentsDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1750068524981602199L;
	
	
	public void save(VendorDocuments vendorDocuments){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(vendorDocuments);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public List<VendorDocuments> documentsByVendorId(int vendorId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<VendorDocuments> documents = null;
		try{
			
			Criteria criteria = session.createCriteria(VendorDocuments.class,"vd");
			criteria.add(Restrictions.eq("vd.vendor.venderId",vendorId));
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
					   + "FROM VendorDocuments v "
					   + "WHERE v.id = :vId ";
			Query query = session.createQuery(hql);
			query.setParameter("vId",documentId);
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
