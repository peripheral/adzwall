package mBeans;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.UserMessage;


@ManagedBean(name = "navigationBean")
@RequestScoped
public class NavigationBean {	

	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;
	@Inject
	private SqlDb db;

	private FacesContext faceCon;

	private HttpServletRequest servReq;

	private boolean markAll = false;

	public boolean isMarkAll() {
		return markAll;
	}

	public void setMarkAll(boolean markAll) {
		this.markAll = markAll;
	}

	public NavigationBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	//Getter Setter for sessionData
	public SessionBean getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionBean sessionData) {
		this.sessionData = sessionData;
	}

	public String main(){	
		if(sessionData.isLogged()){
			return "Profil?faces-redirect=true";
		}else{
			return "Profil?faces-redirect=false";
		}
	}

}
