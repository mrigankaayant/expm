package com.ayantsoft.expm.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.expm.hibernate.dao.VendorDao;
import com.ayantsoft.expm.hibernate.pojo.Account;
import com.ayantsoft.expm.hibernate.pojo.Vendor;

@ManagedBean
@ApplicationScoped
public class VendorService implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9093207945843544908L;
	
	
	@ManagedProperty("#{vendorDao}")
	private VendorDao vendorDao;
	
	
	public void saveVendor(Vendor vendor){
		vendorDao.save(vendor);
	}
	
	public List<Vendor> getVendorList(){
		return vendorDao.findVendorList();
	}
	
	public Vendor getVendorById(int vendorId){
		return vendorDao.findVendorById(vendorId);
	} 


	// setter and getter
	
	public VendorDao getVendorDao() {
		return vendorDao;
	}


	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}
}
