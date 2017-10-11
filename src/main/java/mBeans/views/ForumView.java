package mBeans.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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
import db.entities.RegisteredUser;
import db.entities.UsersProfile;
import db.storage.FileStorageHandler;
import mBeans.SessionBean;

@ManagedBean(name = "forumView")
@RequestScoped
public class ForumView {
	@ManagedProperty(value="#{SessionB}")
	private SessionBean sessionData;

	@ManagedProperty(value="#{adbean}")
	Advertisement adbean;
	
	private RegisteredUser user =null;
	private List<Advertisement> ads = new LinkedList<>();
	
	private LinkedList<MediaFile> fileList = new LinkedList<>();
	
	private List<UsersProfile>  advertisers = new LinkedList<UsersProfile>();

	private Part file;
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

	private List<Account> accounts;

	public ForumView(){
		faceCon=FacesContext.getCurrentInstance();
		servReq=(HttpServletRequest)faceCon.getExternalContext().getRequest();		
	}
	
	@PostConstruct
	public void init(){
		user = db.findUser(sessionData.getEmail());
		ads = db.getAllAds();
		accounts = db.getChannels(sessionData.getEmail());
		advertisers = db.getAdvertisers();
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
	
	public String submit(){
		db.save(adbean);
		//saveFile();
		return "Advertise?faces-redirect=false";
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

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	
	public String deleteAdvertisement(String strId){
		Long id = Long.valueOf(strId);
		FileStorageHandler storage = new FileStorageHandler();
		Advertisement ad = db.findAdvertisement(id);
		storage.releaseResources(ad);
		for(Advertisement a:ads){
			if(a.getId() == ad.getId()){
				ads.remove(a);
				break;
			}
		}
		db.deleteAdvertisement(id);
		return "Advertise?faces-redirect=false";
	}

	public List<Advertisement> getAds() {
		return ads;
	}

	public void setAds(List<Advertisement> ads) {
		this.ads = ads;
	}
	
	public boolean hasChannelAttached(String chanName){
		System.out.println("Has channels called"+chanName);
		for(Account ac:accounts){
			System.out.println(ac.getType());
			if(ac.getType().equals(chanName)){
				return true;
			}
		}		
		return false;
	}
	
	public double getOpacity(String str){
		if(hasChannelAttached(str)){
			return 1;
		}else{
			return 0.5;
		}
	}

	public List<UsersProfile> getAdvertisers() {
		return advertisers;
	}

	public void setAdvertisers(List<UsersProfile> advertisers) {
		this.advertisers = advertisers;
	}
}
