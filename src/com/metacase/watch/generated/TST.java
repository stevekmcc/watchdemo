package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class TST extends AbstractWatchApplication {
	static final int d9_751	= 1;



	public TST(Master master) {
		super(master, "9_389");
            addStateOop("Start [Watch]", "9_2759");
            addStateOop("Stopwatch", "9_2773");
            addStateOop("Time", "9_1549");
            addStateOop("Timer", "9_3442");

            addTransition ("Start [Watch]", "", 0, "Time");
            addTransition ("Time", "", 0, "Stopwatch");
            addTransition ("Stopwatch", "", 0, "Timer");
            addTransition ("Timer", "", 0, "Time");

            addDecomposition("Stopwatch", "Stopwatch", new Stopwatch(master));
            addDecomposition("Time", "Time", new Time(master));
            addDecomposition("Timer", "Timer", new Timer(master));

            addStateDisplay("Stopwatch", -1, METime.SECOND, d9_751);
            addStateDisplay("Time", -1, METime.SECOND, d9_751);
            addStateDisplay("Timer", -1, METime.SECOND, d9_751);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case d9_751:
				return getclockTime();
		}
		return null;
	}
}