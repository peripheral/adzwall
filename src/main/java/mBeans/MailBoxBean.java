package mBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="mailBoxB")
@SessionScoped
public class MailBoxBean {
	private String activeDirectory=null;
	private long mailId = -1;
	public static final String RECEIVED = "received";
	public static final String SENT = "sent";
	
	public static final String DELETED = "deleted";
	
	public String getDELETED(){
		return DELETED;
	}
	
	public String getRECEIVED(){
		return RECEIVED;
	}
	
	public String getSENT(){
		return SENT;
	}
	
	public MailBoxBean(){
		activeDirectory = SENT;
	}
	public String getActiveDirectory() {
		return activeDirectory;
	}

	public void setActiveDirectory(String activeDirectory) {
		System.out.println("Directory set"+activeDirectory);
		this.activeDirectory = activeDirectory;
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
}
