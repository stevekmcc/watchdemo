package com.metacase.watch.framework;

public class Icon extends Object {
	public String iconName;
	public boolean on;

	public Icon(String name) {
		iconName = name;
		on = false;
	}

	public void setOn() {
		on = true;
	}

	public void setOff() {
		on = false;
	}

	public void toggle() {
		on =! on;
	}
}
