package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.expm.hibernate.pojo.User;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;


@ManagedBean
@ApplicationScoped
public class LoginDao implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4811211197268535815L;
	
	
	public User loginfromDb(String username, String password){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = null;
		try{
			String hql ="from User u join fetch u.roles r where u.username = :username and u.password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("password", password);
			user = (User)query.uniqueResult();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally {
			session.close();
		}
		
		return user;
	}
	
	

}