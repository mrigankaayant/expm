package com.ayantsoft.expm.hibernate.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;

import com.ayantsoft.expm.hibernate.pojo.Expense;
import com.ayantsoft.expm.hibernate.util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class ExpenseDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3399035939242724377L;
	
	
	public void save(Expense expense){

		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(expense);
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

	}
	
	
	
	public Object[] expenseFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		
		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try
		{
			Criteria criteria = session.createCriteria(Expense.class,"expense");
			criteria.createAlias("expense.account","account");
			criteria.createAlias("expense.vendor","vendor");
			criteria.createAlias("expense.userByCancelBy","userByCancelBy",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("expense.userByEntryBy","userByEntryBy",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("expense.userByApprovedBy","userByApprovedBy",JoinType.LEFT_OUTER_JOIN);	
			

			if (filters != null) {
				filters.forEach((k,v)->{
					if(k.equals("expenseId")){
						criteria.add(Restrictions.eq(k, Integer.parseInt((String)v)));
					}else if(k.equals("serviceTax")||k.equals("totalAmount")||k.equals("tdsAmount")||k.equals("netAmount")){
						criteria.add(Restrictions.eq(k,new BigDecimal((String)v)));
					}else if(k.equals("isApproved")){
						String val = (String)v;
						if(val.equals("true") || val.equals("false")){
							criteria.add(Restrictions.eq(k,Boolean.parseBoolean((String)v)));
						}
						if(val.equals("null")){
							criteria.add(Restrictions.isNull(k));
						}
					}else if(k.equals("isCancel")){
						criteria.add(Restrictions.eq(k,Boolean.parseBoolean((String)v)));
					}else if(k.equals("invoiceDate")||k.equals("approvedDate")||k.equals("cancelDate")){
						criteria.add(Restrictions.eq(k,v));
					}else{
						criteria.add(Restrictions.ilike(k, (String)v, MatchMode.ANYWHERE));
					}

				});
			}

			criteria.setProjection(Projections.rowCount());
			Long resultCount = (Long)criteria.uniqueResult();
			resultWithCount[0]=resultCount;
			criteria.setProjection(null);

			if(sortField != null){
				if(SortOrder.ASCENDING == sortOrder){
					criteria.addOrder(Order.asc(sortField));
				}else if(SortOrder.DESCENDING == sortOrder){
					criteria.addOrder(Order.desc(sortField));
				}
			}

			criteria.setFirstResult(first);
			criteria.setMaxResults(pageSize);

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<Expense> expenses = criteria.list();
			resultWithCount[1]=expenses;
		}catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		}
		finally{
			session.close();
		}

		return resultWithCount;
	}



	public Expense findExpenseById(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Expense expense = null;
		try{
			String hql = "FROM Expense exp "
					+ "JOIN FETCH exp.account account "
					+ "JOIN FETCH exp.vendor vendor "
					+ "JOIN FETCH exp.userByEntryBy ue "
					+ "LEFT OUTER JOIN FETCH exp.userByCancelBy uc "
					+ "LEFT OUTER JOIN FETCH exp.userByApprovedBy up "
					+ "LEFT OUTER JOIN FETCH exp.expenseDocumentses expdoc "
					+ "LEFT OUTER JOIN FETCH exp.paymentDetails payDetl "
					+ "LEFT OUTER JOIN FETCH payDetl.paymentsDocumentses payDetlDoc "
					+ "LEFT OUTER JOIN FETCH payDetl.cashBooks cb "
					+ "LEFT OUTER JOIN FETCH payDetl.cardBooks cd "
					+ "LEFT OUTER JOIN FETCH cd.bankInternalCard bankInternalCard "
					+ "LEFT OUTER JOIN FETCH bankInternalCard.bankInternal bi "
					+ "LEFT OUTER JOIN FETCH payDetl.neftBooks nf "
					+ "LEFT OUTER JOIN FETCH payDetl.chequeBooks ch "
					+ "where exp.expenseId = :expId";
			
			Query query = session.createQuery(hql);
			query.setParameter("expId", expenseId);
			expense = (Expense) query.uniqueResult();
			
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}

		return expense;
	} 
	
	
	public Expense findExpenceByIdForShowDetails(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Expense expense = null;
		try{
			String hql = "FROM Expense exp "
					+ "JOIN FETCH exp.account account "
					+ "JOIN FETCH exp.vendor vendor "
					+ "where exp.expenseId = :expId";
			
			Query query = session.createQuery(hql);
			query.setParameter("expId", expenseId);
			expense = (Expense) query.uniqueResult();
			
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		return expense;
	}
	
	
	
	public void updatePaymentStatus(int expenseId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			String hql = "UPDATE Expense exp "
					   + "SET exp.paymentStatus = :status "
					   + "WHERE exp.expenseId = :eId";
			Query query = session.createQuery(hql);
			query.setParameter("status","PARTIALLY PAID");
			query.setParameter("eId",expenseId);
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	public void updateExpenseDueAmount(int expenseId,BigDecimal amount){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			String hql = "UPDATE Expense exp "
					   + "SET exp.expenseDue = :dueAmount "
					   + "WHERE exp.expenseId = :eId";
			Query query = session.createQuery(hql);
			query.setParameter("dueAmount",amount);
			query.setParameter("eId",expenseId);
			query.executeUpdate();
			session.getTransaction().commit();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
	}
	
	
	
	
	public List<Expense> findExpenseListWithDateRange(Date startDate,Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Expense> expenseList = null;
		try{
			
			Criteria criteria = session.createCriteria(Expense.class,"expense");
			criteria.createAlias("expense.account","account");
			criteria.createAlias("expense.vendor","vendor");
			criteria.createAlias("expense.userByCancelBy","userByCancelBy",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("expense.userByEntryBy","userByEntryBy",JoinType.LEFT_OUTER_JOIN);
			criteria.createAlias("expense.userByApprovedBy","userByApprovedBy",JoinType.LEFT_OUTER_JOIN);
			Criterion c1 = Restrictions.between("expense.entryDate",startDate,endDate);
			Criterion c2 = Restrictions.isNull("expense.isCancel");
			criteria.add(Restrictions.and(c1,c2));
			expenseList = criteria.list();
		}catch(HibernateException he){
			he.printStackTrace();
			throw he;
		}finally{
			session.close();
		}
		
		return expenseList;
	}
	
	
	

}
