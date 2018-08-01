package com.ayantsoft.expm.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CashBookDto {

	private Integer txnId;
	private String description;
	private String txnType;
	private Date entryDate;
	private BigDecimal credit;
	private BigDecimal debit;
	private BigDecimal currentBalance;
	private Integer paymentDetailsId;

	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public Integer getPaymentDetailsId() {
		return paymentDetailsId;
	}
	public void setPaymentDetailsId(Integer paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}
	
	public BigDecimal calculateBalance(BigDecimal balance){
		Double bVal = balance.doubleValue();
		if(this.txnType.equals("CREDIT")){
			Double amount = this.credit.doubleValue();
			bVal = bVal - amount;
			this.currentBalance = BigDecimal.valueOf(bVal);
		}
		if(this.txnType.equals("DEBIT")){
			Double amount = this.debit.doubleValue();
			bVal = bVal + amount;
			this.currentBalance = BigDecimal.valueOf(bVal);
		}
		return BigDecimal.valueOf(bVal);
	}

}
