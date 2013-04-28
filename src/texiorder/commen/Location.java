package texiorder.commen;

public class Location {
	private String street;
	private int    number = 0;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String toString() {
		String str = null;
		if(street != null) {
			str += street;
			if(number > 0)
				str = " " + String.valueOf(number);
		}
		return str;
	}

}
