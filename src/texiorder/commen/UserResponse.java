package texiorder.commen;

public class UserResponse extends UserMessage{
	
	private String ack;
	private int    queueNumber;

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
}
