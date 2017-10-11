package db.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class UserMessage {
	private String sender;
	private String recipient;
	private String topic;
	private String date;
	@Column(columnDefinition = "TEXT")
	private String message;
	private Long timeStamp;
	
	private boolean received = false;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public UserMessage(String sender, String message,String subject, String date) {
		this.sender = sender;
		this.message = message;
		this.date = date;
		topic = subject;
	}
	public UserMessage(String sender, String recipient, String message,
			String subject, int id, String date) {
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.setId(id);
		topic = subject;
		this.setRecipient(recipient);
		
	}
	
	public UserMessage() {
	}
	
	public UserMessage(String sender, String recipient, String subject,
			String message, long timeStamp) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeStamp);
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
		this.date = c.getTime().toString();
		this.timeStamp = timeStamp;
		topic = subject;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date.replaceAll("\\n","");
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getId() {
		return id;
	}
	public String getId(String s) {
		return id+"";
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}
}
