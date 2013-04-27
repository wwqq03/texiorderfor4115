package texiorder.userclient;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.UserOrder;

public class UserClient extends Block {

	public String alias_user;
	private UserOrder order;
	public static String ORDER = "ORDER";
	public static String QUEUE = "QUEUE";
	public static String CANCEL = "CANCEL";
	
	public static String getAlias(String id) {
		return id;
	}
	
	public static String getAlias(UserOrder order) {
		return order.getAlias();
	}

	public UserOrder createOrder(String address) {
		if(address == null)
			return null;
		order = new UserOrder();
		order.setAlias(alias_user);
		order.setAddress(address);
		order.setCommand(UserClient.ORDER);
		return order;
	}

	public String updateResponse(UserOrder order) {
		if(order == null)
			return null;
		else if(order.getAck() != null && order.getCommand().equals(UserClient.ORDER))
			return "You got response: " + order.getAck();
		else if(order.getQueueNumber() > 0 && order.getCommand().equals(UserClient.QUEUE))
			return "You are in position: " + String.valueOf(order.getQueueNumber()) + "of the waiting list";
		else 
			return null;
	}

	public UserOrder requestQueue() {
		if(order == null)
			return null;
		else
			order.setCommand(UserClient.QUEUE);
		return order;
	}

	public UserOrder cansel() {
		if(order == null)
			return null;
		else
			order.setCommand(UserClient.CANCEL);
		return order;
	}

}
