package db;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import db.entities.Account;
import db.entities.Advertisement;
import db.entities.MediaFile;
import db.entities.RegisteredUser;
import db.entities.User;
import db.entities.UserMessage;
import db.entities.UserMessageDeleted;
import db.entities.UserMessageReceived;
import db.entities.UserMessageSent;
import db.entities.UsersProfile;
import mBeans.MailBoxBean;
import mBeans.UserBean;
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateful
public class SqlDb {	

	public static final String DB_NAME = "adhub";

	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;  

	public void setEntityManager(EntityManager em){
		entityManager = em;
	}

	public EntityManager getEntityManager(){
		return entityManager;
	}

	public SqlDb(){

	}

	public String findUser(String email, String pass){
		RegisteredUser result = null;
		try{
			if(entityManager != null){			
				result = entityManager.find(RegisteredUser.class, email);
				if(result != null && result.getPassword().equals(pass)){
					return result.getName();
				}else{
					return null;
				}
			}else{
				System.out.println("em is null"+entityManager);
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * All new users are entered in to pending users untill they are to proceed
	 * @param email -email
	 * @param pass -password
	 * @param Username - 
	 * @return returns true on success, else false
	 */
	public boolean newUser(String email, String pass, String username){
		RegisteredUser rUser = new RegisteredUser(username, email, pass);
		if(entityManager != null){			
			entityManager.persist(rUser);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}
	}

	/**
	 * All new users are entered in to pending users untill they are to proceed
	 * @param email -email
	 * @param pass -password
	 * @param Username - 
	 * @return returns true on success, else false
	 */
	public boolean newRegisteredUser(String email, String pass, String username){
		RegisteredUser rUser = new RegisteredUser(username, email, pass);
		if(entityManager != null){			
			entityManager.persist(rUser);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}
	}

	public boolean newMediaFile(String email,String type,String fName){
		MediaFile mFile = new MediaFile(email, type, fName);
		if(entityManager != null){			
			entityManager.persist(mFile);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}
	}
	public boolean newMediaFile(String ownerEmail,String fName){
		MediaFile mFile = new MediaFile(ownerEmail, fName);
		if(entityManager != null){			
			entityManager.persist(mFile);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}
	}

	public void getReceivedMessageList(String email,LinkedList<UserMessage> list) {
		RegisteredUser rUser = new RegisteredUser();
		rUser.setEmail(email);
		if(entityManager != null){			
			rUser = entityManager.find(RegisteredUser.class,email);
			list.addAll(rUser.getMessagesReceived());
		}else{
			System.out.println("em is null"+entityManager);
		}
	}

	public RegisteredUser findUser(String email) {
		if(entityManager != null){
			RegisteredUser rUser = entityManager.find(RegisteredUser.class,email);
			if(rUser!= null){
				return rUser;
			}else{
				return null;
			}
		}else{
			System.out.println("em is null"+entityManager);
			return null;
		}
	}

	public boolean newMessage(String sender, String recipient, String message,
			long timeStamp, String subject) {
		UserMessageSent sMessage = new UserMessageSent(sender,recipient,subject,message,timeStamp);
		UserMessageReceived rMessage = new UserMessageReceived(sender,recipient,subject,message,timeStamp);
		if(entityManager != null){
			entityManager.persist(sMessage);
			entityManager.persist(rMessage);
		}else{
			return false;
		}
		return true;		
	}

	public UserMessage getUserMessage(long message_id, String email, String type) {
		UserMessage uMessage=null;
		if(entityManager != null){
			if(type.equals(MailBoxBean.RECEIVED)){
				uMessage = entityManager.find(UserMessageReceived.class,message_id );
				if(uMessage.getRecipient().equals(email)){
					return uMessage;
				}else{
					return null;
				}
			}else if(type.equals(MailBoxBean.SENT)){
				uMessage = entityManager.find(UserMessageSent.class,message_id );
				if(uMessage.getSender().equals(email)){
					return uMessage;
				}else{
					return null;
				}
			}else{
				UserMessageDeleted mess = entityManager.find(UserMessageDeleted.class,message_id );
				if(mess.getOwner().equals(email)){
					return mess;
				}else{
					return null;
				}

			}
		}
		else{
			return null;
		}
	}

	public void getSentMessageList(String email,LinkedList<UserMessage> list) {
		if(entityManager != null){					
			list.addAll(entityManager.find(RegisteredUser.class,email).getMessagesSent());
		}else{
			System.out.println("em is null"+entityManager);
		}
	}

	public boolean deleteSentMessage(Long message_id, String email) {
		UserMessageSent mSent = null;		
		if(entityManager != null){
			mSent = entityManager.find(UserMessageSent.class,message_id);
			if(mSent.getSender().equals(email)){
				entityManager.remove(mSent);
				return true;
			}else{
				return true;
			}
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}
	}

	public boolean deleteReceivedMessage(Long message_id, String email) {
		UserMessageReceived mReceived = null;
		if(entityManager != null){
			mReceived = entityManager.find(UserMessageReceived.class,message_id);
			if(mReceived.getRecipient().equals(email)){
				entityManager.remove(mReceived);
				return true;
			}else{
				return true;
			}
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}

	}

	public UserBean getUser(Long id, String table) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Advertisement adbean) {
		if(entityManager != null){
			entityManager.persist(adbean);
		}else{
			System.out.println("em is null"+entityManager);
		}
	}

	public void getNewUsers(LinkedList<User> newUsers) {
		// TODO Auto-generated method stub

	}

	public void newAcceptedUser(Long id) {
		// TODO Auto-generated method stub

	}

	public boolean copyToDeleted(Long message_id, String email, String type) {
		if(type.equals(MailBoxBean.SENT)){
			UserMessageSent mSent = null;		
			if(entityManager != null){
				mSent = entityManager.find(UserMessageSent.class,message_id);
				if(mSent.getSender().equals(email)){
					UserMessageDeleted mess = new UserMessageDeleted(mSent,email);
					entityManager.persist(mess);
					return true;
				}else{
					return true;
				}
			}else{
				System.out.println("em is null"+entityManager);
				return false;
			}	
		}else if(type.equals(MailBoxBean.RECEIVED)){
			UserMessageReceived mReceived = null;
			if(entityManager != null){
				mReceived = entityManager.find(UserMessageReceived.class,message_id);
				if(mReceived.getRecipient().equals(email)){
					UserMessageDeleted mess = new UserMessageDeleted(mReceived,email);
					entityManager.persist(mess);
					return true;
				}else{
					return true;
				}
			}else{
				System.out.println("em is null"+entityManager);
				return false;
			}
		}
		else{
			return false;
		}
	}

	public boolean deleteMessage(Long message_id, String email,
			String type) {
		if(type.equals(MailBoxBean.SENT)){
			UserMessageSent mSent = null;		
			if(entityManager != null){
				mSent = entityManager.find(UserMessageSent.class,message_id);
				if(mSent.getSender().equals(email)){
					UserMessageDeleted mess = new UserMessageDeleted(mSent,email);
					entityManager.persist(mess);
					return true;
				}else{
					return true;
				}
			}else{
				System.out.println("em is null"+entityManager);
				return false;
			}	
		}else if(type.equals(MailBoxBean.RECEIVED)){
			UserMessageReceived mReceived = null;
			if(entityManager != null){
				mReceived = entityManager.find(UserMessageReceived.class,message_id);
				if(mReceived.getRecipient().equals(email)){
					UserMessageDeleted mess = new UserMessageDeleted(mReceived,email);
					entityManager.persist(mess);
					return true;
				}else{
					return true;
				}
			}else{
				System.out.println("em is null"+entityManager);
				return false;
			}
		}
		else{
			UserMessageDeleted mess = entityManager.find(UserMessageDeleted.class,message_id);
			if(mess.getRecipient().equals(email)){
				entityManager.remove(mess);
				return true;
			}else{
				return false;
			}
		}

	}

	public void getMessageList(String email, LinkedList<UserMessage> list, String type) {
		if(type.equals(MailBoxBean.DELETED)){
			if(entityManager != null){					
				list.addAll(entityManager.find(RegisteredUser.class,email).getMessagesDeleted());
			}else{
				System.out.println("em is null"+entityManager);
			}
		}else{
			throw new RuntimeException("Not implemented");
		}
	}

	public Advertisement findAdvertisement(Long id) {
		if(entityManager != null){
			Advertisement ad = entityManager.find(Advertisement.class,id);
			if(ad!= null){
				return ad;
			}else{
				return null;
			}
		}else{
			System.out.println("em is null"+entityManager);
			return null;
		}

	}

	public boolean newMediaFile(Long id, String fName) {
		MediaFile mFile = new MediaFile(id, fName);
		if(entityManager != null){			
			entityManager.persist(mFile);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}		
	}

	public boolean deleteAdvertisement(Long id) {
		if(entityManager != null){			
			entityManager.remove(entityManager.find(Advertisement.class, id));
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}	
	}

	public List<Advertisement> getAds(String email) {
		RegisteredUser ru = findUser(email);
		List<Advertisement> ads = ru.getAds();
		for(@SuppressWarnings("unused") Advertisement ad:ads){
		}
		return ads;
	}

	public List<Account> getChannels(String email) {
		List<Account> acs = findUser(email).getAccounts();
		for(@SuppressWarnings("unused") Account ac:acs){
			
		}
		return acs;
	}

	public boolean newAccount(Account ac) {
		if(entityManager != null){			
			entityManager.persist(ac);
			return true;
		}else{
			System.out.println("em is null"+entityManager);
			return false;
		}	
		
	}

	public List<Advertisement> getAllAds() {
		if(entityManager != null){	
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Advertisement> cq = cb.createQuery(Advertisement.class);
			Root<Advertisement> ad = cq.from(Advertisement.class);
			cq.select(ad);
			TypedQuery<Advertisement> q = entityManager.createQuery(cq);
			List<Advertisement> allAdds = q.getResultList();
			for(Advertisement a:allAdds){
				for(@SuppressWarnings("unused") MediaFile f: a.getAttachedFiles()){
				}
			}
			return allAdds;
			
		}else{
			System.out.println("em is null"+entityManager);
			return null;
		}	
	}

	public List<UsersProfile> getAdvertisers() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsersProfile> cq = cb.createQuery(UsersProfile.class);
		Root<UsersProfile> advertiser = cq.from(UsersProfile.class);
		cq.select(advertiser);
		TypedQuery<UsersProfile> q = entityManager.createQuery(cq);
		List<UsersProfile> allUserProfiles = q.getResultList();
		return allUserProfiles;
	}
}
