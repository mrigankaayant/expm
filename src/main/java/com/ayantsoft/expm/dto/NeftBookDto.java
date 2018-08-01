package com.ayantsoft.expm.dto;

import java.math.BigDecimal;
import java.util.Date;

public class NeftBookDto {
	
	private Integer txnId;
	private Integer paymentId;
	private String username;
	private String payeeName;
	private String payeeBankName;
	private String payeeBankIfsc;
	private String payeeBankBranch;
	private BigDecimal amount;
	private String neftStatus;
	private Date neftDate;
	private Date entryDate;
	
	
	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
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
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public String getPayeeBankIfsc() {
		return payeeBankIfsc;
	}
	public void setPayeeBankIfsc(String payeeBankIfsc) {
		this.payeeBankIfsc = payeeBankIfsc;
	}
	public String getPayeeBankBranch() {
		return payeeBankBranch;
	}
	public void setPayeeBankBranch(String payeeBankBranch) {
		this.payeeBankBranch = payeeBankBranch;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getNeftStatus() {
		return neftStatus;
	}
	public void setNeftStatus(String neftStatus) {
		this.neftStatus = neftStatus;
	}
	public Date getNeftDate() {
		return neftDate;
	}
	public void setNeftDate(Date neftDate) {
		this.neftDate = neftDate;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	
	

}
