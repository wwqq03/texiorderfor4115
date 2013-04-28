package texiorder.commen;

/**
 * This class is for dispatcher to send a order to taxi
 * @author which
 *
 */

public class TaxiOrder extends TaxiMessage{
	
	private String   customer;
	private Location pickup;	
	
	public Location getPickup() {
		return pickup;
	}
	public void setPickup(Location pickup) {
		this.pickup = pickup;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
}
