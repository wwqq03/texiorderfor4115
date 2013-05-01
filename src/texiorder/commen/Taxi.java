package texiorder.commen;

public class Taxi {
	private String   id;
	private String current;
	private boolean  busy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
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
