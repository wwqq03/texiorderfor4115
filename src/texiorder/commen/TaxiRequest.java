package texiorder.commen;

/**
 * This class is for taxi to send request to dispatcher to change its state. e.g.Busy, Free...
 * @author which
 *
 */

public class TaxiRequest extends TaxiMessage {
	
	private double[] current;

	public double[] getCurrent() {
		return current;
	}

	public void setCurrent(double[] current) {
		this.current = current;
	}
	
}
