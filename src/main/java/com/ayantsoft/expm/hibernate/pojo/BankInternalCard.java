package com.ayantsoft.expm.hibernate.pojo;
// Generated 10 Jun, 2017 2:20:04 PM by Hibernate Tools 4.3.5.Final

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

/**
 * BankInternalCard generated by hbm2java
 */
@Entity
@Table(name = "bank_internal_card", catalog = "expm")
public class BankInternalCard implements java.io.Serializable {

	private Integer id;
	private BankInternal bankInternal;
	private String cardNo;
	private String cardHolderName;
	private String cardType;
	private Set<CardBook> cardBooks = new HashSet<CardBook>(0);

	public BankInternalCard() {
	}

	public BankInternalCard(BankInternal bankInternal, String cardNo, String cardHolderName, String cardType,
			Set<CardBook> cardBooks) {
		this.bankInternal = bankInternal;
		this.cardNo = cardNo;
		this.cardHolderName = cardHolderName;
		this.cardType = cardType;
		this.cardBooks = cardBooks;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_internal_id")
	public BankInternal getBankInternal() {
		return this.bankInternal;
	}

	public void setBankInternal(BankInternal bankInternal) {
		this.bankInternal = bankInternal;
	}

	@Column(name = "card_no", length = 100)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "card_holder_name", length = 150)
	public String getCardHolderName() {
		return this.cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	@Column(name = "card_type", length = 6)
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankInternalCard")
	public Set<CardBook> getCardBooks() {
		return this.cardBooks;
	}

	public void setCardBooks(Set<CardBook> cardBooks) {
		this.cardBooks = cardBooks;
	}

}
