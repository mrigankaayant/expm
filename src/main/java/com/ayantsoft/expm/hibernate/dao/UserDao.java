package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.ayantsoft.expm.hibernate.pojo.User;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class UserDao implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3055546967943904130L;
	
	public List<User> findAllUser(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> users = null;
		try{
			
			Criteria criteria = session.createCriteria(User.class);
			users = criteria.list();
		}catch(HibernateException he ){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return users;
	}

}
