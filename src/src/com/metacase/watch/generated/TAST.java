package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class TAST extends AbstractWatchApplication {
	static final int d9_475	= 1;



	public TAST(Master master) {
		super(master, "9_386");
            addStateOop("Start [Watch]", "9_519");
            addStateOop("AlarmClock", "9_3007");
            addStateOop("Stopwatch", "9_2773");
            addStateOop("Time", "9_1549");
            addStateOop("Timer", "9_3442");

            addTransition ("AlarmClock", "", 0, "Stopwatch");
            addTransition ("Stopwatch", "", 0, "Timer");
            addTransition ("Time", "", 0, "AlarmClock");
            addTransition ("Start [Watch]", "", 0, "Time");
            addTransition ("Timer", "", 0, "Time");

            addDecomposition("AlarmClock", "AlarmClock", new AlarmClock(master));
            addDecomposition("Stopwatch", "Stopwatch", new Stopwatch(master));
            addDecomposition("Time", "Time", new Time(master));
            addDecomposition("Timer", "Timer", new Timer(master));

            addStateDisplay("AlarmClock", -1, METime.MINUTE, d9_475);
            addStateDisplay("Stopwatch", -1, METime.MINUTE, d9_475);
            addStateDisplay("Time", -1, METime.MINUTE, d9_475);
            addStateDisplay("Timer", -1, METime.MINUTE, d9_475);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case d9_475:
				return getClockTime();
		}
		return null;
	}
}