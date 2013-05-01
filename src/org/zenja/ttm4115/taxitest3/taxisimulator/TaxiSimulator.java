package org.zenja.ttm4115.taxitest3.taxisimulator;

import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.mqtt.MQTT.Message;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.library.routeplanner.routeplanner.Journey;

public class TaxiSimulator extends Block {
	
	private static final String fromAddress[] = new String[]{"Oslo", "Boston", "Shanghai"};
	private static final String toAddress[] = new String[]{"Trondheim", "Washington", "Beijing"};
	public java.lang.String alias;

	public MQTTConfigParam initMQTT() {
		MQTTConfigParam param = new MQTTConfigParam("broker.mqttdashboard.com", 1883, "Test Client " + alias);
		param.setDefaultPublishTopic("generic-map-ui-group21");
		
		return param;
	}

	public Message createMessage(String messageString) {
		Message message = new Message(messageString.getBytes());
		return message;
	}

	public Journey createJourney() {
		int taxiID = Integer.valueOf(alias.replace("Taxi No.", ""));
		Journey journey = new Journey(fromAddress[taxiID], toAddress[taxiID], alias);
		
		System.out.println("Journey made: From " + fromAddress[taxiID] + " to " + toAddress[taxiID] + " by " + alias);
		
		return journey;
	}
	
	public static String getAlias(String ob) {
		return ob;
	}

}
