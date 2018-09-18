package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class AlarmClock extends AbstractWatchApplication {
	static final int a9_420	= 1;
	static final int a9_1187	= 2;
	static final int a9_2126	= 3;
	static final int a9_2134	= 4;
	static final int a9_2597	= 5;
	static final int a9_2907	= 6;
	static final int a9_3169	= 7;
	static final int d9_717	= 8;
	public METime alarmTime = new METime();

	public METime getalarmTime() {
		return alarmTime;
	}
	public void setalarmTime(METime t1) {
		alarmTime = t1;
	}


	public AlarmClock(Master master) {
		super(master, "9_377");
            addStateOop("Start [Watch]", "9_1843");
            addStateOop("AlarmRang", "9_2425");
            addStateOop("EditHours", "9_412");
            addStateOop("EditMinutes", "9_1675");
            addStateOop("Show", "9_583");
            addStateOop("Stop [Watch]", "9_1047");

            addTransition ("AlarmRang", "", a9_2134, "Show");
            addTransition ("EditMinutes", "Mode", 0, "EditHours");
            addTransition ("EditMinutes", "Up", a9_2597, "EditMinutes");
            addTransition ("EditHours", "Set", a9_420, "Show");
            addTransition ("Show", "Mode", 0, "Stop [Watch]");
            addTransition ("EditHours", "Down", a9_2126, "EditHours");
            addTransition ("Show", "Down", a9_3169, "Show");
            addTransition ("Start [Watch]", "", 0, "Show");
            addTransition ("EditMinutes", "Set", a9_420, "Show");
            addTransition ("Show", "Up", a9_420, "Show");
            addTransition ("EditHours", "Mode", 0, "EditMinutes");
            addTransition ("AlarmRang", "Mode", 0, "Stop [Watch]");
            addTransition ("EditHours", "Up", a9_2907, "EditHours");
            addTransition ("EditMinutes", "Down", a9_1187, "EditMinutes");
            addTransition ("Show", "Set", a9_3169, "EditHours");


            addStateDisplay("AlarmRang", -1, METime.MINUTE, d9_717);
            addStateDisplay("EditHours", METime.HOUR_OF_DAY, METime.MINUTE, d9_717);
            addStateDisplay("EditMinutes", METime.MINUTE, METime.MINUTE, d9_717);
            addStateDisplay("Show", -1, METime.MINUTE, d9_717);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_420:
				setAlarm("AlarmClock", true, "AlarmRang", getalarmTime().meMinus(getclockTime()));
				iconOn("alarm");
				return null;
			case a9_1187:
				getalarmTime().roll(METime.MINUTE, false, displayTime());
				return null;
			case a9_2126:
				getalarmTime().roll(METime.HOUR_OF_DAY, false, displayTime());
				return null;
			case a9_2134:
				iconOff("alarm");
				return null;
			case a9_2597:
				getalarmTime().roll(METime.MINUTE, true, displayTime());
				return null;
			case a9_2907:
				getalarmTime().roll(METime.HOUR_OF_DAY, true, displayTime());
				return null;
			case a9_3169:
				iconOff("alarm");
				killAlarm("AlarmClock");
				return null;
			case d9_717:
				return getalarmTime();
		}
		return null;
	}
}