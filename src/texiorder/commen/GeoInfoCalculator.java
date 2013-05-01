package texiorder.commen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class GeoInfoCalculator {
	
	public double calculateDistance(String address1, String address2) {
		String query1 = createQuery(address1);
		String query2 = createQuery(address2);
		
		double[] point1 = parseLocation(query(query1));
		double[] point2 = parseLocation(query(query2));
		
		return calculateDistance(point1[0], point1[1], point2[0], point2[1]);
	}
	
	public double calculateDistance(String address1, double lat, double lng) {
		String query1 = createQuery(address1);
		
		double[] point1 = parseLocation(query(query1));
		
		return calculateDistance(point1[0], point1[1], lat, lng);
	}
	
	
	private double[] parseLocation(String xmlString) {
		double[] result = new double[2];
		double lat = 0.0;
		double lng = 0.0;
		
//		System.out.println(xmlString); // DEBUG
		
		/* parse lat and lng */
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document doc = builder.parse(is);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			Node node = doc.getElementsByTagName("location").item(0);
			Element element = (Element)node;
			
			lat = Double.parseDouble(element.getElementsByTagName("lat").item(0).getTextContent());
			lng = Double.parseDouble(element.getElementsByTagName("lng").item(0).getTextContent());
			
			// DEBUG
//			System.out.println("Lat : " + lat);
//			System.out.println("Lng : " + lng);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      
		
		result[0] = lat;
		result[1] = lng;
		
		return result;
	}

	public String createQuery(String address) {
		String urlEncodedAddress = address.replace(" ", " ");
		return "http://maps.google.com/maps/api/geocode/xml?address=" + urlEncodedAddress + "&sensor=false";
	}
	
	public String query(String url) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			String line = "";
			StringBuilder xmlStringBuilder = new StringBuilder();
			while ((line = rd.readLine()) != null) {
				xmlStringBuilder.append(line);
				xmlStringBuilder.append("\n");
			}
			
			return xmlStringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
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


	/**
	 * For Test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GeoInfoCalculator dc = new GeoInfoCalculator();
		double distance = dc.calculateDistance("Trondheim", "Oslo");
		System.out.println("Distance: " + distance);
	}

}
