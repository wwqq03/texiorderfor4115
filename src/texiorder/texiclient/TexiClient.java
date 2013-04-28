package texiorder.texiclient;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.Location;
import texiorder.commen.TaxiOrder;
import texiorder.commen.TaxiRequest;
import texiorder.commen.TaxiResponse;

public class TexiClient extends Block {

	public static String STATE_ONLINE  = "Online";
	public static String STATE_OFFLINE = "Offline";
	public static String STATE_BUSY    = "Busy";
	public static String STATE_FREE    = "Free";
	
	public String        alias_taxi;
	private TaxiOrder    order;
	private TaxiRequest  request;
	private TaxiResponse response;
	private Location     current;

	public String getNewOrder(TaxiOrder or) {
		if(or == null)
			return null;
		if(or.getCommand().equals(TaxiOrder.COMMAND_NEW)) {
			this.order = or;
			response = new TaxiResponse();
			return order.getPickup().toString();
		}
		else
			return null;
	}

	public TaxiResponse acceptOrder() {
		if(response == null)
			return null;
		response.setCommand(TaxiResponse.COMMAND_NEW);
		response.setAlias(alias_taxi);
		response.setAck("200");
		TaxiResponse res = response;
		response = null;
		return res;
	}

	public TaxiRequest logOn() {
		request = new TaxiRequest();
		request.setAlias(alias_taxi);
		request.setCommand(TaxiRequest.COMMAND_LOGON);
		current = new Location();
		current.setStreet("Herman Kragsvei");
		current.setNumber(45);
		request.setCurrent(current);
		return request;
	}

	public TaxiRequest logOff() {
		if(request == null)
			return null;
		request.setCommand(TaxiRequest.COMMAND_LOGOFF);
		TaxiRequest req = request;
		request = null;
		return req;
	}

	public Object sendBusy() {
		if(response != null){
			response.setAlias(alias_taxi);
			response.setCommand(TaxiResponse.COMMAND_NEW);
			response.setAck("408");
			TaxiResponse res = response;
			response = null;
			return res;
		}
		if(request == null)
			return null;
		request.setCommand(TaxiRequest.COMMAND_BUSY);
		return request;
	}

	public TaxiRequest sendFree() {
		if(request == null)
			return null;
		request.setCommand(TaxiRequest.COMMAND_FREE);
		current.setStreet("Herman Kragsvei");
		current.setNumber(45);
		request.setCurrent(current);
		return request;
	}
}
