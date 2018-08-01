package com.ayantsoft.expm.jsf.view;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.ayantsoft.expm.dto.CardBookDto;
import com.ayantsoft.expm.dto.CashBookDto;
import com.ayantsoft.expm.dto.ChequeBookDto;
import com.ayantsoft.expm.dto.LazyExpenseDataModel;
import com.ayantsoft.expm.dto.NeftBookDto;
import com.ayantsoft.expm.dto.PaymentDetailsDto;
import com.ayantsoft.expm.dto.PropertyFileContainsDto;
import com.ayantsoft.expm.hibernate.pojo.Account;
import com.ayantsoft.expm.hibernate.pojo.Bank;
import com.ayantsoft.expm.hibernate.pojo.BankInternal;
import com.ayantsoft.expm.hibernate.pojo.BankInternalCard;
import com.ayantsoft.expm.hibernate.pojo.CardBook;
import com.ayantsoft.expm.hibernate.pojo.CashBook;
import com.ayantsoft.expm.hibernate.pojo.CashDocuments;
import com.ayantsoft.expm.hibernate.pojo.ChequeBook;
import com.ayantsoft.expm.hibernate.pojo.ChequeNo;
import com.ayantsoft.expm.hibernate.pojo.Expense;
import com.ayantsoft.expm.hibernate.pojo.ExpenseDocuments;
import com.ayantsoft.expm.hibernate.pojo.NeftBook;
import com.ayantsoft.expm.hibernate.pojo.PaymentDetail;
import com.ayantsoft.expm.hibernate.pojo.PaymentsDocuments;
import com.ayantsoft.expm.hibernate.pojo.Vendor;
import com.ayantsoft.expm.hibernate.pojo.VendorDocuments;
import com.ayantsoft.expm.jsf.util.CalendarUtil;
import com.ayantsoft.expm.service.AccountService;
import com.ayantsoft.expm.service.BankInternalCardService;
import com.ayantsoft.expm.service.BankInternalService;
import com.ayantsoft.expm.service.BankService;
import com.ayantsoft.expm.service.CardBookService;
import com.ayantsoft.expm.service.CashBookDocumentsService;
import com.ayantsoft.expm.service.CashBookService;
import com.ayantsoft.expm.service.ChequeBookService;
import com.ayantsoft.expm.service.ChequeNoService;
import com.ayantsoft.expm.service.ExpenseDocumentsService;
import com.ayantsoft.expm.service.ExpenseService;
import com.ayantsoft.expm.service.MailService;
import com.ayantsoft.expm.service.NeftBookService;
import com.ayantsoft.expm.service.PaymentDetailsService;
import com.ayantsoft.expm.service.PaymentDocumentsService;
import com.ayantsoft.expm.service.VendorDocumentsService;
import com.ayantsoft.expm.service.VendorService;


/**
 * @author Mriganka
 *
 */
