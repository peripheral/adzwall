package mBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;

@ManagedBean(name="SessionB")
@SessionScoped
public class SessionBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1080269443601312782L;



	private String password;
	private String name;
	private String email;
	private boolean isLogged=false;
	private String redirect ="";


	private  FacesContext faceCon;
	private final HttpServletRequest servReq;
	private FacesMessage msg;



	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;  
	
	@Inject
	private SqlDb db;


	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}



	public SessionBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String login() {		
		name=db.findUser(email, password);
		if(name!=null){
			isLogged=true;
			try{
				servReq.getSession().setAttribute("UserSession", email);
				servReq.getSession().setAttribute("SessionBean", this);
				msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Login success", null);
				faceCon = FacesContext.getCurrentInstance();
				faceCon.addMessage(null, msg);
			}catch(Exception e){
				e.printStackTrace();
				return "Profil?faces-redirect=false";
			}
			Map<String, String> map = FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterMap();
			if(redirect != null && !redirect.isEmpty()){
				try {
					faceCon.getExternalContext().redirect(redirect);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return redirect;
			}
			return "MyWallP?faces-redirect=true";
		}
		else{
			email = null;
			password = null;
			name = null;
			isLogged=false;
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Check your credentials", null);
			try{
				faceCon= FacesContext.getCurrentInstance();
				faceCon.addMessage(null, msg);
			}catch(Exception e){
				e.printStackTrace();
				return "index?faces-redirect=false";
			}
			return "index?faces-redirect=false";
		}
	}    

	public String logout() {

		email = null;
		password = null;
		name = null;
		isLogged=false;
		servReq.getSession().removeAttribute("SessionBean");
		System.out.println("Successfully logged out");
		return "index?faces-redirect=true";
	}    

	public boolean LoggedIn() {
		return name != null;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
}