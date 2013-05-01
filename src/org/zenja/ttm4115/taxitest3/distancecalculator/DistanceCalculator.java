package org.zenja.ttm4115.taxitest3.distancecalculator;

import no.ntnu.item.arctis.runtime.Block;

public class DistanceCalculator extends Block {

	public double calculateDistance(double[] data) {
		double lat1 = data[0];
		double lng1 = data[1];
		double lat2 = data[2];
		double lng2 = data[3];
	
		double r = 6371;
		double dLat = rad(lat2 - lat1);
		double dLng = rad(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(rad(lat1)) * Math.cos(rad(lat2))
				* Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = r * c;
		
		return d;
	}

	private double rad(double x) {
		return x * Math.PI / 180;
	}

}
