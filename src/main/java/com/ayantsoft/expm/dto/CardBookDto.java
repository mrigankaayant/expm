package com.ayantsoft.expm.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CardBookDto {
	
	private Integer txnId;
	private Integer paymentDetailsId;
	private String username;
	private BigDecimal amount;
	private Date cardSwipedDate;
	private String cardSwipedBy;
	private Date entryDate;
	private String cardNo;
	private String cardHolderName;
	private String cardType;
	private String bankName;
	private String accountNo;
	
	
	public Integer getTxnId() {
		return txnId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public Integer getPaymentDetailsId() {
		return paymentDetailsId;
	}
	public void setPaymentDetailsId(Integer paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getCardSwipedDate() {
		return cardSwipedDate;
	}
	public void setCardSwipedDate(Date cardSwipedDate) {
		this.cardSwipedDate = cardSwipedDate;
	}
	public String getCardSwipedBy() {
		return cardSwipedBy;
	}
	public void setCardSwipedBy(String cardSwipedBy) {
		this.cardSwipedBy = cardSwipedBy;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
}
