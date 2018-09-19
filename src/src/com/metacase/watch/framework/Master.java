package com.metacase.watch.framework;

/**
 * (C) 2002 MetaCase Consulting
 * The basic watch behaviour. Handles state machine stack and event dispatching, and alarms. 
 * Actual individual state machines are specified in generated classes, generic state machine behaviour in AbstractWatchApplication.
 */
import java.util.*;

public class Master {
	public AbstractWatchApplet applet;
	public AbstractDisplay display;
	public Vector<Alarm> alarms = new Vector<Alarm>();
	public Hashtable<String, AbstractWatchApplication> applications = new Hashtable<String, AbstractWatchApplication>();
	public Stack<AbstractWatchApplication> appStack = new Stack<AbstractWatchApplication>();
	public METime clockOffset = new METime();
	public int blinkingUnit = -1;

	public void init(AbstractWatchApplet applet, AbstractDisplay display, AbstractWatchApplication topApp) {
		this.applet = applet;
		this.display = display;
		addThisAndSubApps(topApp.appName(), topApp);
		startWatchApp(topApp.appName());
	}

	public void setBlinks(int unit) {
		blinkingUnit = unit;
	}

	public void sendEvent(String eventName)	{
		(appStack.peek()).handleEvent(eventName);
	}

	public void jog() {
		sendEvent("");
	}

	public void addThisAndSubApps(String appName, AbstractWatchApplication app)	{
		applications.put(appName, app);
		try {
			for ( java.util.Map.Entry<String, DecompPair> decompEntry : app.decompositions.entrySet()) {
				addThisAndSubApps(decompEntry.getValue().name, decompEntry.getValue().application);
			}
		}
		catch (Exception e) {}
	}

	public void startWatchApp(String appName) {
		try {
			appStack.push((applications.get(appName)));
			jog();
		}
		catch (Exception e) {}
	}

	public void clockOffsetChanged(METime offsetChange) {
		Alarm oldAlarm;
		Alarm newAlarm;
		METime newTime;

		for (int i = 0; i < alarms.size() ; i++) {
			oldAlarm = (alarms.elementAt(i));
			if(oldAlarm.localTimeAwareness) {
				newTime = new METime();
				newTime.add(METime.HUNDREDTH, oldAlarm.sleeptime / 10);
				newTime = newTime.meMinus(offsetChange);
				newAlarm = new Alarm(oldAlarm.name, oldAlarm.localTimeAwareness, oldAlarm.alarmApplication, oldAlarm.alarmState, newTime, this);
				alarms.setElementAt(newAlarm, i);
				newAlarm.start();
				oldAlarm.stopAlarm();
			}
		}
	}

	public void killAlarm(Alarm alarm) {
		alarms.removeElement(alarm);
		alarm.stopAlarm();
	}

	public void raiseAlarm(Alarm alarm)	{
		alarm.alarmApplication.currentState = alarm.alarmState;
		if ((appStack.peek()) == alarm.alarmApplication) jog();
		else {
			startWatchApp(alarm.alarmApplication.appName());
		}
		killAlarm(alarm);
		applet.ringAlarm();
	}
}