package mBeans;



import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
@ManagedBean(name="mailHandler")
public class MailHandler {
	private String senderName="";
	private String senderMail="";
	private String subject ="";
	private String content="";
	private  FacesContext faceCon;
	private final HttpServletRequest servReq;
	private FacesMessage msg;
	
	public MailHandler() {
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	
	public String sendMail(){
		Email email = new SimpleEmail();
		email.setHostName("send.one.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("noreply@adzwall.com", "12345678"));
		email.setSSLOnConnect(true);
		try {
			email.setFrom("noreply@adzwall.com");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		email.setSubject(subject);
		try {
			email.setMsg("Sent from:"+senderMail+"\n"+
					"Sender name:"+senderName+"\n"+
					content);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			email.addTo("contactus@adzwall.com");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Message Sent", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		System.out.println("Mail sent"+senderName);
		return "Contact?faces-redirect=false";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
