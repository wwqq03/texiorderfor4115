package texiorder.dispatcher;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.GeoInfoCalculator;
import texiorder.commen.Taxi;
import texiorder.commen.TaxiOrder;
import texiorder.commen.TaxiRequest;
import texiorder.commen.TaxiResponse;
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
		System.out.println("In Dispatcher.registerTaxi");
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
		System.out.println("In Dispatcher.unregisterTaxi");
		if(taxiRequest == null)
			return;
		String taxiId = taxiRequest.getAlias();
		Iterator<Taxi> i = taxis.iterator();
		while(i.hasNext()){
			Taxi t = i.next();
			if(t.getId().equals(taxiId)){
				taxis.remove(t);
				return;
			}
		}
	}

	public void setTaxi2Free(TaxiRequest taxiRequest) {
		System.out.println("In Dispatcher.setTaxi2Free");
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

	public User cancelUser(UserOrder or) {
		System.out.println("In Dispatcher.cancelUser");
		if(or == null)
			return null;
		User user = null;
		Iterator<User> i = users.iterator();
		while(i.hasNext()){
			user = i.next();
			if(user.getId().equals(or.getAlias())) {
				users.remove(user);
				break;
			}
		}
		return user;
	}

	public String isTaxiAvailible(UserOrder uOrder) {
		System.out.println("In Dispatcher.isTaxiAvailible");
		if(uOrder == null || !uOrder.getCommand().equals(UserOrder.ORDER))
			return null;
		String userId = uOrder.getAlias();
		Iterator<User> i = users.iterator();
		User u = null;
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
		System.out.println("In Dispatcher.getAFreeTaxi");
		Iterator<Taxi> i = taxis.iterator();
		Taxi preferedTaxi = null;
		double minDis = 0;
		while(i.hasNext()) {
			Taxi t = i.next();
			if(t.isBusy())
				continue;
			double[] taxiLocation = t.getCurrent();
			GeoInfoCalculator dc = new GeoInfoCalculator();
			double distance = dc.calculateDistance(userLocation, taxiLocation[0], taxiLocation[1]);
			if(minDis == 0 || distance < minDis){
				minDis = distance;
				preferedTaxi = t;
			}
		}
		return preferedTaxi;
	}

	public TaxiOrder cancelTaxiOrder(User user) {
		System.out.println("In Dispatcher.cancelTaxiOrder");
		if(user == null || user.getWaitForTaxi() == null)
			return null;
		TaxiOrder order = new TaxiOrder();
		order.setCommand(TaxiOrder.COMMAND_CANCEL);
		order.setAlias(user.getWaitForTaxi());
		order.setCustomer(user.getId());
		return order;
	}

	public User checkWaitingUser() {
		System.out.println("In Dispatcher.checkWaitingUser");
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
		System.out.println("In Dispatcher.sentNewTaxiOrder");
		if(user == null)
			return null;
		TaxiOrder tOrder = new TaxiOrder();
		Taxi preferedTaxi = getAFreeTaxi(user.getAddress());
		tOrder.setAlias(preferedTaxi.getId());
		tOrder.setCommand(TaxiOrder.COMMAND_NEW);
		tOrder.setCustomer(user.getId());
		tOrder.setPickup(user.getAddress());
		user.setProcessing();
		user.setWaitForTaxi(preferedTaxi.getId());
		preferedTaxi.setBusy();
		return tOrder;
	}

	public UserResponse sendUserQueue(String userId) {
		System.out.println("In Dispatcher.sendUserQueue");
		if(userId == null)
			return null;
		UserResponse response = new UserResponse();
		response.setCommand(UserResponse.QUEUE);
		response.setAlias(userId);
		int que = 0;
		Iterator<User> i = users.iterator();
		boolean flag = false;
		while(i.hasNext()) {
			User u = i.next();
			if(u.isInWaitingList()){
				que ++;
			}
			if(u.getId().equals(userId)){
				response.setQueueNumber(que);
				flag = true;
				break;
			}
		}
		if(flag)
			return response;
		return null;
	}

	public String getQueueRequest(UserOrder or) {
		System.out.println("In Dispatcher.getQueueRequest");
		if(or == null)
			return null;
		return or.getAlias();
	}

	public UserResponse processAccept(TaxiResponse response) {
		System.out.println("In Dispatcher.processAccept");
		if(response == null || response.getAck()!= TaxiResponse.RESPONSE_OK)
			return null;
		Iterator<User> i = users.iterator();
		while(i.hasNext()){
			User u = i.next();
			if(u.getId().equals(response.getCustomer())){
				Iterator<Taxi> itaxi = taxis.iterator();
				while(itaxi.hasNext()){
					Taxi t = itaxi.next();
					if(t.getId().equals(response.getAlias())){
						t.setBusy();
						break;
					}
				}
				UserResponse userResponse = new UserResponse();
				userResponse.setAlias(u.getId());
				userResponse.setAck("Taxi " + response.getAlias() + " will serve for you! Please wait!");
				userResponse.setCommand(UserResponse.ORDER);
				users.remove(u);
				return userResponse;
			}
		}
		return null;
	}

	public boolean rejectOrder(Object ob) {
		System.out.println("In Dispatcher.rejectOrder");
		if(ob instanceof TaxiResponse){
			TaxiResponse response = (TaxiResponse)ob;
			Iterator<Taxi> i = taxis.iterator();
			while(i.hasNext()){
				Taxi taxi = i.next();
				if(taxi.getId().equals(response.getAlias())){
					taxi.setBusy();
					break;
				}
			}
			if(response.getAck() != TaxiResponse.RESPONSE_BUSY)
				return false;
			else{
				Iterator<User> iuser = users.iterator();
				while(iuser.hasNext()){
					User u = iuser.next();
					if(u.getId().equals(response.getCustomer())){
						u.setWaiting();
						u.setWaitForTaxi(null);
					}
				}
				return true;
			}
		}
		else if(ob instanceof TaxiRequest){
			TaxiRequest request = (TaxiRequest)ob;
			Iterator<Taxi> i = taxis.iterator();
			while(i.hasNext()){
				Taxi taxi = i.next();
				if(taxi.getId().equals(request.getAlias())){
					taxi.setBusy();
					break;
				}
			}
			return false;
		}
		else
			return false;
	}
}
