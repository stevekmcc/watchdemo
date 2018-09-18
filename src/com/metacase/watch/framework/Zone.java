package com.metacase.watch.framework;

public class Zone extends Object {
	public String zoneName;
	public String zoneValue;
	public boolean blinking;
	public int unit;

	public Zone(String name) {
		zoneName = name;
		zoneValue = "00";
	}

	public void setValue(String value) {
		zoneValue = ("00" + value).substring(value.length());
	}
}
