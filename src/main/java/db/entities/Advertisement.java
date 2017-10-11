package db.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@ManagedBean(name="adbean")
@Entity
public class Advertisement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String TABLE_PENDING = "pending_users";
	public static final String TABLE_ACCEPTED = "users";
	public static final String ID = "id";
	private Long supplierId;
	private String ownerEmail;
	private String link;
	private String title="";
	private String path ="";
	private Boolean yt = false;
	private Boolean fb = false;
	private Boolean sn = false;
	private Boolean ln = false;
	private Boolean tw = false;
	private Boolean instagram = false;
	private Boolean allchannels = false;
	
	@Column(columnDefinition="TEXT")
	private String description ="";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@OneToMany
	@JoinColumn(name="reqId", referencedColumnName="id")
	private List<Bid> bids = new LinkedList<Bid>();
	
	@OneToMany(targetEntity=MediaFile.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="adId", referencedColumnName="id")
	private List<MediaFile> attachedFiles = new LinkedList<>();
	
	public List<MediaFile> getAttachedFiles() {
		return attachedFiles;
	}

	public void setAttachedFiles(List<MediaFile> attachedFiles) {
		this.attachedFiles = attachedFiles;
	}
	
	public Advertisement(){}
	
	public Advertisement(Long supplierId, String link) {
		this.setSupplierId(supplierId);
		this.link = link;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link=link;
	}	
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getYt() {
		return yt;
	}

	public void setYt(Boolean yt) {
		this.yt = yt;
	}

	public Boolean getFb() {
		return fb;
	}

	public void setFb(Boolean fb) {
		this.fb = fb;
	}

	public Boolean getSn() {
		return sn;
	}

	public void setSn(Boolean sn) {
		this.sn = sn;
	}

	public Boolean getLn() {
		return ln;
	}

	public void setLn(Boolean ln) {
		this.ln = ln;
	}

	public Boolean getTw() {
		return tw;
	}

	public void setTw(Boolean tw) {
		this.tw = tw;
	}


	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Boolean getAllchannels() {
		return allchannels;
	}

	public void setAllchannels(Boolean allchannels) {
		this.allchannels = allchannels;
	}

	public Boolean getInstagram() {
		return instagram;
	}

	public void setInstagram(Boolean instagram) {
		this.instagram = instagram;
	}
}
