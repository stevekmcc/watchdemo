package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class WorldTime extends AbstractWatchApplication {
	static final int a9_521	= 1;
	static final int a9_2056	= 2;
	static final int a9_2830	= 3;
	static final int a9_3248	= 4;
	static final int d9_2520	= 5;
	public METime offset = new METime();

	public METime getoffset() {
		return offset;
	}
	public void setoffset(METime t1) {
		offset = t1;
	}


	public WorldTime(Master master) {
		super(master, "9_383");
            addStateOop("Start [Watch]", "9_809");
            addStateOop("EditHours", "9_1595");
            addStateOop("EditMinutes", "9_3498");
            addStateOop("Show", "9_2825");
            addStateOop("Stop [Watch]", "9_1282");

            addTransition ("Show", "Set", 0, "EditHours");
            addTransition ("EditHours", "Set", 0, "Show");
            addTransition ("EditMinutes", "Mode", 0, "EditHours");
            addTransition ("EditMinutes", "Set", 0, "Show");
            addTransition ("Start [Watch]", "", 0, "Show");
            addTransition ("EditMinutes", "Up", a9_2830, "EditMinutes");
            addTransition ("EditHours", "Mode", 0, "EditMinutes");
            addTransition ("EditMinutes", "Down", a9_3248, "EditMinutes");
            addTransition ("EditHours", "Up", a9_521, "EditHours");
            addTransition ("Show", "Mode", 0, "Stop [Watch]");
            addTransition ("EditHours", "Down", a9_2056, "EditHours");


            addStateDisplay("EditHours", METime.HOUR_OF_DAY, METime.MINUTE, d9_2520);
            addStateDisplay("EditMinutes", METime.MINUTE, METime.MINUTE, d9_2520);
            addStateDisplay("Show", -1, METime.MINUTE, d9_2520);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_521:
				getoffset().roll(METime.HOUR_OF_DAY, true, displayTime());
				return null;
			case a9_2056:
				getoffset().roll(METime.HOUR_OF_DAY, false, displayTime());
				return null;
			case a9_2830:
				getoffset().roll(METime.MINUTE, true, displayTime());
				return null;
			case a9_3248:
				getoffset().roll(METime.MINUTE, false, displayTime());
				return null;
			case d9_2520:
				return getclockTime().mePlus(getoffset());
		}
		return null;
	}
}