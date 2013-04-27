package texiorder.commen;

public class TourInfo {
	private String   customer;
	private String   taxi;
	private Location pickup;
	private Location start;
	private Location drop;
	private Location end;
	private int      distance;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getTaxi() {
		return taxi;
	}
	public void setTaxi(String taxi) {
		this.taxi = taxi;
	}
	public Location getPickup() {
		return pickup;
	}
	public void setPickup(Location pickup) {
		this.pickup = pickup;
	}
	public Location getStart() {
		return start;
	}
	public void setStart(Location start) {
		this.start = start;
	}
	public Location getDrop() {
		return drop;
	}
	public void setDrop(Location drop) {
		this.drop = drop;
	}
	public Location getEnd() {
		return end;
	}
	public void setEnd(Location end) {
		this.end = end;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	

}