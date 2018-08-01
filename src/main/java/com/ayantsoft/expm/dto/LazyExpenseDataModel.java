package com.ayantsoft.expm.dto;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ayantsoft.expm.hibernate.pojo.Expense;
import com.ayantsoft.expm.service.ExpenseService;

public class LazyExpenseDataModel extends LazyDataModel<Expense> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4454493923793837822L;
	
	
	private List<Expense> expenses;
	private ExpenseService expenseService;
	
	
	public LazyExpenseDataModel(ExpenseService expenseService){
		this.expenseService = expenseService;
	}
	

	@Override
	public Object getRowKey(Expense expense) {
		return expense.getExpenseId();
	}

	@Override
	public Expense getRowData(String rowKey) {
		for(Expense exp : expenses) {
			if(exp.getExpenseId()==Integer.parseInt(rowKey))
				return exp;
		}

		return null;
	}
	
	@Override
	public List<Expense> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		Object[] resultWithCount = expenseService.expenseFilter(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(((Long)resultWithCount[0]).intValue());
		expenses=(List<Expense>) resultWithCount[1];
		return (List<Expense>) resultWithCount[1];

	}


	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public ExpenseService getExpenseService() {
		return expenseService;
	}

	public void setExpenseService(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}
	
}
