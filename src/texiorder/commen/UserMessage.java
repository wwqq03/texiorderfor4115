package texiorder.commen;

public abstract class UserMessage {
	
	public static String ORDER = "ORDER";
	public static String QUEUE = "QUEUE";
	public static String CANCEL = "CANCEL";
	
	protected String command;
	protected String alias;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
