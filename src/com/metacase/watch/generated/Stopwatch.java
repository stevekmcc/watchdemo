package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Stopwatch extends AbstractWatchApplication {
	static final int a9_755	= 1;
	static final int a9_1531	= 2;
	static final int a9_1713	= 3;
	static final int d9_2464	= 4;
	static final int d9_453	= 5;
	public METime startTime = new METime();
	public METime stopTime = new METime();

	public METime getStartTime() {
		return startTime;
	}
	public void setStartTime(METime t1) {
		startTime = t1;
	}
	public METime getStopTime() {
		return stopTime;
	}
	public void setStopTime(METime t1) {
		stopTime = t1;
	}

	public void reset() {
		/* MEPMD5 reset */
		setStopTime(new METime());
		/* MEPMD5 73d0a7b3fab51beeee8ba428b9cd1d47 */
	}

	public Stopwatch(Master master) {
		super(master, "9_392");
            addStateOop("Start [Watch]", "9_457");
            addStateOop("Running", "9_1547");
            addStateOop("Stopped", "9_3446");
            addStateOop("Stop [Watch]", "9_2000");

            addTransition ("Running", "Mode", 0, "Stop [Watch]");
            addTransition ("Start [Watch]", "", 0, "Stopped");
            addTransition ("Stopped", "Mode", 0, "Stop [Watch]");
            addTransition ("Stopped", "Up", a9_755, "Running");
            addTransition ("Stopped", "Down", a9_1713, "Stopped");
            addTransition ("Running", "Up", a9_1531, "Stopped");


            addStateDisplay("Running", -1, METime.SECOND, d9_453);
            addStateDisplay("Stopped", -1, METime.SECOND, d9_2464);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_755:
				iconOn("stopwatch");
				setStartTime(getSysTime().meMinus(getStopTime()));
				return null;
			case a9_1531:
				iconOff("stopwatch");
				setStopTime(getSysTime().meMinus(getStartTime()));
				return null;
			case a9_1713:
				reset();
				return null;
			case d9_2464:
				return getStopTime();
			case d9_453:
				return getSysTime().meMinus(getStartTime());
		}
		return null;
	}
}