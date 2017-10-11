package db.entities;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.type.SerializableType;


@Entity
public class UsersProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name = "";
	private String ownerEmail="";
	private String location ="Not provided";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String password;

	public UsersProfile(){}
	
	public UsersProfile(String name, String email, String password) {
		this.name = name;
		this.setOwnerEmail(email);
		this.setPassword(password);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
