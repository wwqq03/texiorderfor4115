package texiorder.commen;

public class TaxiResponse extends TaxiMessage {
	
	private String ack;
	private String customer;
	public static String RESPONSE_OK   = "200";
	public static String RESPONSE_BUSY = "408";

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

}
