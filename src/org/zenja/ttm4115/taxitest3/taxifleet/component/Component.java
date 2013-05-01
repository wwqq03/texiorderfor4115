package org.zenja.ttm4115.taxitest3.taxifleet.component;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {

	private static long counter = 0;
	
	private static long getNewId() {
		return counter++;
	}

	public String createAlias() {
		String alias = "Taxi No." + String.valueOf(getNewId());
		System.out.println("createAlias: " + alias);
		return alias;
	}

}
