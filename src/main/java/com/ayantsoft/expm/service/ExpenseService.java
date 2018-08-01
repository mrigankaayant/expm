package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.expm.hibernate.dao.ExpenseDao;
import com.ayantsoft.expm.hibernate.pojo.Expense;

@ManagedBean
@ApplicationScoped
public class ExpenseService implements Serializable {

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -7342109868754862529L;
	
	
	@ManagedProperty("#{expenseDao}")
	private ExpenseDao expenseDao;
	
	
	public void saveExpense(Expense expense){
		expenseDao.save(expense);
	}
	
	public Object[] expenseFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return expenseDao.expenseFilter(first, pageSize, sortField, sortOrder, filters);
	}
	
	public Expense getExpenseById(int expenseId){
		return expenseDao.findExpenseById(expenseId);
	}
	
	public Expense findExpenceByIdForShowDetails(int expenseId){
		return expenseDao.findExpenceByIdForShowDetails(expenseId);
	}
	
	
	public void updatePaymentStatus(int expenseId){
		expenseDao.updatePaymentStatus(expenseId);
	}
	
	public void updateExpenseDueAmount(int expenseId,BigDecimal amount){
		expenseDao.updateExpenseDueAmount(expenseId,amount);
	}
	
	public List<Expense> findExpenseListWithDateRange(Date startDate,Date endDate){
		return expenseDao.findExpenseListWithDateRange(startDate,endDate);
	}


	// setter and getter
	
	public ExpenseDao getExpenseDao() {
		return expenseDao;
	}


	public void setExpenseDao(ExpenseDao expenseDao) {
		this.expenseDao = expenseDao;
	}

}
