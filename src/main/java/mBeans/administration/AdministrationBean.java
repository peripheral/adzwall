package mBeans.administration;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.User;
import mBeans.UserBean;

public class AdministrationBean {
	private LinkedList<User> newUsers = new LinkedList<>();
	private SqlDb db;
	private FacesContext faceCon;
	private HttpServletRequest servReq;
	private Exception e = null;
	@ManagedProperty(name="userB", value = "#{userB}")
	private UserBean userB = null;
	private String table;
	
	public UserBean getUserB() {
		return userB;
	}

	public void setUserB(UserBean userB) {
		this.userB = userB;
	}

	public AdministrationBean() {
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();

	}
	
	@PostConstruct
	private void init(){
		try{
			db.getNewUsers(newUsers);
		}catch(Exception e){
			this.e = e;
		}
	}
	
	public LinkedList<User> getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(LinkedList<User> newUsers) {
		this.newUsers = newUsers;
	}
	
	public void acceptUser(Long id) {
		try {
			db.newAcceptedUser(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String openUser(Long id){
		if(userB == null){
			userB = db.getUser(id,table);
			userB.setId(id);
		}
		return "user_profile?faces-redirect=true";
	}
}
