package texiorder.commen;

public class Taxi {
	private String   id;
	private double[] current;
	private boolean  busy;
	private String   customer;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double[] getCurrent() {
		return current;
	}
	public void setCurrent(double[] current) {
		this.current = current;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public boolean isBusy() {
		return busy;
	}
	public void setBusy() {
		busy = true;
	}
	public void setFree() {
		busy = false;
	}
}
