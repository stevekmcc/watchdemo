package com.metacase.watch.framework;

import java.util.*;

/**
 * (C) 2003 MetaCase Consulting
 * The main workhorse: implements a state machine with submachines maintaining their own state. 
 * Only one state machine is active, and a transition to a Stop state doesn't actually occur, 
 * but instead returns control to the parent, leaving the submachine dormant in the state preceding the transition.
 * From each Watch Application graph we generate a subclass of this class, 
 * providing its own state transition and display function tables, and methods for actions and display functions.
 */
public abstract class AbstractWatchApplication {
	private static String START = "Start [Watch]";
	private static String STOP  = "Stop [Watch]";

	public Master master;
	
	public Hashtable<String, DecompPair> decompositions = new Hashtable<String, DecompPair>();
	public Hashtable<String, Hashtable<String, Result>> stateTransitions = new Hashtable<String, Hashtable<String, Result>>();
	public Hashtable<String, DisplayInfo> stateDisplays = new Hashtable<String, DisplayInfo>();
	public String currentState = START;

	public AbstractWatchApplication(Master master, String oopString) {
		this.master = master;
	}

	public void addStateOop(String stateName, String oopString) {
		// This method is needed when generated for API
		// Implemented as empty here due to the compliance reasons
	}

	public String appName()	{
		String fullName = getClass().getName();
		return fullName.substring(1 + fullName.lastIndexOf('.'));
	}
	
	public METime getClockTime() {
		return getClockOffset().mePlus(getSysTime());
	}

	public void setClockTime(METime newClockTime) {
		setClockOffset(newClockTime.meMinus(getSysTime()));
	}

	public METime getSysTime() {
		METime sysTime = new METime();
		sysTime.setFromSysClock();
		return sysTime;
	}

	public void setSysTime(METime time) {
		// A null operation
	}

	public METime getClockOffset() {
		// always return a copy not the real thing, thus same as sysTime and clockTime
		return master.clockOffset.mePlus(new METime());
	}

	public void setClockOffset(METime newOffset) {
		METime offsetChange = newOffset.meMinus(getClockOffset());
		master.clockOffset = newOffset;
		master.clockOffsetChanged(offsetChange);
	}

	public void setAlarm(String name, boolean localTimeAwareness, String alarmState, METime sleepTime) {
		Alarm alarm;
		alarm = new Alarm(name, localTimeAwareness, this, alarmState, sleepTime, master);
		master.alarms.addElement(alarm);
		alarm.start();
	}

	public void killAlarm(String name) {
		for (int i = 0; i < master.alarms.size(); i++) {
			Alarm alarm = (master.alarms.elementAt(i));
			if(alarm.name.equals(name) && alarm.alarmApplication == this) master.killAlarm(alarm);
		}
	}

	public void addDecomposition(String decompState, String appName, AbstractWatchApplication app) {
		decompositions.put(decompState, new DecompPair(appName, app));
	}

	public void addTransition(String initialState, String event, int action, String targetState) {
		Hashtable<String, Result> eventTable = (stateTransitions.get(initialState));
		if (eventTable == null) { 
			eventTable = new Hashtable<String, Result>();
			stateTransitions.put(initialState, eventTable);
		}
		eventTable.put(event, new Result(action, targetState));
	}

	public void addStateDisplay(String state, int blinking, int keyUnit, int displayMethod) {	
		stateDisplays.put(state, new DisplayInfo(blinking, keyUnit, displayMethod));
	}

	public void handleEvent(String button) {
		Hashtable<String, Result> eventTable;
		Result result;		

		eventTable = (stateTransitions.get(currentState));
		if (eventTable == null) return;
		result = (Result)(eventTable.get(button));
		if (result == null) return;
		if (result.action != 0) perform(result.action);
		if (result.targetState.equals(STOP)) {
			master.appStack.pop();
			master.jog();
			return;
		}
		currentState=result.targetState;
		DisplayInfo info = (stateDisplays.get(currentState));
		master.setBlinks(info.blinking);
		DecompPair decompPair = (decompositions.get(currentState));
		if (decompPair != null) {
			master.startWatchApp(decompPair.name);
			return;
		}
		master.jog();
	}

	public void iconOn(String name) {
		for (int i = 0; i < master.display.icons.size();i++) {
			Icon icon = (Icon)(master.display.icons.elementAt(i));
			if(name.equals(icon.iconName)) icon.setOn();
		}
	}

	public void iconOff(String name) {
		for (int i = 0; i < master.display.icons.size();i++) {
			Icon icon = (Icon)(master.display.icons.elementAt(i));
			if(name.equals(icon.iconName)) icon.setOff();
		}
	}

	public void iconToggle(String name)	{
		for (int i = 0; i < master.display.icons.size();i++) {
			Icon icon = (Icon)(master.display.icons.elementAt(i));
			if(name.equals(icon.iconName)) {
				icon.toggle();
				break; // in case it is included multiple times in the display
			}
		}
	}

	public int keyUnit() {
		DisplayInfo info = (stateDisplays.get(currentState));
		return info.keyUnit;
	}

	public METime displayTime()	{
		DisplayInfo info = (stateDisplays.get(currentState));
		if (info == null) return getClockTime();
		int mId = info.displayMethod;
		METime displayTime = (METime)(perform(mId));
		return displayTime;
	}

	public abstract Object perform(int methodId);

}
