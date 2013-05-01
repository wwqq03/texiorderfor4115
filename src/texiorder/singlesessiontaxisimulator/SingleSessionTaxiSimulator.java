package texiorder.singlesessiontaxisimulator;

import texiorder.commen.TaxiOrder;
import texiorder.commen.TextIdGenerator;

import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.mqtt.MQTT.Message;

import no.ntnu.item.arctis.runtime.Block;
import no.ntnu.item.ttm4115.library.routeplanner.routeplanner.Journey;

public class SingleSessionTaxiSimulator extends Block {

	private String taxiId = "";
	
	private static final String initAddress = "Herman Kragsvei";
	private String currentAddress = initAddress;
	
	private double currentLat;
	private double currentLng;
	
	public MQTTConfigParam initMQTT() {
		MQTTConfigParam param = new MQTTConfigParam("broker.mqttdashboard.com", 1883, "Test Client " + taxiId);
		param.setDefaultPublishTopic("generic-map-ui-group21");
		
		return param;
	}

	public Message createMessage(String messageString) {
		Message message = new Message(messageString.getBytes());
		
		// update current 
		double[] position = getLatAndLngFromMessageString(messageString);
		currentLat = position[0];
		currentLng = position[1];
		
		return message;
	}

	public Journey createJourney(String destination) {
		Journey journey = new Journey(currentAddress, destination, taxiId);
		
		return journey;
	}

	public void setTaxiId() {
		// init taxiId
		taxiId = TextIdGenerator.getNextId();
	}
	
	private double[] getLatAndLngFromMessageString(String messageString) {
		String latString, lngString;
		latString = messageString.split("\"latitude\":")[1].split(",")[0];
		lngString = messageString.split("\"longitude\":")[1].split("}")[0];
		
		return new double[]{Double.parseDouble(latString), Double.parseDouble(lngString)};
	}

	public double[] updatePosition() {
		return new double[]{currentLat, currentLng};
	}
	
}
