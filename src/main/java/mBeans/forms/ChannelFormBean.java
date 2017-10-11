package mBeans.forms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import db.SqlDb;
import db.entities.Account;
import db.entities.Advertisement;
import db.entities.MediaFile;
import mBeans.SessionBean;
import mBeans.views.AdvertiseView;
import mBeans.views.MyWallView;
@ManagedBean(name = "channelForm")
@RequestScoped
public class ChannelFormBean {
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;

	@ManagedProperty(value="#{adbean}")
	private Advertisement adbean;
	
	@ManagedProperty(value="#{myWallView}")
	private MyWallView myWall;

	public MyWallView getMyWall() {
		return myWall;
	}

	public void setMyWall(MyWallView myWall) {
		this.myWall = myWall;
	}

	@ManagedProperty(value="#{advertiseView}")
	private AdvertiseView advertiseView;

	public AdvertiseView getAdvertiseView() {
		return advertiseView;
	}

	public void setAdvertiseView(AdvertiseView advertiseView) {
		this.advertiseView = advertiseView;
	}

	private LinkedList<MediaFile> fileList = new LinkedList<>();
	public enum CHANNEL_TYPE{YOUTUBE,FACEBOOK,TWITTER,SNAPCHAT,LINKEDIN,INSTAGRAM,NON_SELECTED};
	private List<String> types = new LinkedList<>();
	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Map<String,String> channel_names =  new TreeMap<>();


	public String getName(String key) {
		return channel_names.get(key);
	}


	private Part file;
	private String channelId ="";
	private String password ="";
	private String type ="";
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



	public ChannelFormBean(){
		channel_names.put(CHANNEL_TYPE.NON_SELECTED.name(), "Select type");
		channel_names.put(CHANNEL_TYPE.FACEBOOK.name(), "Facebook");
		channel_names.put(CHANNEL_TYPE.LINKEDIN.name(), "Linkedin");
		channel_names.put(CHANNEL_TYPE.SNAPCHAT.name(), "Snapchat");
		channel_names.put(CHANNEL_TYPE.YOUTUBE.name(), "Youtube");
		channel_names.put(CHANNEL_TYPE.TWITTER.name(), "Twitter");
		channel_names.put(CHANNEL_TYPE.INSTAGRAM.name(), "Instagram");
		types.add(CHANNEL_TYPE.NON_SELECTED.name());
		for(CHANNEL_TYPE t:CHANNEL_TYPE.values()){
			if(t == CHANNEL_TYPE.NON_SELECTED) continue;
			types.add(t.name());
		}

		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();		
	}

	@PostConstruct
	public void init(){

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

	public void setSessionData(SessionBean s){
		sessionData = s;
	}
	public SessionBean getSessionData(){
		return sessionData;
	}

	public void setAdbean(Advertisement adv){
		adbean = adv;
	}
	public Advertisement getAdbean(){
		return adbean;
	}

	public Part getFile() {
		return file;
	}


	public void setFile(Part file) {
		this.file = file;
	}


	public LinkedList<MediaFile> getFileList() {
		return fileList;
	}


	public void setFileList(LinkedList<MediaFile> files) {
		this.fileList = files;
	}

	public void valueChangeMethod(ValueChangeEvent e){
		System.out.println("called");
	}

	public void saveFile(){
		System.out.println("Save file requested");
		if(file == null){
			return;
		}
		try {
			setFileContent(file.getInputStream(),"");
			db.newMediaFile(sessionData.getEmail(), "any", file.getSubmittedFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPath(Object o){
		return "media\\"+o.toString();
	}

	public void setFileContent(InputStream in,String path) {
		String username = sessionData.getEmail();
		if(username == null || username.isEmpty()){
			return;
		}
		String p = System.getProperty("jboss.server.data.dir");
		System.out.println(p);
		File newFile = new File(p+"\\media\\"+sessionData.getEmail()+"\\"+file.getSubmittedFileName());
		File theDir = new File(p+"\\media\\");
		File theDir1 = new File(p+"\\media\\"+sessionData.getEmail());
		//Make directory if it doesn't esists
		if (!theDir.exists()) theDir.mkdir();
		if (!theDir1.exists()) theDir1.mkdir();
		FileOutputStream fw = null;
		byte[] b = new byte[512];
		int readBytes = 0;
		try {
			fw = new FileOutputStream(newFile);
			do{
				readBytes = in.read(b);
				if(readBytes < 1){
					break;
				}
				fw.write(b,0,readBytes);

			}while(readBytes > 0);
			fw.close();
			in.close();
		} catch (IOException e) { 			
			e.printStackTrace();
		}
		System.out.println("File uploaded successfully");
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String saveAccount(){
		Account ac  = new Account();
		ac.setOwnerEmail(sessionData.getEmail());
		ac.setPassword(password);
		ac.setType(type);
		ac.setLoginName(channelId);
		db.newAccount(ac);
		msg=new FacesMessage(FacesMessage.SEVERITY_INFO, "Form was successfully submitted.", null);
		FacesContext.getCurrentInstance().addMessage("channel_form", msg);
		myWall.appendAccount(ac);
		return "MyAdzwall?faces-redirect=false";
	}
}
