package texiorder.commen;

/**
 * This class is for dispatcher to send a order to taxi
 * @author which
 *
 */

public class TaxiOrder extends TaxiMessage{
	
	private String   customer;
	private String pickup;	
	
	public String getPickup() {
		return pickup;
	}
	public void setPickup(String pickup) {
		this.pickup = pickup;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
}
