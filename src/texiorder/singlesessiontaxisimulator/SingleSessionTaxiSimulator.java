package texiorder.singlesessiontaxisimulator;

import texiorder.commen.GeoInfoCalculator;
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
	
	private boolean isRunning = false;
	
	public MQTTConfigParam initMQTT() {
		MQTTConfigParam param = new MQTTConfigParam("broker.mqttdashboard.com", 1883, "Test Client " + taxiId);
		param.setDefaultPublishTopic("generic-map-ui-group21");
		
		return param;
	}

	public Message createMessage(String messageString) {
		// if is stopped, send empty message
		if (isRunning = false) {
			System.out.println("Taxi stopped, stop creating valid message.");
			return new Message("".getBytes());
		}
		
		System.out.println("Creating message: " + messageString);
	
		Message message = new Message(messageString.getBytes());
		
		// update current 
		double[] position = getLatAndLngFromMessageString(messageString);
		currentLat = position[0];
		currentLng = position[1];
		
		return message;
	}

	public Journey createJourney(String destination) {
		Journey journey = new Journey(currentAddress.replace(" ", "+"), destination.replace(" ", "+"), taxiId);
		
		// start the taxi!
		isRunning = true;
		
		System.out.println("Journey from " + currentAddress + " to " + destination + " created.");
		
		return journey;
	}

	public void setTaxiId() {
		// init taxiId
		taxiId = TextIdGenerator.getNextId();
		
		System.out.println("New taxi simulator generated with id " + taxiId + "!");
	}
	
	private double[] getLatAndLngFromMessageString(String messageString) {
		String latString, lngString;
		latString = messageString.split("\"latitude\":")[1].split(",")[0];
		lngString = messageString.split("\"longitude\":")[1].split("}")[0];
		
		return new double[]{Double.parseDouble(latString), Double.parseDouble(lngString)};
	}

	public double[] updatePosition() {
		// if is running, update the position
		if (isRunning == true) {
			// update currentAddress
			GeoInfoCalculator gif = new GeoInfoCalculator();
			currentAddress = gif.getAddressFromLatLng(currentLat, currentLng);
		}
		
		return new double[]{currentLat, currentLng};
	}

	public void stop() {
		isRunning = false;
	}
	
}
