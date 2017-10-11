package db.entities;

import javax.persistence.Entity;

@Entity
public class UserMessageSent extends UserMessage {

	public UserMessageSent(String sender, String recipient, String subject,
			String message, long timeStamp) {
		super(sender, recipient, subject, message, timeStamp);
	}
	
	public UserMessageSent(){}	
}
