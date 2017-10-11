package db.entities;

import javax.persistence.Entity;

@Entity
public class UserMessageReceived extends UserMessage {

	public UserMessageReceived(String sender, String recipient, String subject,
			String message, long timeStamp) {
		super(sender, recipient, subject, message, timeStamp);
	}
	
	public UserMessageReceived(){}	
}
