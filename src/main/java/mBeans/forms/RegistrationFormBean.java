package mBeans.forms;

import javax.faces.application.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.*;
import javax.inject.Inject;
import javax.servlet.http.*;

import db.SqlDb;
import utilities.Validator;

@ManagedBean(name="register")
@RequestScoped
public class RegistrationFormBean {

	private String name;
	private String password;
	private String email;
	private String emailCopy;
	private String paypalNumber;
	private String bankNumber;

	private final HttpServletRequest servreq;
	private final FacesContext faceCon;
	private FacesMessage msg;
	@Inject
	private SqlDb db;
	
	private boolean adProvider = false;
	private boolean company = false;


	public RegistrationFormBean(){

		faceCon=FacesContext.getCurrentInstance();
		servreq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	public String register() {
		if(name!=null && name!="" && password.length()>6 && Validator.isValidEmailAddress(email)){
			if(db.newUser(email, password, name)){
				msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Register success", null);
				faceCon.addMessage(null, msg);
				return "index";
			}
			else{
				msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error in registration.", null);
				faceCon.addMessage(null, msg);
				return "register";
			}
		}
		else
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong data introduced.", null);
		faceCon.addMessage(null, msg);
		return "register";
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmailCopy() {
		return emailCopy;
	}


	public void setEmailCopy(String emailCopy) {
		this.emailCopy = emailCopy;
	}


	public String getPaypalNumber() {
		return paypalNumber;
	}


	public void setPaypalNumber(String paypalNumber) {
		this.paypalNumber = paypalNumber;
	}


	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankAccountNumber) {
		this.bankNumber = bankAccountNumber;
	}

	public boolean isAdProvider() {
		return adProvider;
	}

	public void setAdProvider(boolean adProvider) {
		this.adProvider = adProvider;
	}

	public boolean isCompany() {
		return company;
	}

	public void setCompany(boolean company) {
		this.company = company;
	}

}
