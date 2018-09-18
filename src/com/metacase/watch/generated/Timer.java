package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Timer extends AbstractWatchApplication {
	static final int a9_942	= 1;
	static final int a9_1102	= 2;
	static final int a9_1726	= 3;
	static final int a9_2018	= 4;
	static final int a9_2181	= 5;
	static final int a9_2482	= 6;
	static final int a9_2661	= 7;
	static final int a9_2828	= 8;
	static final int d9_1243	= 9;
	static final int d9_1588	= 10;
	static final int d9_2653	= 11;
	public METime length = new METime();
	public METime lengthLeft = new METime();
	public METime stopTime = new METime();

	public METime getlength() {
		return length;
	}
	public void setlength(METime t1) {
		length = t1;
	}
	public METime getlengthLeft() {
		return lengthLeft;
	}
	public void setlengthLeft(METime t1) {
		lengthLeft = t1;
	}
	public METime getstopTime() {
		return stopTime;
	}
	public void setstopTime(METime t1) {
		stopTime = t1;
	}


	public Timer(Master master) {
		super(master, "9_395");
            addStateOop("Start [Watch]", "9_1891");
            addStateOop("Counting", "9_467");
            addStateOop("EditHours", "9_2348");
            addStateOop("EditMinutes", "9_773");
            addStateOop("Rang", "9_1420");
            addStateOop("Stopped", "9_3215");
            addStateOop("Stop [Watch]", "9_3107");

            addTransition ("Stopped", "Mode", 0, "Stop [Watch]");
            addTransition ("Counting", "Mode", 0, "Stop [Watch]");
            addTransition ("Stopped", "Down", a9_2828, "Stopped");
            addTransition ("EditMinutes", "Down", a9_2482, "EditMinutes");
            addTransition ("Stopped", "Set", 0, "EditHours");
            addTransition ("EditHours", "Up", a9_2661, "EditHours");
            addTransition ("EditMinutes", "Up", a9_1726, "EditMinutes");
            addTransition ("Start [Watch]", "", 0, "Stopped");
            addTransition ("Stopped", "Up", a9_942, "Counting");
            addTransition ("EditHours", "Down", a9_2181, "EditHours");
            addTransition ("EditMinutes", "Set", a9_2828, "Stopped");
            addTransition ("EditHours", "Mode", 0, "EditMinutes");
            addTransition ("Rang", "", a9_1102, "Stopped");
            addTransition ("EditMinutes", "Mode", 0, "EditHours");
            addTransition ("EditHours", "Set", a9_2828, "Stopped");
            addTransition ("Counting", "Up", a9_2018, "Stopped");


            addStateDisplay("Counting", -1, METime.MINUTE, d9_1588);
            addStateDisplay("EditHours", METime.HOUR_OF_DAY, METime.MINUTE, d9_2653);
            addStateDisplay("EditMinutes", METime.MINUTE, METime.MINUTE, d9_2653);
            addStateDisplay("Rang", -1, METime.MINUTE, d9_1243);
            addStateDisplay("Stopped", -1, METime.MINUTE, d9_1243);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_942:
				setAlarm("Timer", false, "Rang", getlengthLeft());
				iconOn("timer");
				setstopTime(getsysTime().mePlus(getlengthLeft()));
				return null;
			case a9_1102:
				iconOff("timer");
				setlengthLeft(getlength());
				return null;
			case a9_1726:
				getlength().roll(METime.MINUTE, true, displayTime());
				return null;
			case a9_2018:
				killAlarm("Timer");
				iconOff("timer");
				setlengthLeft(getstopTime().meMinus(getsysTime()));
				return null;
			case a9_2181:
				getlength().roll(METime.HOUR_OF_DAY, false, displayTime());
				return null;
			case a9_2482:
				getlength().roll(METime.MINUTE, false, displayTime());
				return null;
			case a9_2661:
				getlength().roll(METime.HOUR_OF_DAY, true, displayTime());
				return null;
			case a9_2828:
				setlengthLeft(getlength());
				return null;
			case d9_1243:
				return getlengthLeft();
			case d9_1588:
				return getstopTime().meMinus(getsysTime());
			case d9_2653:
				return getlength();
		}
		return null;
	}
}