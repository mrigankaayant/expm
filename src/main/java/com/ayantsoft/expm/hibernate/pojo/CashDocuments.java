package com.ayantsoft.expm.hibernate.pojo;
// Generated 10 Jun, 2017 2:20:04 PM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CashDocuments generated by hbm2java
 */
@Entity
@Table(name = "cash_documents", catalog = "expm")
public class CashDocuments implements java.io.Serializable {

	private Integer id;
	private CashBook cashBook;
	private String fileName;
	private String filePath;
	private String contentType;
	private Date createdDate;
	private String extension;

	public CashDocuments() {
	}

	public CashDocuments(CashBook cashBook, String fileName, String filePath, String contentType, Date createdDate,
			String extension) {
		this.cashBook = cashBook;
		this.fileName = fileName;
		this.filePath = filePath;
		this.contentType = contentType;
		this.createdDate = createdDate;
		this.extension = extension;
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
	@JoinColumn(name = "cash_book_id", nullable = false)
	public CashBook getCashBook() {
		return this.cashBook;
	}

	public void setCashBook(CashBook cashBook) {
		this.cashBook = cashBook;
	}

	@Column(name = "file_name", nullable = false, length = 100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_path", nullable = false, length = 300)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "content_type", nullable = false, length = 100)
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", nullable = false, length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "extension", nullable = false, length = 45)
	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
