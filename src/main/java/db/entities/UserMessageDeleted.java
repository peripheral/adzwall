package db.entities;

import javax.persistence.Entity;

@Entity
public class UserMessageDeleted extends UserMessage {
	private String owner ="";
	
	public UserMessageDeleted(){}
	
	public UserMessageDeleted(UserMessageReceived mReceived, String owner) {
		setDate(mReceived.getDate());
		setMessage(mReceived.getMessage());
		setOwner(owner);
		setRecipient(mReceived.getRecipient());
		setSender(mReceived.getSender());
		setTimeStamp(mReceived.getTimeStamp());
		setTopic(mReceived.getTopic());
		setReceived(true);
	}

	public UserMessageDeleted(UserMessageSent mSent, String owner) {
		setDate(mSent.getDate());
		setMessage(mSent.getMessage());
		setOwner(owner);
		setRecipient(mSent.getRecipient());
		setSender(mSent.getSender());
		setTimeStamp(mSent.getTimeStamp());
		setTopic(mSent.getTopic());
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
