package texiorder.commen;

public abstract class TaxiMessage {
	
	public static String COMMAND_LOGON  = "LOGON";
	public static String COMMAND_LOGOFF = "LOGOFF";
	public static String COMMAND_BUSY   = "BUSY";
	public static String COMMAND_FREE   = "FREE";
	public static String COMMAND_NEW    = "NEW";
	public static String COMMAND_CANCEL    = "CANCEL";
	
	private String   command;
	private String   alias;
	
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
