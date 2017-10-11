package mBeans.channels;

public class Channel {
	private long id = -1;
	private String channelName = "";
	private String owner= "";
	
	public Channel(long id,String cname,String o){
		this.id = id;
		this.channelName = cname;
		this.owner = o;
	}
	public Channel(String name, String owner) {
		this.channelName = name;
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
