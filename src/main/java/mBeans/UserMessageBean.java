package mBeans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.UserMessage;

@ManagedBean(name = "userMessageBean")
@RequestScoped
public class UserMessageBean {
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;

	@ManagedProperty(value="#{mailBoxB}")
	MailBoxBean mailBoxB;

	private String sender;
	private String topic;
	private String date;
	private String message;
	private long id=-1;
	
	@Inject
	private SqlDb db;
	
	private FacesContext faceCon;
	private HttpServletRequest servReq;
	private FacesMessage msg;

	private String recipient;

	public UserMessageBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();		
	}
	
	@PostConstruct
	public void init(){
		if(mailBoxB.getMailId() != -1 && mailBoxB.getActiveDirectory() != null){
			System.out.println(mailBoxB.getMailId() + mailBoxB.getActiveDirectory());
			getUserMessage(mailBoxB.getMailId(),mailBoxB.getActiveDirectory() );
		}
	}

	UserMessage uMessage=null;
	private void getUserMessage(Long id,String type) {
		try {
			System.out.println(id + sessionData.getEmail() +type+db);
			uMessage = db.getUserMessage(id,sessionData.getEmail(),type);
			if(uMessage != null){
				setValues(uMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to fetch", null);
			faceCon.addMessage(null, msg);
		}
	}

	public UserMessageBean(String sender, String message,String subject, int id, String date) {
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.setId(id);
		topic = subject;
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	private void setValues(UserMessage uM) {
		this.sender = uM.getSender();
		this.message = uM.getMessage();
		this.date = uM.getDate();
		this.setId(uM.getId());
		topic = uM.getTopic();
		recipient = uM.getRecipient();
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
		this.date = date;
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
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage(String id){
		long message_id = -1;
		try{
			message_id = Long.valueOf(id);
		}catch(Exception e){
			return "Internal error.";
		}
		String message="No message found.";
		try {
			message = db.getUserMessage(message_id
					, sessionData.getEmail()
					, mailBoxB.getActiveDirectory()).getMessage();
		} catch (Exception e) {
			System.err.println(e);
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to fetch", null);
			faceCon.addMessage(null, msg);
			return message;
		}		
		return message;	
	}

	public void setSessionData(SessionBean s){
		sessionData = s;
	}
	public SessionBean getSessionData(){
		return sessionData;
	}

	public MailBoxBean getMailBoxB() {
		return mailBoxB;
	}
	
	public void setMailBoxB(MailBoxBean mailBoxB) {
		this.mailBoxB = mailBoxB;
	}
	
	public String getEmail(){
		if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
			return sender;
		}else{
			return recipient;
		}
	}
	
	public String getPrefix(){
		if(uMessage.isReceived()){
			return "From:";
		}else{
			return "To:";
		}
	}
}
