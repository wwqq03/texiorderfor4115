package texiorder.userclient;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.UserOrder;
import texiorder.commen.UserResponse;

public class UserClient extends Block {

	public String alias_user;
	private UserOrder order;
	
	public static String getAlias(String id) {
		return id;
	}
	
	public static String getAlias(UserResponse res) {
		return res.getAlias();
	}

	public UserOrder createOrder(String address) {
		if(address == null)
			return null;
		order = new UserOrder();
		order.setAlias(alias_user);
		order.setAddress(address);
		order.setCommand(UserOrder.ORDER);
		return order;
	}

	public String updateResponse(UserResponse res) {
		if(res == null)
			return null;
		else if(res.getAck() != null && res.getCommand().equals(UserResponse.ORDER))
			return "You got response: " + res.getAck();
		else if(res.getQueueNumber() > 0 && res.getCommand().equals(UserResponse.QUEUE))
			return "You are in position: " + String.valueOf(res.getQueueNumber()) + " of the waiting list";
		else 
			return null;
	}

	public UserOrder requestQueue() {
		if(order == null)
			return null;
		else
			order.setCommand(UserOrder.QUEUE);
		return order;
	}

	public UserOrder cansel() {
		if(order == null)
			return null;
		else
			order.setCommand(UserOrder.CANCEL);
		return order;
	}

}