@ManagedBean
@ViewScoped
public class AccountBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -908638231306343660L;


	@ManagedProperty("#{accountService}")
	private AccountService accountService;

	@ManagedProperty("#{vendorService}")
	private VendorService vendorService;

	@ManagedProperty("#{bankService}")
	private BankService bankService;


	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@ManagedProperty("#{appDataBean}")
	private AppDataBean appDataBean;

	@ManagedProperty("#{vendorDocumentsService}")
	private VendorDocumentsService vendorDocumentsService;

	@ManagedProperty("#{cashBookDocumentsService}")
	private CashBookDocumentsService cashBookDocumentsService;
	
	@ManagedProperty("#{expenseService}")
	private ExpenseService expenseService;

	@ManagedProperty("#{paymentDetailsService}")
	private PaymentDetailsService paymentDetailsService;

	@ManagedProperty("#{expenseDocumentsService}")
	private ExpenseDocumentsService expenseDocumentsService;

	@ManagedProperty("#{paymentDocumentsService}")
	private PaymentDocumentsService paymentDocumentsService;

	@ManagedProperty("#{cashBookService}")
	private CashBookService cashBookService;

	@ManagedProperty("#{cardBookService}")
	private CardBookService cardBookService;

	@ManagedProperty("#{neftBookService}")
	private NeftBookService neftBookService;

	@ManagedProperty("#{chequeBookService}")
	private ChequeBookService chequeBookService;

	@ManagedProperty("#{bankInternalService}")
	private BankInternalService bankInternalService;

	@ManagedProperty("#{chequeNoService}")
	private ChequeNoService chequeNoService;

	@ManagedProperty("#{bankInternalCardService}")
	private BankInternalCardService bankInternalCardService;


	private String action;
	private Account account;
	private List<Account> accounts;
	private Vendor vendor;
	private Vendor selectedVendor;
	private List<Vendor> vendors;
	private Bank bank;
	private Bank selectedBank;
	private List<Bank> banks;
	private boolean bankListPanel;
	private boolean vendorEditPanel;
	private List<UploadedFile> vendorFiles;
	private List<String> vendorFileNames;
	private List<VendorDocuments> vendorDocumentsList;
	private boolean primaryAccountCheck;
	private Expense expense;
	private LazyDataModel<Expense> expenseLazyModel;
	private Expense selectedExpense;
	public Boolean serviceTaxCheck;
	public Boolean TDSCheck;
	public Double serviceTaxInPercentage;
	public Double TDSInPercentage;
	public Double calculatedTotalServiceTax = 0.0;
	public Double calculatedTotalAmount = 0.0;
	public Double netAmount = 0.0;
	public Boolean enablefield = true;
	public Boolean enableTDSField = true;
	public Double totalAmount;
	public Double calculatedTDSAmount;
	public boolean paymentPanelShow = false;
	public boolean paymentButtonShow = false;
	public PaymentDetail paymentDetail;
	public List<PaymentDetail> paymentDetailsList;
	public boolean cardDetailsPanelShow = false;
	public boolean neftDetailsPanelShow = false;
	public boolean chequeDetailsPanelShow = false;
	public CardBook cardBook;
	public NeftBook neftBook;
	public ChequeBook chequeBook;
	private List<UploadedFile> expenseDocuments;
	private List<UploadedFile> paymentDocuments;
	private List<String> expenseFileName;
	private List<String> paymentFileName;
	private Set<PaymentDetail> paymentDetailsSet;
	private String cancelReason;
	private Integer bankId;
	private List<BankInternalCard> cards;
	private Integer cardId;
	private BankInternalCard bankInternalCard;
	private List<ChequeNo> cheques;
	private ChequeNo chequeNo;
	private String checkno;
	private Bank vendorPromaryAccount;
	private Long chequeStartNo;
	private Long chequeEndNo;
	private Integer internalBankId;
	private List<ChequeNo> chequeList;
	private BankInternal internalBank;
	private BankInternal bankInternal;
	private List<BankInternal> bankInternalList;
	private BankInternalCard internalCard;
	private List<BankInternalCard> internalCardList;
	private CashBook cashbook;
	private List<CashBook> cashbooks;
	private List<UploadedFile> cashFiles;
	private List<String> cashFileNames;
	private boolean cashDetailsPanelSelector;
	private boolean dateRangePanelSelector;
	private String reportFor;
	private String duration;
	private Date startDate;
	private Date endDate;
	private BigDecimal totalCredit;
	private BigDecimal totalDebit;
	private BigDecimal balance;
	private List<CashBookDto> cashBookList;
	private List<CardBookDto> cardBookList;
	private List<NeftBookDto> neftBookList;
	private List<ChequeBookDto> chequeBookList;
	private BigDecimal opengingTotalCredit;
	private BigDecimal openingTotalDebit;
	private BigDecimal openingBalance; 
	private boolean showCashDetailsPanel;
	private boolean showCardDetailsPanel;
	private boolean showNeftDetailsPanel;
	private boolean showChequeDetailsPanel;
	private CashBookDto selectedCashBookDto;
	private CardBookDto selectedCardBookDto;
	private NeftBookDto selectedNeftBookDto;
	private ChequeBookDto selectedChequeBookDto;
	private PaymentDetail paymentDetailForReport;
	private Integer expenseAccountId;
	private double calculateExpense;
	private List<Expense> expenseDetailsList;
	private List<PaymentDetailsDto> paymentDetailsDtoList;



	public void accountList(){
		try{
			if(account != null){
				account = null;
			}
			accounts = accountService.getAccountList();
			action = "ACCOUNTLIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void editAccount(){
		action = "ACCOUNTFORM";
	}



	public void vendorForm(){
		vendorFiles = new ArrayList<UploadedFile>();
		vendorFileNames = new ArrayList<String>();
		if(bankListPanel == true){
			bankListPanel = false;
		}
		if(banks != null && banks.size() >0){
			banks = null;
		}
		vendor = new Vendor();
		action = "VENDORFORM";

		bank = new Bank();
		bank.setVendor(new Vendor());
	}



	public void vendorFileUpload(FileUploadEvent event) throws IOException{
		try{
			UploadedFile uploadFile=event.getFile();
			uploadFile.getContents();
			vendorFiles.add(uploadFile);
			String fileName = uploadFile.getFileName();
			vendorFileNames.add(fileName);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void vendorFileDelete(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String deletefile = params.get("deletefile");
		vendorFileNames.remove(deletefile);
		Iterator<String> ite = vendorFileNames.iterator();
		while(ite.hasNext()) {
			String value = ite.next();
			if(value.equals(deletefile))
				ite.remove();
		}
		Iterator<UploadedFile> ite2 = vendorFiles.iterator();
		while(ite2.hasNext()) {
			UploadedFile uploadedFile = ite2.next();
			if(uploadedFile.getFileName().equals(deletefile)){
				ite2.remove();
			}
		}
	}


	public void saveVendor(){
		try{

			Integer vId = vendor.getVenderId();
			vendorService.saveVendor(vendor);
			bank.setVendor(vendor);
			List<String> docPaths = null;

			if(vendorFiles.size() > 0){
				docPaths = new ArrayList<String>();
				String rootFilePath = null;
				String vendorFileFolderPath = null;
				try{
					Properties prop = new Properties();
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
					prop.load(inputStream);
					rootFilePath = prop.getProperty("vendorFilePath");
				}catch(Exception e){
					e.printStackTrace();
				}

				vendorFileFolderPath = rootFilePath+"/"+vendor.getVenderName()+"_"+vendor.getVenderId();
				File file = new File(vendorFileFolderPath);
				if(!file.exists()){
					file.mkdir();
				}

				for(UploadedFile uploadFile:vendorFiles){
					uploadFile.getContents();
					byte[] bytes = uploadFile.getContents();
					String filename = FilenameUtils.getName(uploadFile.getFileName());
					String fileContent = uploadFile.getContentType();
					int index = filename.indexOf( '.' );
					String extension = filename.substring(index);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(vendorFileFolderPath+"/"+filename)));
					stream.write(bytes);
					stream.close();
					VendorDocuments vendorDocuments = new VendorDocuments();
					vendorDocuments.setVendor(vendor);
					vendorDocuments.setFileName(filename);
					vendorDocuments.setFilePath(vendorFileFolderPath+"/"+filename);
					docPaths.add(vendorFileFolderPath+"/"+filename);
					vendorDocuments.setContentType(fileContent);
					vendorDocuments.setCreatedDate(new Date());
					vendorDocuments.setExtension(extension);
					vendorDocumentsService.saveVendorDocuments(vendorDocuments);
				}
			}
			appDataBean.vendorListInitaalize();
			if(vId == null){
				if(loginBean.hasRole("ACCOUNT"))
				{
					PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
					MailService mailService = new MailService();
					MailService accountMail = new MailService();
					MailService subAdminMail = new MailService();
					String msg = "<p>New vendor has been created</p> <p>The details of this vendor is - </p> <b>Vendor Name: </b> "+vendor.getVenderName()+" <br> <b>Vendor Pan No: </b>"+vendor.getVenderPan()+" <br> " 
							+"<b>Vendor Address: </b>"+vendor.getVenderAddress()+"<br> <b>Vendor Mobile No: </b>"+vendor.getVenderMobile()+"<br> <b>Vendor Email: </b>"+vendor.getVenderEmail();
					if(docPaths != null && docPaths.size()>0){
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg, propertyFileContainsDto.getAdminEmail(),docPaths);
						accountMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg, propertyFileContainsDto.getAccountEmail(),docPaths);
						subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg, propertyFileContainsDto.getSubAdminEmail(),docPaths);
					}else{
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg, propertyFileContainsDto.getAdminEmail(),null);
						accountMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg, propertyFileContainsDto.getAccountEmail(),null);
						subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),vendor.getVenderName()+" vendor added",msg,propertyFileContainsDto.getSubAdminEmail(),null);
					}
					Thread t = new Thread(mailService);
					t.start(); 
					Thread t2 = new Thread(accountMail);
					t2.start();
					Thread t3 = new Thread(subAdminMail);
					t3.start();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Vendor Saved Successfully"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Vendor Updated Successfully"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Vendor Not Saved"));
		}
	}


	public void updateVendor(){
		try{
			vendorEditPanel = true;
			vendorService.saveVendor(vendor);


			if(vendorFiles.size() > 0){

				String rootFilePath = null;
				String vendorFileFolderPath = null;
				try{
					Properties prop = new Properties();
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
					prop.load(inputStream);
					rootFilePath = prop.getProperty("vendorFilePath");
				}catch(Exception e){
					e.printStackTrace();
				}

				vendorFileFolderPath = rootFilePath+"/"+vendor.getVenderName()+"_"+vendor.getVenderId();
				File file = new File(vendorFileFolderPath);
				if(!file.exists()){
					file.mkdir();
				}

				for(UploadedFile uploadFile:vendorFiles){
					uploadFile.getContents();
					byte[] bytes = uploadFile.getContents();
					String filename = FilenameUtils.getName(uploadFile.getFileName());
					String fileContent = uploadFile.getContentType();
					int index = filename.indexOf( '.' );
					String extension = filename.substring(index);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(vendorFileFolderPath+"/"+filename)));
					stream.write(bytes);
					stream.close();
					VendorDocuments vendorDocuments = new VendorDocuments();
					vendorDocuments.setVendor(vendor);
					vendorDocuments.setFileName(filename);
					vendorDocuments.setFilePath(vendorFileFolderPath+"/"+filename);
					vendorDocuments.setContentType(fileContent);
					vendorDocuments.setCreatedDate(new Date());
					vendorDocuments.setExtension(extension);
					vendorDocumentsService.saveVendorDocuments(vendorDocuments);
				}
			}
			vendor = vendorService.getVendorById(vendor.getVenderId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Vendor Updated Successfully"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Vendor Not Updated"));
		}
	}


	public void deleteVendorFile(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String path = params.get("filePath");
		String id = params.get("id");
		try{
			boolean idDetete = vendorDocumentsService.deleteVendorDocById(Integer.valueOf(id));
			if(idDetete){
				File file = new File(path);
				file.delete();
			}
			vendorDocumentsList = vendorDocumentsService.documentsByVendorId(vendor.getVenderId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void editVendor(){
		vendorEditPanel = false;
		vendorFiles = new ArrayList<UploadedFile>();
		vendorFileNames = new ArrayList<String>();
		vendorDocumentsList = vendorDocumentsService.documentsByVendorId(vendor.getVenderId());
	}



	public void vendorList(){
		try{
			if(vendor != null){
				vendor = null;
			}
			if(selectedVendor != null){
				selectedVendor = null;
			}
			vendors = vendorService.getVendorList();
			action = "VENDORLIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void onRowSelectInVendorList(SelectEvent event) {
		try{

			if(vendorEditPanel == false){
				vendorEditPanel = true;
			}
			vendor = vendorService.getVendorById(selectedVendor.getVenderId());
			action = "VENDORDETAILS";
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void saveBank(){
		try{
			boolean isExist = false;
			if(bank.getPrimaryAccount()){
				isExist = bankService.findPrimaryAccountByVendorId(vendor.getVenderId());
			}
			if(isExist){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Primary Account Already Exist"));
			}else{
				Integer bankId = bank.getBankId();
				bankService.saveBank(bank);
				if(bank.getBankId() != null){
					bankListPanel = true;
				}
				banks = bankService.getBankListByVendorId(bank.getVendor().getVenderId());
				if(bankId == null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Saved Successfully"));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Updated Successfully"));
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Bank Not Saved"));
		}
	}


	public void checkPrimaryAccountForUpdate(FacesContext context, UIComponent component, Object value){
		boolean val = (boolean) value;
		try{
			if(val != primaryAccountCheck){
				if(val){
					boolean isExist = bankService.findPrimaryAccountByVendorId(bank.getVendor().getVenderId());
					if(isExist){
						FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Primary Account Check", "Primary Account Exist"));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void updateBank(){
		try{
			if(bank.getPrimaryAccount() != primaryAccountCheck){
				if(bank.getPrimaryAccount()){
					boolean isExist = bankService.findPrimaryAccountByVendorId(bank.getVendor().getVenderId());
					if(isExist){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Primary Account Already Exist"));
						vendor = vendorService.getVendorById(bank.getVendor().getVenderId());
					}else{
						bankService.saveBank(bank);
						vendor = vendorService.getVendorById(bank.getVendor().getVenderId());
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Successfully Updated"));
					}
				}else{
					bankService.saveBank(bank);
					vendor = vendorService.getVendorById(bank.getVendor().getVenderId());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Successfully Updated"));
				}
			}else{
				bank.setPrimaryAccount(primaryAccountCheck);
				bankService.saveBank(bank);
				vendor = vendorService.getVendorById(bank.getVendor().getVenderId());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Successfully Updated"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Bank Not Updated"));
		}
	}



	public void addMoreBank(){
		Integer bankId = null;
		try{

			boolean isExist = false;
			if(bank.getPrimaryAccount()){
				isExist = bankService.findPrimaryAccountByVendorId(vendor.getVenderId());
			}

			if(isExist){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Primary Account Already Exist"));
			}else{
				bankId = bank.getBankId();
				bankService.saveBank(bank);
				vendor = vendorService.getVendorById(bank.getVendor().getVenderId());
				if(bankId == null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Successfully Added"));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Bank Successfully Updated"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			if(bankId == null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Bank Not Added"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Bank Not Updated"));
			}
		}
	}


	public void editBank(Vendor vendor,Bank updatedbank){
		bank = updatedbank;
		bank.setVendor(vendor);
		primaryAccountCheck = bank.getPrimaryAccount();
	}


	public void addBank(){
		bank = new Bank();
		if(vendor != null){
			bank.setVendor(vendor);
		}else{
			bank.setVendor(new Vendor());
		}
	}


	public void bankList(){
		try{
			if(bank != null){
				bank = null;
			}
			if(selectedBank != null){
				selectedBank = null;
			}
			banks = bankService.getBankList();
			action = "BANKLIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void onRowSelectInBankList(SelectEvent event) {
		try{

			bank = bankService.getBankById(selectedBank.getBankId());
			action = "BANKFORM";

		}catch(Exception e){
			e.printStackTrace();
		}
	}




	public void expenseForm(){

		expenseFileName = new ArrayList<String>();
		expenseDocuments = new ArrayList<UploadedFile>();
		serviceTaxCheck=false;
		TDSCheck=false;
		cardDetailsPanelShow = false;
		neftDetailsPanelShow = false;
		chequeDetailsPanelShow = false;
		serviceTaxInPercentage = null;
		TDSInPercentage = null;
		calculatedTotalServiceTax = 0.0;
		calculatedTotalAmount = 0.0;
		netAmount = 0.0;
		enablefield = true;
		enableTDSField = true;
		totalAmount = null;
		calculatedTDSAmount = null;
		paymentPanelShow = false;
		paymentButtonShow = false;
		expense = new Expense();
		expense.setAccount(new Account());
		expense.setVendor(new Vendor());
		if(paymentDetailsList != null){
			paymentDetailsList = null;
		}
		action = "EXPENSEFORM";
	}



	public void getTotalAmount(FacesContext context, UIComponent component, Object value){
		BigDecimal val = (BigDecimal)value;
		totalAmount = val.doubleValue();
		calculatedTotalAmount = totalAmount;
		netAmount = totalAmount;
	}


	public void enableServiceTax(){
		if(serviceTaxCheck == true){
			enablefield = false;
			serviceTaxInPercentage = 15.0;
			if(totalAmount != null){
				calculatedTotalServiceTax = (totalAmount*serviceTaxInPercentage)/100;
				calculatedTotalAmount = totalAmount+calculatedTotalServiceTax;
				if(calculatedTDSAmount != null){
					netAmount = (totalAmount+calculatedTotalServiceTax) - calculatedTDSAmount;
				}else{
					netAmount = totalAmount+calculatedTotalServiceTax;
				}

			}
		}else{
			enablefield = true;
			serviceTaxInPercentage = null;
			if(totalAmount != null){
				calculatedTotalAmount = totalAmount;
				netAmount = totalAmount;
			}
			calculatedTotalServiceTax = 0.0;
			if(calculatedTDSAmount != null){
				netAmount = totalAmount - calculatedTDSAmount;
			}
		}

	}


	public void enableTDSTax(){
		if(TDSCheck == true){
			enableTDSField = false;
			TDSInPercentage = 10.0;
			if(totalAmount != null){
				calculatedTDSAmount = (totalAmount*TDSInPercentage)/100;
				if(calculatedTotalServiceTax == null){
					netAmount = (totalAmount+0.0) - calculatedTDSAmount;
				}else{
					netAmount = (totalAmount+calculatedTotalServiceTax) - calculatedTDSAmount;
				}
			}
		}else{
			enableTDSField = true;
			TDSInPercentage = null;
			calculatedTDSAmount = 0.0;
			if(totalAmount != null && calculatedTotalServiceTax!= null && calculatedTotalServiceTax == 0.0 ){
				netAmount = totalAmount;
			}

			if(totalAmount != null && calculatedTotalServiceTax!= null && calculatedTotalServiceTax > 0.0 ){
				netAmount = totalAmount+calculatedTotalServiceTax;
			}
		}
	}


	public void calculateAmountOfServiceTax(FacesContext context, UIComponent component, Object value){

		Double serviceTax = (Double)value;
		if(totalAmount != null && serviceTax != null){
			calculatedTotalServiceTax = (totalAmount*serviceTax)/100;
			calculatedTotalAmount = totalAmount+calculatedTotalServiceTax;
			if(calculatedTDSAmount != null){
				netAmount = (totalAmount+calculatedTotalServiceTax) - calculatedTDSAmount;
			}else{
				netAmount = totalAmount+calculatedTotalServiceTax;
			}
		}
	}


	public void calculateAmountOfTDS(FacesContext context, UIComponent component, Object value){

		Double tds = (Double)value;
		if(totalAmount != null && tds != null){
			calculatedTDSAmount = (totalAmount*tds)/100;
			if(calculatedTotalServiceTax != null){
				netAmount = (totalAmount+calculatedTotalServiceTax) - calculatedTDSAmount;
			}else{
				netAmount = (totalAmount+0.0) - calculatedTDSAmount;
			}
		}
	}


	public void checkModeOfPayment(FacesContext context, UIComponent component, Object value){

	}


	public void checkPrimaryAccount(FacesContext context, UIComponent component, Object value){
		boolean val = (boolean) value;
		try{
			if(val){
				boolean isExist = bankService.findPrimaryAccountByVendorId(vendor.getVenderId());
				if(isExist){
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Primary Account Check", "Primary Account Exist"));
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void saveExpense(){
		try{

			List<String> docPaths = null;
			expense.setEntryDate(new Date());
			expense.setUserByEntryBy(loginBean.getUser());
			if(calculatedTotalServiceTax != null){
				expense.setServiceTax(new BigDecimal(calculatedTotalServiceTax));
			}
			if(calculatedTDSAmount != null){
				expense.setTdsAmount(new BigDecimal(calculatedTDSAmount));
			}
			if(netAmount != null){
				expense.setNetAmount(new BigDecimal(netAmount));
				expense.setExpenseDue(new BigDecimal(netAmount));
			}
			expense.setPaymentStatus("NOT PAID");
			expenseService.saveExpense(expense);
			if(expense.getExpenseType().equals("INCURRED")){
				paymentButtonShow = true;
			}

			if(expenseDocuments.size() > 0){
				docPaths = new ArrayList<String>();
				String rootFilePath = null;
				String expenseFileFolderPath = null;
				try{
					Properties prop = new Properties();
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
					prop.load(inputStream);
					rootFilePath = prop.getProperty("expenseFilePath");
				}catch(Exception e){
					e.printStackTrace();
				}

				expenseFileFolderPath = rootFilePath+"/"+"expense"+"_"+expense.getExpenseId();
				File file = new File(expenseFileFolderPath);
				if(!file.exists()){
					file.mkdir();
				}

				for(UploadedFile uploadFile:expenseDocuments){
					uploadFile.getContents();
					byte[] bytes = uploadFile.getContents();
					String filename = FilenameUtils.getName(uploadFile.getFileName());
					String fileContent = uploadFile.getContentType();
					int index = filename.indexOf( '.' );
					String extension = filename.substring(index);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(expenseFileFolderPath+"/"+filename)));
					stream.write(bytes);
					stream.close();

					ExpenseDocuments expenseDocuments = new ExpenseDocuments();
					expenseDocuments.setFileName(filename);
					expenseDocuments.setFilePath(expenseFileFolderPath+"/"+filename);
					docPaths.add(expenseFileFolderPath+"/"+filename);
					expenseDocuments.setContentType(fileContent);
					expenseDocuments.setExtension(extension);
					expenseDocuments.setCreatedDate(new Date());
					expenseDocuments.setExpense(expense);
					expenseDocumentsService.saveExpenseDocuments(expenseDocuments);
				}
			}

			expense = expenseService.findExpenceByIdForShowDetails(expense.getExpenseId());

			if(expense.getExpenseId() != null){
				if(loginBean.hasRole("ACCOUNT")){
					PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
					MailService mailService = new MailService();
					MailService mailaccount = new MailService();
					MailService subAdminMail = new MailService();
					String msg = "<p>New expense has been created</p> <p>The details of this expense is - </p> <b>Invoice No: </b>"+expense.getInvoiceNo()+"<br> <b>Invoice Date: </b>"+expense.getInvoiceDate()
					+"<br><b>Expense Type: </b>"+expense.getExpenseType()+"<br><b>Account Name: </b>"+expense.getAccount().getAccountName()+"<br><b>Service Tax: </b>"+expense.getServiceTax()+"<br><b>Total Amount: </b>"+expense.getTotalAmount()+"<br><b>TDS Amount: </b>"+expense.getTdsAmount()
					+"<br><b>NET Amount: </b>"+expense.getNetAmount()+"<br><b>Vendor Name: </b>"+expense.getVendor().getVenderName()+"<br><b>Vendor Phone No: </b>"+expense.getVendor().getVenderMobile()+"<br><b>Vendor Email: </b>"+expense.getVendor().getVenderEmail();
					if(docPaths != null && docPaths.size() >0){
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getAdminEmail(),docPaths);
						mailaccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getAccountEmail(),docPaths);
						subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getSubAdminEmail(),docPaths);
					}else{
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getAdminEmail(),null);
						mailaccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getAccountEmail(),null);
						subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New expense added of RS "+expense.getNetAmount(),msg, propertyFileContainsDto.getSubAdminEmail(),null);
					}
					Thread t = new Thread(mailService);
					t.start(); 
					Thread t2 = new Thread(mailaccount);
					t2.start(); 
					Thread t3 = new Thread(subAdminMail);
					t3.start();
				}
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Expense Saved Successfully"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Expense Not Saved"));
		}
	}


	public void showPaymentForm(){
		paymentDetailsSet = new HashSet<PaymentDetail>();
		paymentFileName = new ArrayList<String>();
		paymentDocuments = new ArrayList<UploadedFile>();
		paymentDetail = new PaymentDetail();
		cardBook = new CardBook();
		neftBook = new NeftBook();
		chequeBook = new ChequeBook();
		paymentDetail.setExpense(expense);
		if(paymentDetailsSet != null && paymentDetailsSet.size() >0){
			paymentDetailsSet.clear();
		}
		if(paymentFileName != null && paymentFileName.size() >0){
			paymentFileName.clear();
		}
		if(cards != null && cards.size() >0){
			cards = null;
		}
		if(cheques != null && cheques.size() > 0){
			cheques = null;
		}
		cardDetailsPanelShow = false;
		chequeDetailsPanelShow = false;
		neftDetailsPanelShow = false;
		bankId = null;
		cardId = null;
		checkno = null;
		bankInternalCard = new BankInternalCard();
		chequeNo = new ChequeNo();
		vendorPromaryAccount = new Bank();

	}


	public void expenseFileDelete(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String deletefile = params.get("deletefile");
		expenseFileName.remove(deletefile);
		Iterator<String> ite = expenseFileName.iterator();
		while(ite.hasNext()) {
			String value = ite.next();
			if(value.equals(deletefile))
				ite.remove();
		}
		Iterator<UploadedFile> ite2 = expenseDocuments.iterator();
		while(ite2.hasNext()) {
			UploadedFile uploadedFile = ite2.next();
			if(uploadedFile.getFileName().equals(deletefile)){
				ite2.remove();
			}
		}
	}

	public void getBankNameForPayment(FacesContext context, UIComponent component, Object value){
		bankId = (Integer)value;
		if(paymentDetail.getModeOfPayment().equals("CARD")){
			cardDetailsPanelShow = true;
			cards = bankInternalCardService.findCardByBankId(bankId);
		}else{
			cardDetailsPanelShow = false;
		}
		if(paymentDetail.getModeOfPayment().equals("NEFT")){
			neftDetailsPanelShow = true;
			vendorPromaryAccount = bankService.findPrimaryBankByVendorId(expense.getVendor().getVenderId());
		}else{
			neftDetailsPanelShow = false;
		}
		if(paymentDetail.getModeOfPayment().equals("CHEQUE")){
			chequeDetailsPanelShow = true;
			cheques = chequeNoService.findChequeListByBankId(bankId);
			vendorPromaryAccount = bankService.findPrimaryBankByVendorId(expense.getVendor().getVenderId());
		}else{
			chequeDetailsPanelShow = false;
		}



	}


	public void getCardNumberForPayment(FacesContext context, UIComponent component, Object value){
		cardId = (Integer)value;
		bankInternalCard = bankInternalCardService.findBankInternalCardById(cardId);
	}


	public void getCheckNoForPayment(FacesContext context, UIComponent component, Object value){
		checkno = (String)value;
		chequeNo = chequeNoService.findChequeNoById(checkno);
	}



	public void savePayment(){

		try{
			List<String> paymentDocPaths = null;
			double totalPaidAmount = paymentDetailsService.findTotalPaidAmountByExpenseId(expense.getExpenseId());
			if(totalPaidAmount == expense.getNetAmount().doubleValue()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Completed"));
			}else{
				double payAmount = paymentDetail.getAmount().doubleValue();
				double total = totalPaidAmount + payAmount;
				if(total == expense.getNetAmount().doubleValue() || total < expense.getNetAmount().doubleValue()){
					paymentDetail.setEntryDate(new Date());
					paymentDetail.setUserByEnrtyBy(loginBean.getUser());
					paymentDetailsService.save(paymentDetail);
					if(paymentDetail.getPaymentId() != null){
						if(total == expense.getNetAmount().doubleValue()){
							expense.setPaymentStatus("FULL PAID");
						}else{
							expense.setPaymentStatus("PARTIALLY PAID");
						}
						expense.setExpenseDue(new BigDecimal(expense.getExpenseDue().doubleValue()-payAmount));
						expenseService.saveExpense(expense);


						if(paymentDetail.getModeOfPayment().equals("CASH")){
							CashBook cashBook = new CashBook();
							cashBook.setDescription(paymentDetail.getDescription());
							cashBook.setTxnType("CREDIT");
							cashBook.setCredit(paymentDetail.getAmount());
							cashBook.setDebit(new BigDecimal(0.0));
							cashBook.setPaymentDetail(paymentDetail);
							cashBook.setEntryDate(new Date());
							cashBook.setUser(loginBean.getUser()); 
							cashBookService.saveCashBook(cashBook);
						}

						if(paymentDetail.getModeOfPayment().equals("CARD")){
							cardBook.setDescription(paymentDetail.getDescription());
							cardBook.setAmount(paymentDetail.getAmount());
							cardBook.setPaymentDetail(paymentDetail);
							cardBook.setEntryDate(new Date());
							cardBook.setUser(loginBean.getUser());
							cardBook.setBankInternalCard(bankInternalCard);
							cardBookService.saveCardBook(cardBook);
						}

					}

					if(paymentDocuments.size() > 0){

						paymentDocPaths = new ArrayList<String>();
						String rootFilePath = null;
						String paymentFileFolderPath = null;
						try{
							Properties prop = new Properties();
							InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
							prop.load(inputStream);
							rootFilePath = prop.getProperty("paymentFilePath");
						}catch(Exception e){
							e.printStackTrace();
						}

						paymentFileFolderPath = rootFilePath+"/"+"payment"+"_"+paymentDetail.getPaymentId();
						File file = new File(paymentFileFolderPath);
						if(!file.exists()){
							file.mkdir();
						}

						for(UploadedFile uploadFile:paymentDocuments){
							uploadFile.getContents();
							byte[] bytes = uploadFile.getContents();
							String filename = FilenameUtils.getName(uploadFile.getFileName());
							String fileContent = uploadFile.getContentType();
							int index = filename.indexOf( '.' );
							String extension = filename.substring(index);
							BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(paymentFileFolderPath+"/"+filename)));
							stream.write(bytes);
							stream.close();

							PaymentsDocuments paymentsDocuments = new PaymentsDocuments();
							paymentsDocuments.setFileName(filename);
							paymentsDocuments.setFilePath(paymentFileFolderPath+"/"+filename);
							paymentDocPaths.add(paymentFileFolderPath+"/"+filename);
							paymentsDocuments.setExtension(extension);
							paymentsDocuments.setContentType(fileContent);
							paymentsDocuments.setCreatedDate(new Date());
							paymentsDocuments.setPaymentDetail(paymentDetail);
							paymentDocumentsService.savePaymentDocuments(paymentsDocuments);
						}
					}

					paymentDetailsList = paymentDetailsService.findPaymentListByExpenseId(expense.getExpenseId());
					for(PaymentDetail p :paymentDetailsList){
						paymentDetailsSet.add(p);
					}
					paymentDetailsList.clear();
					paymentDetailsList.addAll(paymentDetailsSet);

					if(loginBean.hasRole("ACCOUNT")){
						PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
						MailService mailService = new MailService();
						MailService mailAccount = new MailService();
						MailService subAdminMail = new MailService();
						String msg="<p>New payment has been added</p> <p>The details of this payment is - </p> <b>Mode Of Payment: </b>"+paymentDetail.getModeOfPayment()+"<br><b>Payment Amount: </b>"+paymentDetail.getAmount()
						+"<br><b>Invoice No: </b>"+expense.getInvoiceNo()+"<br><b>Vendor Name: </b>"+expense.getVendor().getVenderName()+"<br><b>Vendor Phone: </b>"+expense.getVendor().getVenderMobile()+"<br><b>Vendor Email: </b>"+expense.getVendor().getVenderEmail()+"<br>"
						+"<b>Description: </b>"+paymentDetail.getDescription();
						if(paymentDocPaths != null && paymentDocPaths.size() >0){
							mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getAdminEmail(), paymentDocPaths);
							mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getAccountEmail(), paymentDocPaths);
							subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getSubAdminEmail(), paymentDocPaths);
						}else{
							mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getAdminEmail(), null);
							mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getAccountEmail(), null);
							subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"Payment added by "+paymentDetail.getModeOfPayment()+" of amount "+paymentDetail.getAmount(),msg, propertyFileContainsDto.getSubAdminEmail(), null);
						}
						Thread t = new Thread(mailService);
						t.start();
						Thread t2 = new Thread(mailAccount);
						t2.start();
						Thread t3 = new Thread(subAdminMail);
						t3.start();
					}

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Payment Saved Successfully"));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Amount Is Greater Than Net Amount "));
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Not Saved"));
		}
	}	



	public void expenseList(){
		try{
			if(expense != null){
				expense = null;
			}
			if(selectedExpense != null){
				selectedExpense = null;
			}
			if(cancelReason != null){
				cancelReason = null;
			}
			expenseLazyModel = new LazyExpenseDataModel(expenseService);
			action = "EXPENSELIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void cancelResonForm(){
		cancelReason = null;
	}

	public void cancelExpense(){
		try{
			boolean isPayment = paymentDetailsService.checkPaymentByVendorId(expense.getExpenseId());
			if(!isPayment){
				expense.setUserByCancelBy(loginBean.getUser());
				expense.setCancelDate(new Date());
				expense.setReasonForCancel(cancelReason);
				expense.setIsCancel(true);
				expenseService.saveExpense(expense);
				expense = expenseService.getExpenseById(expense.getExpenseId());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Expense Successfully Canceled"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Cancel Not Possible , Payment Already Exist"));	
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void onRowSelectInExpenseList(SelectEvent event){
		try{

			expense = expenseService.getExpenseById(selectedExpense.getExpenseId());
			if(cancelReason != null){
				cancelReason = null;
			}
			action = "EXPENSEDETAILS";

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void expenseFileUpload(FileUploadEvent event) throws IOException{
		try{
			UploadedFile uploadFile=event.getFile();
			uploadFile.getContents();
			expenseDocuments.add(uploadFile);
			expenseFileName.add(uploadFile.getFileName());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void paymentFileUpload(FileUploadEvent event) throws IOException{
		try{
			UploadedFile uploadFile=event.getFile();
			uploadFile.getContents();
			paymentDocuments.add(uploadFile);
			paymentFileName.add(uploadFile.getFileName());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "File Uploaded Failed"));
		}
	}



	public void paymentFileDelete(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String deletefile = params.get("deletefile");
		paymentFileName.remove(deletefile);
		Iterator<String> ite = paymentFileName.iterator();
		while(ite.hasNext()) {
			String value = ite.next();
			if(value.equals(deletefile))
				ite.remove();
		}
		Iterator<UploadedFile> ite2 = paymentDocuments.iterator();
		while(ite2.hasNext()) {
			UploadedFile uploadedFile = ite2.next();
			if(uploadedFile.getFileName().equals(deletefile)){
				ite2.remove();
			}
		}
	}







	public void openFile() {

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String path = params.get("filePath");
		String name = params.get("filename");
		String extension = params.get("extension");

		try {
			File file = new File(path);
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			if (extension.equals(".xls")) {
				response.setContentType("application/xls");
			}else if(extension.equals(".pdf")){
				response.setContentType("application/pdf");
			}else if(extension.equals(".zip")){
				response.setContentType("application/zip");
			}else if(extension.equals(".docx")){
				response.setContentType("application/octet-stream");
			}else if(extension.equals(".doc")){
				response.setContentType("application/octet-stream");
			}else if(extension.equals(".txt")){
				response.setContentType("application/text");
			}else if(extension.equals(".xlsx")){
				response.setContentType("application/xlsx");
			}else if(extension.equals(".csv")){
				response.setContentType("application/csv");
			}else if(extension.equals(".jpg")){
				response.setContentType("application/jpg");
			}else if(extension.equals(".jpeg")){
				response.setContentType("application/jpeg");
			}else if(extension.equals(".png")){
				response.setContentType("application/png");
			}else if(extension.equals(".svg")){
				response.setContentType("application/svg");
			}else if(extension.equals(".gif")){
				response.setContentType("application/gif");
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "File Type Not Supported"));
			}
			response.setHeader("Content-Disposition", "inline;filename=\"" + name + "\"");
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] bFile = new byte[(int)file.length()];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bFile);
			IOUtils.copy((InputStream)inputStream, (OutputStream)outputStream);
			outputStream.flush();
			outputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		}
		catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error" + e.getMessage()));
		}
	}

	
	public void checkPaidAmount(FacesContext context, UIComponent component, Object value){
		try{
			BigDecimal inputval = (BigDecimal)value;
			double totalPaidAmount = paymentDetailsService.findTotalPaidAmountByExpenseId(expense.getExpenseId());
			double total = inputval.doubleValue()+totalPaidAmount;
			if(total>expense.getNetAmount().doubleValue()){
				FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Amount is Grater Then Net Amount","Amount is Grater Then Net Amount"));
			}

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Error" + e.getMessage()));
		}

	}



	public void addMorePayment(){

		try{
			List<String> paymentDocPaths = null;
			double totalPaidAmount = paymentDetailsService.findTotalPaidAmountByExpenseId(expense.getExpenseId());
			if(totalPaidAmount == expense.getNetAmount().doubleValue()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Completed"));
			}else{
				double payAmount = paymentDetail.getAmount().doubleValue();
				double total = totalPaidAmount + payAmount;
				if(total == expense.getNetAmount().doubleValue() || total < expense.getNetAmount().doubleValue()){
					paymentDetail.setEntryDate(new Date());
					paymentDetail.setUserByEnrtyBy(loginBean.getUser());
					paymentDetailsService.save(paymentDetail);
					if(paymentDetail.getPaymentId() != null){
						if(total == expense.getNetAmount().doubleValue()){
							expense.setPaymentStatus("FULL PAID");
						}else{
							expense.setPaymentStatus("PARTIALLY PAID");
						}
						expense.setExpenseDue(new BigDecimal(expense.getExpenseDue().doubleValue()-payAmount));
						expenseService.saveExpense(expense);


						if(paymentDetail.getModeOfPayment().equals("CASH")){
							CashBook cashBook = new CashBook();
							cashBook.setDescription(paymentDetail.getDescription());
							cashBook.setTxnType("CREDIT");
							cashBook.setCredit(paymentDetail.getAmount());
							cashBook.setDebit(new BigDecimal(0.0));
							cashBook.setPaymentDetail(paymentDetail);
							cashBook.setEntryDate(new Date());
							cashBook.setUser(loginBean.getUser()); 
							cashBookService.saveCashBook(cashBook);
						}

						if(paymentDetail.getModeOfPayment().equals("CARD")){
							cardBook.setDescription(paymentDetail.getDescription());
							cardBook.setAmount(paymentDetail.getAmount());
							cardBook.setPaymentDetail(paymentDetail);
							cardBook.setEntryDate(new Date());
							cardBook.setUser(loginBean.getUser());
							cardBook.setBankInternalCard(bankInternalCard);
							cardBookService.saveCardBook(cardBook);
						}


						if(paymentDetail.getModeOfPayment().equals("NEFT")){
							neftBook.setDescription(paymentDetail.getDescription());
							neftBook.setPayeeName(vendorPromaryAccount.getPayeeName());
							neftBook.setPayeeBankName(vendorPromaryAccount.getBankName());
							neftBook.setPayeeBankIfsc(vendorPromaryAccount.getBankIfsc());
							neftBook.setPayeeBankBranch(vendorPromaryAccount.getBankBranch());
							neftBook.setAmount(paymentDetail.getAmount());
							neftBook.setPaymentDetail(paymentDetail);
							neftBook.setEntryDate(new Date());
							neftBook.setUserByEntryBy(loginBean.getUser());
							neftBookService.saveNeftBook(neftBook);
						}

						if(paymentDetail.getModeOfPayment().equals("CHEQUE")){
							chequeBook.setChequeNo(checkno);
							chequeBook.setDescription(paymentDetail.getDescription());
							chequeBook.setAmount(paymentDetail.getAmount());
							chequeBook.setPaymentDetail(paymentDetail);
							chequeBook.setEntryDate(new Date());
							chequeBook.setUser(loginBean.getUser());
							chequeBook.setChequeStatus("ISSUED");
							chequeBook.setIsAccountPayable(true);
							chequeBook.setPayeeName(vendorPromaryAccount.getPayeeName());
							chequeBookService.saveChequeBook(chequeBook);
							chequeNo.setIsIssued(true);
							chequeNoService.save(chequeNo);
						}

					}

					if(paymentDocuments.size() > 0){

						paymentDocPaths = new ArrayList<String>();
						String rootFilePath = null;
						String paymentFileFolderPath = null;
						try{
							Properties prop = new Properties();
							InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
							prop.load(inputStream);
							rootFilePath = prop.getProperty("paymentFilePath");
						}catch(Exception e){
							e.printStackTrace();
						}

						paymentFileFolderPath = rootFilePath+"/"+"payment"+"_"+paymentDetail.getPaymentId();
						File file = new File(paymentFileFolderPath);
						if(!file.exists()){
							file.mkdir();
						}

						for(UploadedFile uploadFile:paymentDocuments){
							uploadFile.getContents();
							byte[] bytes = uploadFile.getContents();
							String filename = FilenameUtils.getName(uploadFile.getFileName());
							String fileContent = uploadFile.getContentType();
							int index = filename.indexOf( '.' );
							String extension = filename.substring(index);
							BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(paymentFileFolderPath+"/"+filename)));
							stream.write(bytes);
							stream.close();

							PaymentsDocuments paymentsDocuments = new PaymentsDocuments();
							paymentsDocuments.setFileName(filename);
							paymentsDocuments.setFilePath(paymentFileFolderPath+"/"+filename);
							paymentDocPaths.add(paymentFileFolderPath+"/"+filename);
							paymentsDocuments.setExtension(extension);
							paymentsDocuments.setContentType(fileContent);
							paymentsDocuments.setCreatedDate(new Date());
							paymentsDocuments.setPaymentDetail(paymentDetail);
							paymentDocumentsService.savePaymentDocuments(paymentsDocuments);
						}
					}

					paymentDetailsList = paymentDetailsService.findPaymentListByExpenseId(expense.getExpenseId());
					expense = expenseService.getExpenseById(expense.getExpenseId());
					for(PaymentDetail p :paymentDetailsList){
						paymentDetailsSet.add(p);
					}
					paymentDetailsList.clear();
					paymentDetailsList.addAll(paymentDetailsSet);

					if(loginBean.hasRole("ACCOUNT")){
						PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
						MailService mailService = new MailService();
						MailService mailAccount = new MailService();
						MailService subAdminMail = new MailService();
						String msg="<p>New payment has been added</p> <p>The details of this payment is - </p> <b>Mode Of Payment: </b>"+paymentDetail.getModeOfPayment()+"<br><b>Payment Amount: </b>"+paymentDetail.getAmount()
						+"<br><b>Invoice No: </b>"+expense.getInvoiceNo()+"<br><b>Vendor Name: </b>"+expense.getVendor().getVenderName()+"<br><b>Vendor Phone: </b>"+expense.getVendor().getVenderMobile()+"<br><b>Vendor Email: </b>"+expense.getVendor().getVenderEmail()+"<br>"
						+"<b>Description: </b>"+paymentDetail.getDescription();
						if(paymentDocPaths != null && paymentDocPaths.size() >0){
							mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getAdminEmail(), paymentDocPaths);
							mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getAccountEmail(), paymentDocPaths);
							subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getSubAdminEmail(), paymentDocPaths);
						}else{
							mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getAdminEmail(), null);
							mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getAccountEmail(), null);
							subAdminMail.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New payment has been added",msg,propertyFileContainsDto.getSubAdminEmail(), null);
						}
						Thread t = new Thread(mailService);
						t.start();
						Thread t2 = new Thread(mailAccount);
						t2.start();
						Thread t3 = new Thread(subAdminMail);
						t3.start();
					}

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Payment Saved Successfully"));
				}else{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Amount Is Greater Than Net Amount "));
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Payment Not Saved"));
		}
	}	



	public void chequeForm(){
		internalBank = new BankInternal();
		chequeStartNo = null;
		chequeEndNo = null;
		internalBankId = null;
		action = "CHEQUEFORM";
	}


	public void saveCheque(){

		try{

			if(internalBank.getId() != null){
				List<ChequeNo> chequeNos = new ArrayList<ChequeNo>();
				for(Long i=chequeStartNo;i<=chequeEndNo;i++){
					ChequeNo ch = new ChequeNo();
					ch.setChequeNo(Long.toString(i));
					ch.setIsIssued(false);
					ch.setBankInternal(internalBank);
					chequeNos.add(ch);
				}
				chequeNoService.saveChequeNos(chequeNos);

				if(loginBean.hasRole("ACCOUNT")){
					PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
					MailService mailService = new MailService();
					MailService mailAccount = new MailService();
					String msg="<p>New cheque has been added</p> <p>The details of this cheque is - </p> <b>Bank Name: </b>"+internalBank.getBankName()+"<br> <b>Account No: </b>"+internalBank.getAccountNo()
					+"<br> <b>Starting cheque No: </b>"+chequeStartNo+"<br> <b>Ending cheque No: </b>"+chequeEndNo;
					mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New Cheque has been added",msg, propertyFileContainsDto.getAdminEmail(), null);
					mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New Cheque has been added",msg, propertyFileContainsDto.getAccountEmail(), null);
					Thread t = new Thread(mailService);
					t.start();
					Thread t2 = new Thread(mailAccount);
					t2.start();
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Cheque Successfully Created"));
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Cheque Successfully Not Created"));
		}

	}


	public void getInternalBank(FacesContext context, UIComponent component, Object value){
		internalBankId = (Integer) value;
		internalBank = bankInternalService.findBankInternalById(internalBankId);
	}


	public void chequeList(){
		try{
			chequeList = chequeNoService.findChequeList();
			action = "CHEQUELIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void internalBankForm(){
		bankInternal = new BankInternal();
		action = "INTERNALBANKFORM";
	}


	public void saveInternalBank(){
		try{
			bankInternalService.saveBankInternal(bankInternal);
			appDataBean.bankInternalListInitialize();
			PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();

			if(loginBean.hasRole("ACCOUNT"))
			{
				if(bankInternal.getId() != null){
					MailService mailService = new MailService();
					MailService mailAccount = new MailService();
					String msg="<p>New Internal Bank has been added</p> <p>The details of this bank is - </p> <b>Bank Name: </b>"+bankInternal.getBankName()+"<br> <b>Account No: </b>"+bankInternal.getAccountNo();
					mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New internal bank has been added",msg, propertyFileContainsDto.getAdminEmail(), null);
					mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New internal bank has been added",msg, propertyFileContainsDto.getAccountEmail(), null);
					Thread t = new Thread(mailService);
					t.start(); 
					Thread t2 = new Thread(mailAccount);
					t2.start(); 
				}
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Bank Successfully Created"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Bank Successfully Not Created"));
		}
	}


	public void internalBankList(){
		action = "INTERNALBANKLIST";
		bankInternalList = bankInternalService.findBankList();
	}

	public void cardForm(){
		internalCard = new BankInternalCard();
		internalCard.setBankInternal(new BankInternal());
		action ="INTERNALCARDFORM";
	}


	public void saveInternalBankCard(){
		try{
			bankInternalCardService.saveBankInternalCard(internalCard);

			if(loginBean.hasRole("ACCOUNT")){
				PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
				if(internalCard.getId() != null){
					internalCard = bankInternalCardService.findBankInternalCardById(internalCard.getId());
				}
				if(internalCard.getId() != null){
					MailService mailService = new MailService();
					MailService mailAccount = new MailService();
					String msg="<p>New internal card has been added</p> <p>The details of this card is - </p> <b>Bank Name: </b>"+internalCard.getBankInternal().getBankName()+"<br><b>Account No: </b>"+internalCard.getBankInternal().getAccountNo()
							+"<br><b>Card No: </b>"+internalCard.getCardNo()+"<br><b>Card Holder Name: </b>"+internalCard.getCardHolderName()+"<br><b>Card Type: </b>"+internalCard.getCardType();
					mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New Internal Card has been added",msg, propertyFileContainsDto.getAdminEmail(), null);
					mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New Internal Card has been added",msg, propertyFileContainsDto.getAccountEmail(), null);
					Thread t = new Thread(mailService);
					t.start(); 
					Thread t2 = new Thread(mailAccount);
					t2.start();
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "Card Successfully Created"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Card Successfully Not Created"));
		}
	}



	public void cardList(){
		action = "INTERNALCARDLIST";
		internalCardList = bankInternalCardService.findAllBankInternalCards();
	}


	public void cashBookForm(){
		cashbook = new CashBook();
		action = "CASHBOOKFORM";
		cashFiles = new ArrayList<UploadedFile>();
		cashFileNames =new ArrayList<String>();
	}


	public void cashFileUpload(FileUploadEvent event) throws IOException{
		try{
			UploadedFile uploadFile=event.getFile();
			uploadFile.getContents();
			cashFiles.add(uploadFile);
			String fileName = uploadFile.getFileName();
			cashFileNames.add(fileName);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Success", "File Uploaded Succesfully"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void cashFileDelete(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params =fc.getExternalContext().getRequestParameterMap();
		String deletefile = params.get("deletefile");
		cashFileNames.remove(deletefile);
		Iterator<String> ite = cashFileNames.iterator();
		while(ite.hasNext()) {
			String value = ite.next();
			if(value.equals(deletefile))
				ite.remove();
		}
		Iterator<UploadedFile> ite2 = cashFiles.iterator();
		while(ite2.hasNext()) {
			UploadedFile uploadedFile = ite2.next();
			if(uploadedFile.getFileName().equals(deletefile)){
				ite2.remove();
			}
		}
	}




	public void saveCash(){

		try{

			cashbook.setEntryDate(new Date());
			cashbook.setCredit(BigDecimal.valueOf(0.0));
			cashbook.setTxnType("DEBIT");
			cashbook.setUser(loginBean.getUser());
			cashBookService.saveCashBook(cashbook);
			List<String> docPaths = null;

			if(cashFiles.size() > 0){

				docPaths = new ArrayList<String>();
				String rootFilePath = null;
				String cashFileFolderPath = null;
				try{
					Properties prop = new Properties();
					InputStream inputStream = getClass().getClassLoader().getResourceAsStream("expm.properties");
					prop.load(inputStream);
					rootFilePath = prop.getProperty("cashFilePath");
				}catch(Exception e){
					e.printStackTrace();
				}

				cashFileFolderPath = rootFilePath+"/"+"cashbook_"+cashbook.getTxnId();
				File file = new File(cashFileFolderPath);
				if(!file.exists()){
					file.mkdir();
				}


				for(UploadedFile uploadFile:cashFiles){
					uploadFile.getContents();
					byte[] bytes = uploadFile.getContents();
					String filename = FilenameUtils.getName(uploadFile.getFileName());
					String fileContent = uploadFile.getContentType();
					int index = filename.indexOf( '.' );
					String extension = filename.substring(index);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(cashFileFolderPath+"/"+filename)));
					stream.write(bytes);
					stream.close();
					CashDocuments cashDocuments = new CashDocuments();
					cashDocuments.setCashBook(cashbook);
					cashDocuments.setFileName(filename);
					cashDocuments.setFilePath(cashFileFolderPath+"/"+filename);
					docPaths.add(cashFileFolderPath+"/"+filename);
					cashDocuments.setContentType(fileContent);
					cashDocuments.setCreatedDate(new Date());
					cashDocuments.setExtension(extension);
					cashBookDocumentsService.saveCashBookDocuments(cashDocuments);
				}
			}

			if(loginBean.hasRole("ACCOUNT"))
			{
				PropertyFileContainsDto propertyFileContainsDto = appDataBean.getPropertyFileContainsDto();
				if(cashbook.getTxnId() != null){
					MailService mailService = new MailService();
					MailService mailAccount = new MailService();
					if(docPaths != null && docPaths.size()>0){
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New cash has been added","<b>RS "+cashbook.getDebit()+" has added in cash book.</b>", propertyFileContainsDto.getAdminEmail(),docPaths);
						mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New cash has been added","<b>RS "+cashbook.getDebit()+" has added in cash book.</b>", propertyFileContainsDto.getAccountEmail(),docPaths);
					}else{
						mailService.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New cash has been added","<b>RS "+cashbook.getDebit()+" has added in cash book.</b>", propertyFileContainsDto.getAdminEmail(),null);
						mailAccount.setParameters(propertyFileContainsDto.getNotificationEmailId(), propertyFileContainsDto.getNotificationEmailPassword(),"New cash has been added","<b>RS "+cashbook.getDebit()+" has added in cash book.</b>", propertyFileContainsDto.getAccountEmail(),null);
					}
					Thread t = new Thread(mailService);
					t.start(); 
					Thread t2 = new Thread(mailAccount);
					t2.start(); 
				}
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Cash Saved Successfully"));

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Account Not Saved"));
		}
	}


	public void cashBookList(){
		try{
			if(cashbook != null){
				cashbook = null;
			}

			cashbooks = cashBookService.getCashBookList();
			action = "CASHBOOKLIST";
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	public void reportForm(){
		try{
			action = "REPORTFORM";
			reportFor = null;
			duration = null;
			startDate = null;
			endDate = null;
			totalCredit = null;
			totalDebit = null;
			balance = null;
			cashBookList = null;
			expenseAccountId = null;
			cardBookList = null;
			neftBookList = null;
			calculateExpense = 0.0;
			chequeBookList = null;
			opengingTotalCredit = null;
			openingTotalDebit = null;
			openingBalance = null; 
			cashDetailsPanelSelector = false;
			dateRangePanelSelector = false;
			showCashDetailsPanel = false;
			showCardDetailsPanel = false;
			showNeftDetailsPanel = false;
			showChequeDetailsPanel = false;
			selectedCashBookDto = null;
			selectedCardBookDto = null;
			selectedNeftBookDto = null;
			selectedChequeBookDto = null;
			paymentDetailForReport = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void getReportFor(FacesContext context, UIComponent component, Object value){
		reportFor = (String) value; 
		if("CASH".equals(reportFor)){
			cashDetailsPanelSelector = true;
			totalCredit = cashBookService.getTotalCredit();
			totalDebit = cashBookService.getTotalDebit();
			Double creditVal = totalCredit.doubleValue();
			Double debitVal = totalDebit.doubleValue();
			balance = BigDecimal.valueOf(debitVal-creditVal);
		}
		if("CARD".equals(reportFor)){
			cashDetailsPanelSelector = false;
			totalCredit = null;
			totalDebit = null;
			balance = null;
		}
		if("NEFT".equals(reportFor)){
			cashDetailsPanelSelector = false;
			totalCredit = null;
			totalDebit = null;
			balance = null;
		}
		if("CHEQUE".equals(reportFor)){
			cashDetailsPanelSelector = false;
			totalCredit = null;
			totalDebit = null;
			balance = null;
		}
	}



	public void dateRangeCheck(FacesContext context, UIComponent component, Object value){
		try{
			duration = (String) value; 
			if("Date Range".equals(duration)){
				dateRangePanelSelector = true;
				startDate = null;
				endDate = null;
			}
			if("Current Month".equals(duration)){
				if("CASH".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cashBookList = cashBookService.findCashBookDetailsByDateRange(startDate, endDate);
					opengingTotalCredit = cashBookService.findTotalCreditByDateRange(startDate);
					openingTotalDebit = cashBookService.findTotalDebitByDateRange(startDate);
					Double creditVal = opengingTotalCredit.doubleValue();
					Double debitVal =  openingTotalDebit.doubleValue();
					openingBalance = BigDecimal.valueOf(debitVal-creditVal);
					BigDecimal balanceForCalculation = openingBalance;
					for(CashBookDto cbd:cashBookList){
						balanceForCalculation = cbd.calculateBalance(balanceForCalculation);
					}
				}

				if("CARD".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cardBookList = cardBookService.findCardBookDetailsByDateRange(startDate, endDate);
				}

				if("NEFT".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					neftBookList = neftBookService.findNeftBookDetailsByDateRange(startDate, endDate);
				}

				if("CHEQUE".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = false;
					showChequeDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					chequeBookList = chequeBookService.findChequeBookDetailsByDateRange(startDate,endDate);
				}

			}
			if("Current Month Plus Previous Month".equals(duration)){
				if("CASH".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfPreviousMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cashBookList = cashBookService.findCashBookDetailsByDateRange(startDate, endDate);

					opengingTotalCredit = cashBookService.findTotalCreditByDateRange(startDate);
					openingTotalDebit = cashBookService.findTotalDebitByDateRange(startDate);
					Double creditVal = opengingTotalCredit.doubleValue();
					Double debitVal =  openingTotalDebit.doubleValue();
					openingBalance = BigDecimal.valueOf(debitVal-creditVal);

					BigDecimal balanceForCalculation = openingBalance;
					for(CashBookDto cbd:cashBookList){
						balanceForCalculation = cbd.calculateBalance(balanceForCalculation);
					}
				}

				if("CARD".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cardBookList = cardBookService.findCardBookDetailsByDateRange(startDate, endDate);
				}

				if("NEFT".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					neftBookList = neftBookService.findNeftBookDetailsByDateRange(startDate, endDate);
				}

				if("CHEQUE".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = false;
					showChequeDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					chequeBookList = chequeBookService.findChequeBookDetailsByDateRange(startDate,endDate);
				}
			}
			if("Current Month Plus Previous Two Month".equals(duration)){
				if("CASH".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfPreviousMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cashBookList = cashBookService.findCashBookDetailsByDateRange(startDate, endDate);

					opengingTotalCredit = cashBookService.findTotalCreditByDateRange(startDate);
					openingTotalDebit = cashBookService.findTotalDebitByDateRange(startDate);
					Double creditVal = opengingTotalCredit.doubleValue();
					Double debitVal =  openingTotalDebit.doubleValue();
					openingBalance = BigDecimal.valueOf(debitVal-creditVal);

					BigDecimal balanceForCalculation = openingBalance;
					for(CashBookDto cbd:cashBookList){
						balanceForCalculation = cbd.calculateBalance(balanceForCalculation);
					}
				}

				if("CARD".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					cardBookList = cardBookService.findCardBookDetailsByDateRange(startDate, endDate);
				}

				if("NEFT".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					neftBookList = neftBookService.findNeftBookDetailsByDateRange(startDate, endDate);
				}

				if("CHEQUE".equals(reportFor)){
					dateRangePanelSelector = false;
					showCashDetailsPanel = false;
					showCardDetailsPanel = false;
					showNeftDetailsPanel = false;
					showChequeDetailsPanel = true;
					startDate = CalendarUtil.getStartDateOfCurrentMonth();
					endDate = CalendarUtil.getEndDateOfCurrentMonth();
					chequeBookList = chequeBookService.findChequeBookDetailsByDateRange(startDate,endDate);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public void findReportByStartDateAndEndDate(){
		try{
			dateRangePanelSelector = true;
			if("CASH".equals(reportFor)){
				dateRangePanelSelector = true;
				showCashDetailsPanel = true;
				cashBookList = cashBookService.findCashBookDetailsByDateRange(startDate,endDate);

				opengingTotalCredit = cashBookService.findTotalCreditByDateRange(startDate);
				openingTotalDebit = cashBookService.findTotalDebitByDateRange(startDate);
				Double creditVal = opengingTotalCredit.doubleValue();
				Double debitVal =  openingTotalDebit.doubleValue();
				openingBalance = BigDecimal.valueOf(debitVal-creditVal);

				BigDecimal balanceForCalculation = openingBalance;
				for(CashBookDto cbd:cashBookList){
					balanceForCalculation = cbd.calculateBalance(balanceForCalculation);
				}
			}

			if("CARD".equals(reportFor)){
				dateRangePanelSelector = true;
				showCashDetailsPanel = false;
				showCardDetailsPanel = true;
				cardBookList = cardBookService.findCardBookDetailsByDateRange(startDate, endDate);
			}

			if("NEFT".equals(reportFor)){
				dateRangePanelSelector = false;
				showCashDetailsPanel = false;
				showCardDetailsPanel = false;
				showNeftDetailsPanel = true;
				neftBookList = neftBookService.findNeftBookDetailsByDateRange(startDate, endDate);
			}

			if("CHEQUE".equals(reportFor)){
				dateRangePanelSelector = false;
				showCashDetailsPanel = false;
				showCardDetailsPanel = false;
				showNeftDetailsPanel = false;
				showChequeDetailsPanel = true;
				chequeBookList = chequeBookService.findChequeBookDetailsByDateRange(startDate,endDate);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void onRowSelectInCashBookDetails(SelectEvent event) {
		try{
			if(selectedCashBookDto.getPaymentDetailsId() != null){
				action = "CASHDETAILSREPORT";
				paymentDetailForReport = paymentDetailsService.findPaymentDetailsByPaymentId(selectedCashBookDto.getPaymentDetailsId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onRowSelectInCardBookDetails(SelectEvent event){
		try{
			action = "CARDDETAILSREPORT";
			paymentDetailForReport = paymentDetailsService.findPaymentDetailsByPaymentId(selectedCardBookDto.getPaymentDetailsId());
		}catch(Exception e ){
			e.printStackTrace();
		}
	}


	public void onRowSelectInNeftBookDetails(SelectEvent event){
		try{
			action = "NEFTDETAILSREPORT";
			paymentDetailForReport = paymentDetailsService.findPaymentDetailsByPaymentId(selectedNeftBookDto.getPaymentId());
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

	public void onRowSelectInChequeBookDetails(SelectEvent event){
		try{
			action = "CHEQUEDETAILSREPORT";
			paymentDetailForReport = paymentDetailsService.findPaymentDetailsByPaymentId(selectedChequeBookDto.getPaymentId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void updateNeftStatus(NeftBook neftBook,int expenseId,String status){
		try{
			if(status.equals("SUCCESS")){
				neftBook.setNeftStatus("SUCCESS");
			}else{
				neftBook.setNeftStatus("FAILURE");
			}
			neftBookService.saveNeftBook(neftBook);
			expense = expenseService.getExpenseById(expenseId);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "NEFT Successfully Updated"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "NEFT Successfully Not Updated"));
		}

	}


	public void updateChequeStatus(ChequeBook chequeBook,int expenseId,String status){
		try{
			if(status.equals("CLEARED")){
				chequeBook.setChequeStatus("CLEARED");
			}if(status.equals("CANCELED")){
				chequeBook.setChequeStatus("CANCELED");
			}if(status.equals("BOUNCED")){
				chequeBook.setChequeStatus("BOUNCED");
			}
			chequeBookService.saveChequeBook(chequeBook);
			expense = expenseService.getExpenseById(expenseId);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucess:", "Cheque Successfully Updated"));

		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fail:", "Cheque Successfully Not Updated"));
		}

	}
	
	
	
	public void expenseReport(){
		
		action = "EXPENSEREPORT";
		startDate = null;
		endDate = null;
		expenseAccountId = null;
		calculateExpense = 0.0;
		paymentDetailsDtoList = null;
		
	}
	
	
	public void totalExpenseByAccountId(){
		try{
			paymentDetailsDtoList = paymentDetailsService.findPaymentDetailsByAccountId(expenseAccountId,startDate,endDate);
			for(PaymentDetailsDto pdd:paymentDetailsDtoList){
				calculateExpense = calculateExpense + pdd.getAmount().doubleValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public void expenseDetailsReport(){
		action = "EXPENSEDETAILSREPORTWITHDATERANGE";
		startDate = null;
		endDate = null;
		expenseDetailsList = null;
	}
	
	
	public void expenseDetailsList(){
		try{
			expenseDetailsList = expenseService.findExpenseListWithDateRange(startDate,endDate);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public double calculateTotalAmount(){
		double val = 0.0;
		if(expenseDetailsList != null && expenseDetailsList.size() >0){
			for(Expense e:expenseDetailsList){
				val = val + e.getTotalAmount().doubleValue();
			}
		}
		
		return val;
	}
	
	public double calculateTotalServiceTax(){
		double val = 0.0;
		if(expenseDetailsList != null && expenseDetailsList.size() >0){
			for(Expense e:expenseDetailsList){
				if(e.getServiceTax() != null){
					val = val + e.getServiceTax().doubleValue();
				}
			}
		}
		
		return val;
	}
	
	public double calculateTotalTDS(){
		double val = 0.0;
		if(expenseDetailsList != null && expenseDetailsList.size() >0){
			for(Expense e:expenseDetailsList){
				if(e.getTdsAmount() != null){
					val = val + e.getTdsAmount().doubleValue();
				}
			}
		}
		return val;
	}
	
	public double calculateTotalNetAmount(){
		double val = 0.0;
		if(expenseDetailsList != null && expenseDetailsList.size() >0){
			for(Expense e:expenseDetailsList){
				val = val + e.getNetAmount().doubleValue();
			}
		}
		return val;
	}
	
	
	public double calculateTotalDueAmount(){
		double val = 0.0;
		if(expenseDetailsList != null && expenseDetailsList.size() >0){
			for(Expense e:expenseDetailsList){
				if(e.getExpenseDue() != null){
					val = val + e.getExpenseDue().doubleValue();	
				}
			}
		}
		return val;
	}
	
	
	public void backToExpenseList(){
		expenseList();
	}


	//setter and getter


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Vendor getSelectedVendor() {
		return selectedVendor;
	}

	public void setSelectedVendor(Vendor selectedVendor) {
		this.selectedVendor = selectedVendor;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public VendorService getVendorService() {
		return vendorService;
	}

	public void setVendorService(VendorService vendorService) {
		this.vendorService = vendorService;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Bank getSelectedBank() {
		return selectedBank;
	}

	public void setSelectedBank(Bank selectedBank) {
		this.selectedBank = selectedBank;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public ExpenseService getExpenseService() {
		return expenseService;
	}

	public void setExpenseService(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	public Expense getSelectedExpense() {
		return selectedExpense;
	}

	public void setSelectedExpense(Expense selectedExpense) {
		this.selectedExpense = selectedExpense;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public AppDataBean getAppDataBean() {
		return appDataBean;
	}

	public void setAppDataBean(AppDataBean appDataBean) {
		this.appDataBean = appDataBean;
	}

	public boolean isBankListPanel() {
		return bankListPanel;
	}

	public void setBankListPanel(boolean bankListPanel) {
		this.bankListPanel = bankListPanel;
	}

	public boolean isVendorEditPanel() {
		return vendorEditPanel;
	}

	public void setVendorEditPanel(boolean vendorEditPanel) {
		this.vendorEditPanel = vendorEditPanel;
	}

	public Boolean getServiceTaxCheck() {
		return serviceTaxCheck;
	}

	public void setServiceTaxCheck(Boolean serviceTaxCheck) {
		this.serviceTaxCheck = serviceTaxCheck;
	}

	public Double getServiceTaxInPercentage() {
		return serviceTaxInPercentage;
	}

	public void setServiceTaxInPercentage(Double serviceTaxInPercentage) {
		this.serviceTaxInPercentage = serviceTaxInPercentage;
	}

	public Double getCalculatedAmount() {
		return calculatedTotalAmount;
	}

	public void setCalculatedAmount(Double calculatedAmount) {
		this.calculatedTotalAmount = calculatedAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Boolean getEnablefield() {
		return enablefield;
	}

	public void setEnablefield(Boolean enablefield) {
		this.enablefield = enablefield;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getCalculatedTotalAmount() {
		return calculatedTotalAmount;
	}

	public void setCalculatedTotalAmount(Double calculatedTotalAmount) {
		this.calculatedTotalAmount = calculatedTotalAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public Boolean getTDSCheck() {
		return TDSCheck;
	}

	public void setTDSCheck(Boolean tDSCheck) {
		TDSCheck = tDSCheck;
	}

	public Double getTDSInPercentage() {
		return TDSInPercentage;
	}

	public void setTDSInPercentage(Double tDSInPercentage) {
		TDSInPercentage = tDSInPercentage;
	}

	public Boolean getEnableTDSField() {
		return enableTDSField;
	}

	public void setEnableTDSField(Boolean enableTDSField) {
		this.enableTDSField = enableTDSField;
	}

	public Double getCalculatedTDSAmount() {
		return calculatedTDSAmount;
	}

	public void setCalculatedTDSAmount(Double calculatedTDSAmount) {
		this.calculatedTDSAmount = calculatedTDSAmount;
	}

	public Double getCalculatedTotalServiceTax() {
		return calculatedTotalServiceTax;
	}

	public void setCalculatedTotalServiceTax(Double calculatedTotalServiceTax) {
		this.calculatedTotalServiceTax = calculatedTotalServiceTax;
	}

	public boolean isPaymentPanelShow() {
		return paymentPanelShow;
	}

	public void setPaymentPanelShow(boolean paymentPanelShow) {
		this.paymentPanelShow = paymentPanelShow;
	}

	public boolean isPaymentButtonShow() {
		return paymentButtonShow;
	}

	public void setPaymentButtonShow(boolean paymentButtonShow) {
		this.paymentButtonShow = paymentButtonShow;
	}

	public PaymentDetail getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetail paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public PaymentDetailsService getPaymentDetailsService() {
		return paymentDetailsService;
	}

	public void setPaymentDetailsService(PaymentDetailsService paymentDetailsService) {
		this.paymentDetailsService = paymentDetailsService;
	}

	public List<PaymentDetail> getPaymentDetailsList() {
		return paymentDetailsList;
	}

	public void setPaymentDetailsList(List<PaymentDetail> paymentDetailsList) {
		this.paymentDetailsList = paymentDetailsList;
	}


	public ExpenseDocumentsService getExpenseDocumentsService() {
		return expenseDocumentsService;
	}

	public void setExpenseDocumentsService(ExpenseDocumentsService expenseDocumentsService) {
		this.expenseDocumentsService = expenseDocumentsService;
	}

	public PaymentDocumentsService getPaymentDocumentsService() {
		return paymentDocumentsService;
	}

	public void setPaymentDocumentsService(PaymentDocumentsService paymentDocumentsService) {
		this.paymentDocumentsService = paymentDocumentsService;
	}

	public CashBookService getCashBookService() {
		return cashBookService;
	}

	public void setCashBookService(CashBookService cashBookService) {
		this.cashBookService = cashBookService;
	}

	public boolean isCardDetailsPanelShow() {
		return cardDetailsPanelShow;
	}

	public void setCardDetailsPanelShow(boolean cardDetailsPanelShow) {
		this.cardDetailsPanelShow = cardDetailsPanelShow;
	}

	public CardBook getCardBook() {
		return cardBook;
	}

	public void setCardBook(CardBook cardBook) {
		this.cardBook = cardBook;
	}

	public CardBookService getCardBookService() {
		return cardBookService;
	}

	public void setCardBookService(CardBookService cardBookService) {
		this.cardBookService = cardBookService;
	}

	public boolean isNeftDetailsPanelShow() {
		return neftDetailsPanelShow;
	}

	public void setNeftDetailsPanelShow(boolean neftDetailsPanelShow) {
		this.neftDetailsPanelShow = neftDetailsPanelShow;
	}

	public NeftBook getNeftBook() {
		return neftBook;
	}

	public void setNeftBook(NeftBook neftBook) {
		this.neftBook = neftBook;
	}

	public NeftBookService getNeftBookService() {
		return neftBookService;
	}

	public void setNeftBookService(NeftBookService neftBookService) {
		this.neftBookService = neftBookService;
	}

	public boolean isChequeDetailsPanelShow() {
		return chequeDetailsPanelShow;
	}

	public void setChequeDetailsPanelShow(boolean chequeDetailsPanelShow) {
		this.chequeDetailsPanelShow = chequeDetailsPanelShow;
	}

	public ChequeBook getChequeBook() {
		return chequeBook;
	}

	public void setChequeBook(ChequeBook chequeBook) {
		this.chequeBook = chequeBook;
	}

	public ChequeBookService getChequeBookService() {
		return chequeBookService;
	}

	public void setChequeBookService(ChequeBookService chequeBookService) {
		this.chequeBookService = chequeBookService;
	}

	public List<UploadedFile> getVendorFiles() {
		return vendorFiles;
	}

	public void setVendorFiles(List<UploadedFile> vendorFiles) {
		this.vendorFiles = vendorFiles;
	}

	public VendorDocumentsService getVendorDocumentsService() {
		return vendorDocumentsService;
	}

	public void setVendorDocumentsService(VendorDocumentsService vendorDocumentsService) {
		this.vendorDocumentsService = vendorDocumentsService;
	}

	public List<UploadedFile> getExpenseDocuments() {
		return expenseDocuments;
	}

	public void setExpenseDocuments(List<UploadedFile> expenseDocuments) {
		this.expenseDocuments = expenseDocuments;
	}

	public List<UploadedFile> getPaymentDocuments() {
		return paymentDocuments;
	}

	public void setPaymentDocuments(List<UploadedFile> paymentDocuments) {
		this.paymentDocuments = paymentDocuments;
	}

	public List<String> getVendorFileNames() {
		return vendorFileNames;
	}

	public void setVendorFileNames(List<String> vendorFileNames) {
		this.vendorFileNames = vendorFileNames;
	}

	public List<VendorDocuments> getVendorDocumentsList() {
		return vendorDocumentsList;
	}

	public void setVendorDocumentsList(List<VendorDocuments> vendorDocumentsList) {
		this.vendorDocumentsList = vendorDocumentsList;
	}

	public boolean isPrimaryAccountCheck() {
		return primaryAccountCheck;
	}

	public void setPrimaryAccountCheck(boolean primaryAccountCheck) {
		this.primaryAccountCheck = primaryAccountCheck;
	}

	public List<String> getExpenseFileName() {
		return expenseFileName;
	}

	public void setExpenseFileName(List<String> expenseFileName) {
		this.expenseFileName = expenseFileName;
	}

	public List<String> getPaymentFileName() {
		return paymentFileName;
	}

	public void setPaymentFileName(List<String> paymentFileName) {
		this.paymentFileName = paymentFileName;
	}

	public BankInternalService getBankInternalService() {
		return bankInternalService;
	}

	public void setBankInternalService(BankInternalService bankInternalService) {
		this.bankInternalService = bankInternalService;
	}

	public ChequeNoService getChequeNoService() {
		return chequeNoService;
	}

	public void setChequeNoService(ChequeNoService chequeNoService) {
		this.chequeNoService = chequeNoService;
	}

	public Long getChequeStartNo() {
		return chequeStartNo;
	}

	public void setChequeStartNo(Long chequeStartNo) {
		this.chequeStartNo = chequeStartNo;
	}

	public Long getChequeEndNo() {
		return chequeEndNo;
	}

	public void setChequeEndNo(Long chequeEndNo) {
		this.chequeEndNo = chequeEndNo;
	}

	public List<ChequeNo> getChequeList() {
		return chequeList;
	}

	public void setChequeList(List<ChequeNo> chequeList) {
		this.chequeList = chequeList;
	}

	public Set<PaymentDetail> getPaymentDetailsSet() {
		return paymentDetailsSet;
	}

	public void setPaymentDetailsSet(Set<PaymentDetail> paymentDetailsSet) {
		this.paymentDetailsSet = paymentDetailsSet;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public BankInternalCardService getBankInternalCardService() {
		return bankInternalCardService;
	}

	public void setBankInternalCardService(BankInternalCardService bankInternalCardService) {
		this.bankInternalCardService = bankInternalCardService;
	}

	public List<BankInternalCard> getCards() {
		return cards;
	}

	public void setCards(List<BankInternalCard> cards) {
		this.cards = cards;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public BankInternalCard getBankInternalCard() {
		return bankInternalCard;
	}

	public void setBankInternalCard(BankInternalCard bankInternalCard) {
		this.bankInternalCard = bankInternalCard;
	}

	public List<ChequeNo> getCheques() {
		return cheques;
	}

	public void setCheques(List<ChequeNo> cheques) {
		this.cheques = cheques;
	}

	public ChequeNo getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(ChequeNo chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getCheckno() {
		return checkno;
	}

	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}

	public Bank getVendorPromaryAccount() {
		return vendorPromaryAccount;
	}

	public void setVendorPromaryAccount(Bank vendorPromaryAccount) {
		this.vendorPromaryAccount = vendorPromaryAccount;
	}

	public BankInternal getBankInternal() {
		return bankInternal;
	}

	public void setBankInternal(BankInternal bankInternal) {
		this.bankInternal = bankInternal;
	}

	public List<BankInternal> getBankInternalList() {
		return bankInternalList;
	}

	public void setBankInternalList(List<BankInternal> bankInternalList) {
		this.bankInternalList = bankInternalList;
	}

	public BankInternalCard getInternalCard() {
		return internalCard;
	}

	public void setInternalCard(BankInternalCard internalCard) {
		this.internalCard = internalCard;
	}

	public List<BankInternalCard> getInternalCardList() {
		return internalCardList;
	}

	public void setInternalCardList(List<BankInternalCard> internalCardList) {
		this.internalCardList = internalCardList;
	}

	public BankInternal getInternalBank() {
		return internalBank;
	}

	public void setInternalBank(BankInternal internalBank) {
		this.internalBank = internalBank;
	}

	public Integer getInternalBankId() {
		return internalBankId;
	}

	public void setInternalBankId(Integer internalBankId) {
		this.internalBankId = internalBankId;
	}

	public LazyDataModel<Expense> getExpenseLazyModel() {
		return expenseLazyModel;
	}

	public void setExpenseLazyModel(LazyDataModel<Expense> expenseLazyModel) {
		this.expenseLazyModel = expenseLazyModel;
	}

	public CashBook getCashbook() {
		return cashbook;
	}

	public void setCashbook(CashBook cashbook) {
		this.cashbook = cashbook;
	}

	public List<CashBook> getCashbooks() {
		return cashbooks;
	}

	public void setCashbooks(List<CashBook> cashbooks) {
		this.cashbooks = cashbooks;
	}

	public boolean isCashDetailsPanelSelector() {
		return cashDetailsPanelSelector;
	}

	public void setCashDetailsPanelSelector(boolean cashDetailsPanelSelector) {
		this.cashDetailsPanelSelector = cashDetailsPanelSelector;
	}

	public String getReportFor() {
		return reportFor;
	}

	public void setReportFor(String reportFor) {
		this.reportFor = reportFor;
	}

	public boolean isDateRangePanelSelector() {
		return dateRangePanelSelector;
	}

	public void setDateRangePanelSelector(boolean dateRangePanelSelector) {
		this.dateRangePanelSelector = dateRangePanelSelector;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public BigDecimal getTotalCredit() {
		return totalCredit;
	}



	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}



	public BigDecimal getTotalDebit() {
		return totalDebit;
	}



	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}



	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<CashBookDto> getCashBookList() {
		return cashBookList;
	}



	public void setCashBookList(List<CashBookDto> cashBookList) {
		this.cashBookList = cashBookList;
	}



	public BigDecimal getOpengingTotalCredit() {
		return opengingTotalCredit;
	}



	public void setOpengingTotalCredit(BigDecimal opengingTotalCredit) {
		this.opengingTotalCredit = opengingTotalCredit;
	}



	public BigDecimal getOpeningTotalDebit() {
		return openingTotalDebit;
	}



	public void setOpeningTotalDebit(BigDecimal openingTotalDebit) {
		this.openingTotalDebit = openingTotalDebit;
	}



	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}



	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}



	public boolean isShowCashDetailsPanel() {
		return showCashDetailsPanel;
	}



	public void setShowCashDetailsPanel(boolean showCashDetailsPanel) {
		this.showCashDetailsPanel = showCashDetailsPanel;
	}



	public CashBookDto getSelectedCashBookDto() {
		return selectedCashBookDto;
	}



	public void setSelectedCashBookDto(CashBookDto selectedCashBookDto) {
		this.selectedCashBookDto = selectedCashBookDto;
	}



	public PaymentDetail getPaymentDetailForReport() {
		return paymentDetailForReport;
	}



	public void setPaymentDetailForReport(PaymentDetail paymentDetailForReport) {
		this.paymentDetailForReport = paymentDetailForReport;
	}



	public List<CardBookDto> getCardBookList() {
		return cardBookList;
	}



	public void setCardBookList(List<CardBookDto> cardBookList) {
		this.cardBookList = cardBookList;
	}



	public boolean isShowCardDetailsPanel() {
		return showCardDetailsPanel;
	}



	public void setShowCardDetailsPanel(boolean showCardDetailsPanel) {
		this.showCardDetailsPanel = showCardDetailsPanel;
	}



	public CardBookDto getSelectedCardBookDto() {
		return selectedCardBookDto;
	}



	public void setSelectedCardBookDto(CardBookDto selectedCardBookDto) {
		this.selectedCardBookDto = selectedCardBookDto;
	}



	public List<NeftBookDto> getNeftBookList() {
		return neftBookList;
	}



	public void setNeftBookList(List<NeftBookDto> neftBookList) {
		this.neftBookList = neftBookList;
	}



	public boolean isShowNeftDetailsPanel() {
		return showNeftDetailsPanel;
	}



	public void setShowNeftDetailsPanel(boolean showNeftDetailsPanel) {
		this.showNeftDetailsPanel = showNeftDetailsPanel;
	}



	public NeftBookDto getSelectedNeftBookDto() {
		return selectedNeftBookDto;
	}



	public void setSelectedNeftBookDto(NeftBookDto selectedNeftBookDto) {
		this.selectedNeftBookDto = selectedNeftBookDto;
	}



	public List<ChequeBookDto> getChequeBookList() {
		return chequeBookList;
	}



	public void setChequeBookList(List<ChequeBookDto> chequeBookList) {
		this.chequeBookList = chequeBookList;
	}



	public boolean isShowChequeDetailsPanel() {
		return showChequeDetailsPanel;
	}



	public void setShowChequeDetailsPanel(boolean showChequeDetailsPanel) {
		this.showChequeDetailsPanel = showChequeDetailsPanel;
	}



	public ChequeBookDto getSelectedChequeBookDto() {
		return selectedChequeBookDto;
	}



	public void setSelectedChequeBookDto(ChequeBookDto selectedChequeBookDto) {
		this.selectedChequeBookDto = selectedChequeBookDto;
	}



	public CashBookDocumentsService getCashBookDocumentsService() {
		return cashBookDocumentsService;
	}



	public void setCashBookDocumentsService(CashBookDocumentsService cashBookDocumentsService) {
		this.cashBookDocumentsService = cashBookDocumentsService;
	}



	public List<UploadedFile> getCashFiles() {
		return cashFiles;
	}



	public void setCashFiles(List<UploadedFile> cashFiles) {
		this.cashFiles = cashFiles;
	}



	public List<String> getCashFileNames() {
		return cashFileNames;
	}



	public void setCashFileNames(List<String> cashFileNames) {
		this.cashFileNames = cashFileNames;
	}



	public Integer getExpenseAccountId() {
		return expenseAccountId;
	}



	public void setExpenseAccountId(Integer expenseAccountId) {
		this.expenseAccountId = expenseAccountId;
	}



	public double getCalculateExpense() {
		return calculateExpense;
	}



	public void setCalculateExpense(double calculateExpense) {
		this.calculateExpense = calculateExpense;
	}



	public List<Expense> getExpenseDetailsList() {
		return expenseDetailsList;
	}



	public void setExpenseDetailsList(List<Expense> expenseDetailsList) {
		this.expenseDetailsList = expenseDetailsList;
	}



	public List<PaymentDetailsDto> getPaymentDetailsDtoList() {
		return paymentDetailsDtoList;
	}



	public void setPaymentDetailsDtoList(List<PaymentDetailsDto> paymentDetailsDtoList) {
		this.paymentDetailsDtoList = paymentDetailsDtoList;
	}



}