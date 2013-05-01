package texiorder.commen;

public class TextIdGenerator {
	private static int id = 0;
	
	public static String getNextId() {
		return new Integer(id++).toString();
	}
}
