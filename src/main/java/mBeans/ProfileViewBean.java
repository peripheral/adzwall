package mBeans;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import db.SqlDb;
import db.entities.RegisteredUser;
import db.entities.UserMessage;


@ManagedBean(name = "profileB")
@RequestScoped
public class ProfileViewBean {
	private LinkedList<UserMessage> messageList = new LinkedList<UserMessage>();

	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;
	@ManagedProperty(value="#{userMessageBean}")
	private UserMessageBean messageBean;
	@ManagedProperty(value="#{mailBoxB}")
	private MailBoxBean mailBoxB;	
	@Inject
	private SqlDb db;

	private String label="Sender";

	public String getEmail(UserMessage m){
		if(m== null){
			return "";
		}
		if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
			return m.getSender();
		}else{
			return m.getRecipient();
		}
	}

	public MailBoxBean getMailBoxB() {
		return mailBoxB;
	}
	public void setMailBoxB(MailBoxBean mailBoxB) {
		this.mailBoxB = mailBoxB;
	}



	private Map<Long,Boolean> toDelete= new TreeMap<Long,Boolean>();

	private FacesContext faceCon;

	private HttpServletRequest servReq;

	private FacesMessage msg;
	@ManagedProperty(value = "#{param.reply}")
	private String email ="";
	private String message="";
	private String subject="";
	private long selected=-1;


	private boolean markAll = false;;



	public boolean isMarkAll() {
		return markAll;
	}
	public Map<Long, Boolean> getToDelete() {
		return toDelete;
	}

	public void setToDelete(Map<Long, Boolean> toDelete) {
		this.toDelete = toDelete;
	}
	public void setMarkAll(boolean markAll) {
		this.markAll = markAll;
	}

	public ProfileViewBean(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();
	}

	//Getter Setter for sessionData
	public SessionBean getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionBean sessionData) {
		this.sessionData = sessionData;
	}

	@Override
	protected void finalize() throws Throwable{
		super.finalize();
	}

	public LinkedList<UserMessage> getMessageList() {
		if(sessionData.isLogged()){
			messageList.clear();
			if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
				db.getReceivedMessageList(sessionData.getEmail(),messageList);
			}else{
				db.getSentMessageList(sessionData.getEmail(),messageList);
			}
		}
		for(UserMessage m:messageList){
			if(markAll){
				toDelete.put(m.getId(), true);
			}else{
				toDelete.put(m.getId(), false);
			}
		}
		return messageList;
	}

	public void setMessageList(LinkedList<UserMessage> messageList) {	
		this.messageList = messageList;
	}	
	public String sendMessage(){
		if(!sessionData.isLogged()){
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "You need to be logged in!", null);
			faceCon.addMessage(null, msg);
			return "new_message?faces-redirect=false";
		}
		RegisteredUser eReceiver = db.findUser(email.trim());
		if(eReceiver == null ){
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Recipient not found.", null);
			faceCon.addMessage(null, msg);
			return "new_message?faces-redirect=false";
		}
		if(message.isEmpty()){
			msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message field is empty", null);
			faceCon.addMessage(null, msg);
			return "new_message?faces-redirect=false";
		}
		else{
			Calendar c = Calendar.getInstance();
			db.newMessage(sessionData.getEmail(),eReceiver.getEmail(),message,c.getTimeInMillis(),subject);
			return "messages?faces-redirect=true";
		}
	}

	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getSelected() {
		return selected;
	}

	public void setSelected(long selected) {
		this.selected = selected;
	}

	public boolean isSelected(String id){
		System.out.println("id"+id);
		if(toDelete.containsKey(id)){
			return toDelete.get(id);		
		}else{
			return false;
		}
	}

	public String openMessage(String id){
		System.out.println("Open message called"+id);
		Long message_id = Long.valueOf(id);
		mailBoxB.setMailId(message_id);
		return "ReadMessage.xhtml?id="+id+"&type="+mailBoxB.getActiveDirectory()+"&faces-redirect=true";		
	}

	public String reply(){		
		System.out.println("Rep prssed"+messageBean.getSender());
		return "new_message?reply="+messageBean.getSender()+"&faces-redirect=true";
	}

	public void setMessageBean(UserMessageBean b){
		messageBean = b;
	}
	public UserMessageBean getMessageBean(){
		return messageBean;
	}

	public String deleteMessages(){	
		for(Long id: toDelete.keySet()){
			try{
				Long message_id = Long.valueOf(id);
				if(toDelete.get(id) !=null && toDelete.get(id)){
					System.out.println("Deleted"+id);
					if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
						db.deleteReceivedMessage(message_id,sessionData.getEmail());
					}else{
						db.deleteSentMessage(message_id,sessionData.getEmail());
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		System.out.println("Method called");
		return "messages?faces-redirect=false";
	}
	public String deleteMessage(String id){	
		try{
			System.out.println("Contains"+id);
			Long message_id = Long.valueOf(id);

			System.out.println("Deleted"+message_id);
			if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
				db.deleteReceivedMessage(message_id,sessionData.getEmail());
			}else{
				db.deleteSentMessage(message_id,sessionData.getEmail());
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("Method called");
		return "messages?faces-redirect=false";
	}

	public String useSentDirectory(){
		mailBoxB.setActiveDirectory(MailBoxBean.SENT);
		System.out.println("Directory set"+"sent");
		return "messages?faces-redirect=false"; 
	}
	public String useReceivedDirectory(){
		mailBoxB.setActiveDirectory( MailBoxBean.RECEIVED);
		System.out.println("Directory set"+MailBoxBean.RECEIVED);
		return "messages?faces-redirect=false"; 
	}
	public MailBoxBean getMailBoxb() {
		return mailBoxB;
	}
	public void setMailBoxb(MailBoxBean mailBoxb) {
		this.mailBoxB = mailBoxb;
	}

	public String getLabel() {
		if(mailBoxB.getActiveDirectory().equals(MailBoxBean.RECEIVED)){
			return "Sender";
		}else{
			return "Recipient";
		}
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String display(String command){
		System.out.println(command);
		return "Inloggad?faces-redirect=false";
	}
}
