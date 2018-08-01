package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ayantsoft.expm.hibernate.pojo.Vendor;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class VendorDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7752499194927327799L;


	public void save(Vendor vendor){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(vendor);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}


	public List<Vendor> findVendorList(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Vendor> vendorList = null;
		try{
			Criteria criteria = session.createCriteria(Vendor.class,"v");
			criteria.addOrder(Order.asc("v.venderName"));
			vendorList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return vendorList;
	}



	public Vendor findVendorById(int vendorId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Vendor vendor = null;
		try{
			String hql = "FROM Vendor v "
					   + "LEFT OUTER JOIN FETCH v.banks bank "
					   + "LEFT OUTER JOIN FETCH v.vendorDocumentses vendorDoc "
					   + "where v.venderId = :vID";
			Query query = session.createQuery(hql);
			query.setParameter("vID",vendorId);
			vendor = (Vendor) query.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return vendor;
	} 

}
