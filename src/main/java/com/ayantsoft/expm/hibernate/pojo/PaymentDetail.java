package com.ayantsoft.expm.hibernate.pojo;
// Generated 10 Jun, 2017 2:20:04 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PaymentDetail generated by hbm2java
 */
@Entity
@Table(name = "payment_detail", catalog = "expm")
public class PaymentDetail implements java.io.Serializable {

	private Integer paymentId;
	private Expense expense;
	private User userByCancelBy;
	private User userByEnrtyBy;
	private String modeOfPayment;
	private String description;
	private BigDecimal amount;
	private Date entryDate;
	private Boolean isCancel;
	private String reasonForCancel;
	private Date cancelDate;
	private Set<CardBook> cardBooks = new HashSet<CardBook>(0);
	private Set<CashBook> cashBooks = new HashSet<CashBook>(0);
	private Set<NeftBook> neftBooks = new HashSet<NeftBook>(0);
	private Set<PaymentsDocuments> paymentsDocumentses = new HashSet<PaymentsDocuments>(0);
	private Set<ChequeBook> chequeBooks = new HashSet<ChequeBook>(0);

	public PaymentDetail() {
	}

	public PaymentDetail(Expense expense, User userByEnrtyBy, String modeOfPayment, String description,
			BigDecimal amount, Date entryDate) {
		this.expense = expense;
		this.userByEnrtyBy = userByEnrtyBy;
		this.modeOfPayment = modeOfPayment;
		this.description = description;
		this.amount = amount;
		this.entryDate = entryDate;
	}

	public PaymentDetail(Expense expense, User userByCancelBy, User userByEnrtyBy, String modeOfPayment,
			String description, BigDecimal amount, Date entryDate, Boolean isCancel, String reasonForCancel,
			Date cancelDate, Set<CardBook> cardBooks, Set<CashBook> cashBooks, Set<NeftBook> neftBooks,
			Set<PaymentsDocuments> paymentsDocumentses, Set<ChequeBook> chequeBooks) {
		this.expense = expense;
		this.userByCancelBy = userByCancelBy;
		this.userByEnrtyBy = userByEnrtyBy;
		this.modeOfPayment = modeOfPayment;
		this.description = description;
		this.amount = amount;
		this.entryDate = entryDate;
		this.isCancel = isCancel;
		this.reasonForCancel = reasonForCancel;
		this.cancelDate = cancelDate;
		this.cardBooks = cardBooks;
		this.cashBooks = cashBooks;
		this.neftBooks = neftBooks;
		this.paymentsDocumentses = paymentsDocumentses;
		this.chequeBooks = chequeBooks;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "payment_id", unique = true, nullable = false)
	public Integer getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id", nullable = false)
	public Expense getExpense() {
		return this.expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cancel_by")
	public User getUserByCancelBy() {
		return this.userByCancelBy;
	}

	public void setUserByCancelBy(User userByCancelBy) {
		this.userByCancelBy = userByCancelBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enrty_by", nullable = false)
	public User getUserByEnrtyBy() {
		return this.userByEnrtyBy;
	}

	public void setUserByEnrtyBy(User userByEnrtyBy) {
		this.userByEnrtyBy = userByEnrtyBy;
	}

	@Column(name = "mode_of_payment", nullable = false, length = 6)
	public String getModeOfPayment() {
		return this.modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	@Column(name = "description", nullable = false, length = 245)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "amount", nullable = false, precision = 10)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "entry_date", nullable = false, length = 10)
	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	@Column(name = "is_cancel")
	public Boolean getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	@Column(name = "reason_for_cancel", length = 245)
	public String getReasonForCancel() {
		return this.reasonForCancel;
	}

	public void setReasonForCancel(String reasonForCancel) {
		this.reasonForCancel = reasonForCancel;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "cancel_date", length = 10)
	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
	public Set<CardBook> getCardBooks() {
		return this.cardBooks;
	}

	public void setCardBooks(Set<CardBook> cardBooks) {
		this.cardBooks = cardBooks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
	public Set<CashBook> getCashBooks() {
		return this.cashBooks;
	}

	public void setCashBooks(Set<CashBook> cashBooks) {
		this.cashBooks = cashBooks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
	public Set<NeftBook> getNeftBooks() {
		return this.neftBooks;
	}

	public void setNeftBooks(Set<NeftBook> neftBooks) {
		this.neftBooks = neftBooks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
	public Set<PaymentsDocuments> getPaymentsDocumentses() {
		return this.paymentsDocumentses;
	}

	public void setPaymentsDocumentses(Set<PaymentsDocuments> paymentsDocumentses) {
		this.paymentsDocumentses = paymentsDocumentses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail")
	public Set<ChequeBook> getChequeBooks() {
		return this.chequeBooks;
	}

	public void setChequeBooks(Set<ChequeBook> chequeBooks) {
		this.chequeBooks = chequeBooks;
	}

}
