package mBeans.channels;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.UserMessage;
import mBeans.SessionBean;

@ManagedBean(name = "channelsB")
@RequestScoped
public class ChannelsBean {
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;

	@Inject
	private SqlDb db;
	private FacesContext faceCon;
	private HttpServletRequest servReq;
	private FacesMessage msg;
	private LinkedList<Channel> list = new LinkedList<>();

	public ChannelsBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();		
	}
	
	@PostConstruct
	private void initList(){
		//TODO
	}

	

	public void setSessionData(SessionBean s){
		sessionData = s;
	}
	public SessionBean getSessionData(){
		return sessionData;
	}



	public LinkedList<Channel> getList() {
		return list;
	}



	public void setList(LinkedList<Channel> list) {
		this.list = list;
	}

}
