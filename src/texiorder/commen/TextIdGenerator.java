package texiorder.commen;

public class TextIdGenerator {
	private static int id = 1;	// taxi id start form 1
	
	public static String getNextId() {
		return new Integer(id++).toString();
	}
}
