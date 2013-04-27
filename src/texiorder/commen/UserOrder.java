package texiorder.commen;

public class UserOrder {
	
	private String alias;
	private String ack;
	private int    queueNumber;
	private String address;
	private String command;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAck() {
		return ack;
	}
	public void setAck(String ack) {
		this.ack = ack;
	}
	public int getQueueNumber() {
		return queueNumber;
	}
	public void setQueueNumber(int queueNumber) {
		this.queueNumber = queueNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	

}
