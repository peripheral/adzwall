package db.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.type.SerializableType;


@Entity
public class AdvertisemetRequest implements Serializable{
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
	private Long supplierId ;
	private Long recipientId;
	private Long advertisementId;
	
	public AdvertisemetRequest() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public AdvertisemetRequest(Long supplierId, Long recipientId, Long advertisementId) {
		this.supplierId = supplierId;
		this.recipientId = recipientId;
		this.advertisementId = advertisementId;
	}
	
	public Long getName() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public long getSupplierId() {
		return recipientId;
	}
	
	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}
	
	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}
}
