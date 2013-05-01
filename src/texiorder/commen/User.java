package texiorder.commen;

public class User {
	private String   id;
	private String address;
	private boolean inWaitingList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isInWaitingList() {
		return inWaitingList;
	}
	public void setWaiting() {
		inWaitingList = true;
	}
	public void setProcessing() {
		inWaitingList = false;
	}
}
