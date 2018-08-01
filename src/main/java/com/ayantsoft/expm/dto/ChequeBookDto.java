package com.ayantsoft.expm.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ChequeBookDto {
	
	private String chequeNo;
	private Integer paymentId;
	private String username; 
	private String payeeName;
	private Date payableDate;
	private BigDecimal amount;
	private boolean isAccountPayable;
	private String chequeStatus;
	private Date entryDate;
	
	
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public Date getPayableDate() {
		return payableDate;
	}
	public void setPayableDate(Date payableDate) {
		this.payableDate = payableDate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public boolean isAccountPayable() {
		return isAccountPayable;
	}
	public void setAccountPayable(boolean isAccountPayable) {
		this.isAccountPayable = isAccountPayable;
	}
	public String getChequeStatus() {
		return chequeStatus;
	}
	public void setChequeStatus(String chequeStatus) {
		this.chequeStatus = chequeStatus;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	

}
