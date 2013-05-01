package org.zenja.ttm4115.taxitest3.locationfinder;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import no.ntnu.item.arctis.runtime.Block;

public class LocationFinder extends Block {
	
	// Example XML (with address Trondheim):
	//
	//<GeocodeResponse>
	// <status>OK</status>
	// <result>
	//  <type>locality</type>
	//  <type>political</type>
	//  <formatted_address>Trondheim, Norway</formatted_address>
	//  <address_component>
	//   <long_name>Trondheim</long_name>
	//   <short_name>Trondheim</short_name>
	//   <type>locality</type>
	//   <type>political</type>
	//  </address_component>
	//  <address_component>
	//   <long_name>Trondheim</long_name>
	//   <short_name>Trondheim</short_name>
	//   <type>administrative_area_level_2</type>
	//   <type>political</type>
	//  </address_component>
	//  <address_component>
	//   <long_name>Sor-Trondelag</long_name>
	//   <short_name>Sor-Trondelag</short_name>
	//   <type>administrative_area_level_1</type>
	//   <type>political</type>
	//  </address_component>
	//  <address_component>
	//   <long_name>Norway</long_name>
	//   <short_name>NO</short_name>
	//   <type>country</type>
	//   <type>political</type>
	//  </address_component>
	//  <geometry>
	//   <location>
	//    <lat>63.4305149</lat>
	//    <lng>10.3950528</lng>
	//   </location>
	//   <location_type>APPROXIMATE</location_type>
	//   <viewport>
	//    <southwest>
	//     <lat>63.3805535</lat>
	//     <lng>10.2981588</lng>
	//    </southwest>
	//    <northeast>
	//     <lat>63.4569189</lat>
	//     <lng>10.5793655</lng>
	//    </northeast>
	//   </viewport>
	//   <bounds>
	//    <southwest>
	//     <lat>63.3805535</lat>
	//     <lng>10.2981588</lng>
	//    </southwest>
	//    <northeast>
	//     <lat>63.4569189</lat>
	//     <lng>10.5793655</lng>
	//    </northeast>
	//   </bounds>
	//  </geometry>
	// </result>
	//</GeocodeResponse>

	public double[] parseLocation(String xmlString) {
		double[] result = new double[2];
		double lat = 0.0;
		double lng = 0.0;
		
		System.out.println(xmlString); // DEBUG
		
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
			System.out.println("Lat : " + lat);
			System.out.println("Lng : " + lng);
			
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

}
