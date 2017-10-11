package db.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.type.SerializableType;


@Entity
public class RegisteredUser implements Serializable{
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
	public static enum USER_ROLE {ADVERTISER,AFFILIATE};

	private String name = "";


	@Id
	private String email="";
	
	private USER_ROLE role = USER_ROLE.AFFILIATE;
	private String bank_number="";
	private String paypal_number="";
	@OneToMany(targetEntity=UserMessageReceived.class)
	@JoinColumn(name="recipient", referencedColumnName="email")
	private List<UserMessageReceived> messagesReceived = new LinkedList<>();
	
	@OneToMany(targetEntity=Advertisement.class)
	@JoinColumn(name="ownerEmail", referencedColumnName="email")
	private List<Advertisement> ads = new LinkedList<>();
	
	@OneToMany(targetEntity=UserMessageSent.class)
	@JoinColumn(name="sender", referencedColumnName="email")
	private List<UserMessageSent> messagesSent = new LinkedList<>();
	
	@OneToMany(targetEntity=Account.class)
	@JoinColumn(name="ownerEmail", referencedColumnName="email")
	private List<Account> accounts = new LinkedList<>();
	
	@OneToMany(targetEntity=Bid.class)
	@JoinColumn(name="ownerEmail", referencedColumnName="email")
	private List<Bid> bid = new LinkedList<>();
	
	@OneToMany(targetEntity=ActiveJob.class)
	@JoinTable(name="JOB_PARTICIPANT",joinColumns={@JoinColumn(name="ownerEmail",referencedColumnName="email")})
	private List<ActiveJob> activeJobs = new LinkedList<>();


	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Advertisement> getAds() {
		return ads;
	}

	public void setAds(List<Advertisement> ads) {
		this.ads = ads;
	}

	@OneToMany(targetEntity=UserMessageDeleted.class)
	@JoinColumn(name="owner", referencedColumnName="email")
	private List<UserMessageDeleted> messagesDeleted = new LinkedList<>();


	public List<UserMessageDeleted> getMessagesDeleted() {
		return messagesDeleted;
	}

	public void setMessagesDeleted(List<UserMessageDeleted> messagesDeleted) {
		this.messagesDeleted = messagesDeleted;
	}

	public List<UserMessageSent> getMessagesSent(){
		return messagesSent;
	}

	public List<UserMessageReceived> getMessagesReceived(){
		return messagesReceived;
	}

	private String password;
	public RegisteredUser(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.setPassword(password);
	}
	public RegisteredUser() {

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getBank_number() {
		return bank_number;
	}
	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}
	public String getPaypal_number() {
		return paypal_number;
	}
	public void setPaypal_number(String paypal_number) {
		this.paypal_number = paypal_number;
	}

	public void setMessagesReceived(LinkedList<UserMessageReceived> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	public void setMessagesSent(LinkedList<UserMessageSent> messagesSent) {
		this.messagesSent = messagesSent;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}
}
