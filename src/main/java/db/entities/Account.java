package db.entities;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String TABLE_PENDING = "pending_users";
	public static final String TABLE_ACCEPTED = "users";
	public static final String ID = "id";
	public static enum TYPE{FACEBOOK,LINKEDIN,YOUTUBE,TWITTER,SNAPCHAT,INSTAGRAM,UNKNOWN};
	private Long supplierId;
	private String ownerEmail;
	private String link;
	private String attachedFile;
	private String title="";
	private String path ="";
	private String loginName;
	private String password;
	private String type = "";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Account(){}
	
	public Account(String name,String t) {
		type = t;
		setLoginName(name);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link=link;
	}
	
	public String getAttachedFile() {
		return attachedFile;
	}
	
	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getType() {
		return type;		
	}
	
	public void setType(String str){
		type = str;		
	}
}
