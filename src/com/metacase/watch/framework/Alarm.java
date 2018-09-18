package com.metacase.watch.framework;

public class Alarm extends Thread {
	
	private static final int dayMs = 24 * 60 * 60 * 1000;
	public AbstractWatchApplication alarmApplication;
	public boolean localTimeAwareness;
	public int sleeptime;
	public String name;
	public String alarmState;
	private volatile boolean isLive = false;
	private Master master;
	
	public Alarm(String alarmName, boolean aware, AbstractWatchApplication application, String state, METime time, Master masterObject) {
		name = alarmName;
		localTimeAwareness = aware;
		alarmApplication = application;
		alarmState = state;
		master = masterObject;
		sleeptime = time.asMilliseconds();
		while (sleeptime < 0) { 
			// set time to be next 'day'
			sleeptime = sleeptime + dayMs;
		}
		while (sleeptime > dayMs) { 
			// set time to be next 'day'
			sleeptime = sleeptime - dayMs;
		}
	}

	public void run() {
		isLive = true;
		try {
			sleep(sleeptime);
		}
	    catch (InterruptedException e) { }
		if (isLive)	master.raiseAlarm(this);
	}
	
	public void stopAlarm() {
		isLive = false;
	}
}