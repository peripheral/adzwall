package db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GeneratorType;

@Entity
public class Bid {
		
	private String ownerEmail;
	private Long reqId;
	private int bid = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bid(){}
	
	/**
	 * @param owner - owner email as String
	 * @param reqId - id of the Advertisement request;
	 */
	public Bid(String owner,Long reqId){
		ownerEmail = owner;
		this.reqId = reqId;
	}
	
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public Long getReqId() {
		return reqId;
	}
	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
	
}
