package texiorder.dispatcher;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.Taxi;
import texiorder.commen.TaxiOrder;
import texiorder.commen.TaxiRequest;
import texiorder.commen.User;
import texiorder.commen.UserOrder;
import texiorder.commen.UserResponse;

public class Dispatcher extends Block {

	private ArrayList<Taxi> taxis;
	private ArrayList<User> users;

	public void init() {
		taxis = new ArrayList<Taxi>();
		users = new ArrayList<User>();
	}

	public void registerTaxi(TaxiRequest taxiRequest) {
		if(taxiRequest == null)
			return;
		Taxi taxi = new Taxi();
		taxi.setId(taxiRequest.getAlias());
		taxi.setFree();
		taxi.setCurrent(taxiRequest.getCurrent());
		Iterator<Taxi> i = taxis.iterator();
		while(i.hasNext()){
			Taxi t = i.next();
			if(t.getId().equals(taxi.getId()))
				return;
		}
		taxis.add(taxi);
	}

	public void unregisterTaxi(TaxiRequest taxiRequest) {
		if(taxiRequest == null)
			return;
		String taxiId = taxiRequest.getAlias();
		Iterator<Taxi> i = taxis.iterator();
		while(i.hasNext()){
			Taxi t = i.next();
			if(t.getId().equals(taxiId)){
				taxis.remove(i);
				return;
			}
		}
	}

	public void setTaxi2Free(TaxiRequest taxiRequest) {
		if(taxiRequest == null)
			return;
		String taxiId = taxiRequest.getAlias();
		Iterator<Taxi> i = taxis.iterator();
		while(i.hasNext()){
			Taxi t = i.next();
			if(t.getId().equals(taxiId)){
				t.setFree();
				return;
			}
		}
	}

	public void cancelUser(UserOrder or) {
		if(or == null)
			return;
		Iterator<User> i = users.iterator();
		while(i.hasNext()){
			User user = i.next();
			if(user.getId().equals(or.getAlias())) {
				users.remove(user);
				break;
			}
		}
	}

	public String isTaxiAvailible(UserOrder uOrder) {
		if(uOrder == null || !uOrder.getCommand().equals(UserOrder.ORDER))
			return null;
		String userId = uOrder.getAlias();
		Iterator<User> i = users.iterator();
		User u;
		boolean flag = false;
		//add user to userlist
		while(i.hasNext()) {
			u = i.next();
			if(u.getId().equals(userId)) {
				u.setAddress(uOrder.getAddress());
				flag = true;
				break;
			}
		}
		if(!flag) {
			u = new User();
			u.setId(userId);
			u.setAddress(uOrder.getAddress());
			u.setWaiting();
			users.add(u);
		}
		
		Taxi preferedTaxi = getAFreeTaxi(uOrder.getAddress());
		if(preferedTaxi == null) {
			if(u != null)
				u.setWaiting();
			return uOrder.getAlias();
		}
		return null;
	}
	
	private Taxi getAFreeTaxi(String userLocation) {
		Iterator<Taxi> i = taxis.iterator();
		Taxi preferedTaxi = null;
		float minDis = 0;
		while(i.hasNext()) {
			Taxi t = i.next();
			if(t.isBusy())
				continue;
			String taxiLocation = t.getCurrent();
			float distance = getDistance(userLocation, taxiLocation);
			if(minDis == 0 || distance < minDis){
				minDis = distance;
				preferedTaxi = t;
			}
		}
		return preferedTaxi;
	}

	public TaxiOrder cancelTaxiOrder(Object ob) {
		if(ob == null || !(ob instanceof String))
			return null;
		String user = (String)ob;
		TaxiOrder order = new TaxiOrder();
		order.setCommand(TaxiOrder.COMMAND_CANCEL);
		order.setAlias(alias)
	}

	public User checkWaitingUser() {
		Iterator<User> i = users.iterator();
		User waitingUser = null;
		while(i.hasNext()) {
			User u = i.next();
			if(u.isInWaitingList()){
				waitingUser = u;
				break;
			}
		}
		return waitingUser;
	}

	public TaxiOrder sentNewTaxiOrder(User user) {
		if(user == null)
			return null;
		TaxiOrder tOrder = new TaxiOrder();
		Taxi preferedTaxi = getAFreeTaxi(user.getAddress());
		tOrder.setAlias(preferedTaxi.getId());
		tOrder.setCommand(TaxiOrder.COMMAND_NEW);
		tOrder.setCustomer(user.getId());
		tOrder.setPickup(user.getAddress());
		user.setProcessing();
		return tOrder;
	}

	public void putUserIntoList(texiorder.commen.UserOrder uOrder) {
	}

	public UserResponse sendUserQueue(String userId) {
		if(userId == null)
			return null;
		
	}
}
