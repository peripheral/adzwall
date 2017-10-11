package db.entities;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.type.SerializableType;


@Entity
public class MediaFile implements Serializable{
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
	private String type="";
	private String fName ="";
	private boolean persistet = false;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long adId;
	private String ownerEmail;
	private String password;
	public MediaFile(String ownerEmail, String type, String fName) {
		this.setOwnerEmail(ownerEmail);
		this.setType(type);
		this.setFName(fName);
	}
	
	public MediaFile(){}
	
	public MediaFile(String ownerEmail, String fName) {
		this.setOwnerEmail(ownerEmail);
		this.fName = fName;
	}
	public MediaFile(Long adId, String fName) {
		this.adId = adId;
		this.fName = fName;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String fName) {
		this.fName = fName;
	}
	
	public String toString(){
		return fName;
	}

	public boolean isPersistet() {
		return persistet;
	}

	public void setPersistet(boolean persistet) {
		this.persistet = persistet;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public Long getAdId() {
		return adId;
	}
	public void setAdId(Long adId) {
		this.adId = adId;
	}
}
