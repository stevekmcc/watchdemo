package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Simple extends AbstractWatchApplication {
	static final int a9_606	= 1;
	static final int a9_1253	= 2;
	static final int a9_1566	= 3;
	static final int a9_2317	= 4;
	static final int d9_1854	= 5;
	static final int d9_1260	= 6;
	public METime tempOffset = new METime();

	public METime getTempOffset() {
		return tempOffset;
	}
	public void setTempOffset(METime t1) {
		tempOffset = t1;
	}


	public Simple(Master master) {
		super(master, "9_371");
            addStateOop("Start [Watch]", "9_3074");
            addStateOop("EditHours", "9_2026");
            addStateOop("EditMinutes", "9_785");
            addStateOop("Show", "9_2326");

            addTransition ("EditHours", "Set", a9_1253, "EditHours");
            addTransition ("EditMinutes", "Set", a9_2317, "EditMinutes");
            addTransition ("EditHours", "Mode", 0, "EditMinutes");
            addTransition ("Start [Watch]", "", 0, "Show");
            addTransition ("Show", "Mode", a9_1566, "EditHours");
            addTransition ("EditMinutes", "Mode", a9_606, "Show");


            addStateDisplay("EditHours", METime.HOUR_OF_DAY, METime.MINUTE, d9_1260);
            addStateDisplay("EditMinutes", METime.MINUTE, METime.MINUTE, d9_1260);
            addStateDisplay("Show", -1, METime.MINUTE, d9_1854);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_606:
				setClockOffset(getTempOffset());
				return null;
			case a9_1253:
				getTempOffset().roll(METime.HOUR_OF_DAY, true, displayTime());
				return null;
			case a9_1566:
				setTempOffset(getClockOffset());
				return null;
			case a9_2317:
				getTempOffset().roll(METime.MINUTE, true, displayTime());
				return null;
			case d9_1854:
				return getClockTime();
			case d9_1260:
				return getSysTime().mePlus(getTempOffset());
		}
		return null;
	}
}