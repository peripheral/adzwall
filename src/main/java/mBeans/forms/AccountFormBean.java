package mBeans.forms;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.MediaFile;
import mBeans.SessionBean;
import mBeans.views.AdvertiseView;

@ManagedBean(name = "accountFormB")
@RequestScoped
public class AccountFormBean {
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;
	
	@ManagedProperty(value="#{advertiseView}")
	private AdvertiseView advertiseView;
	
	public AdvertiseView getAdvertiseView() {
		return advertiseView;
	}

	public void setAdvertiseView(AdvertiseView advertiseView) {
		this.advertiseView = advertiseView;
	}

	private LinkedList<MediaFile> fileList = new LinkedList<>();


	private String date;
	private long id=-1;
	@Inject
	private SqlDb db;
	private FacesContext faceCon;
	private HttpServletRequest servReq;
	private FacesMessage msg;

	private String recipient;



	public AccountFormBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();		
	}
	
	@PostConstruct
	public void init(){
		
	}


	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void setSessionData(SessionBean s){
		sessionData = s;
	}
	public SessionBean getSessionData(){
		return sessionData;
	}
	
	public LinkedList<MediaFile> getFileList() {
		return fileList;
	}


	public void setFileList(LinkedList<MediaFile> files) {
		this.fileList = files;
	}
	
	public void valueChangeMethod(ValueChangeEvent e){
		System.out.println("called");
	}
	
	public String getPath(Object o){
		return "media\\"+o.toString();
	}
}
