package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Time extends AbstractWatchApplication {
	static final int a9_793	= 1;
	static final int a9_1070	= 2;
	static final int a9_1862	= 3;
	static final int a9_2324	= 4;
	static final int a9_2615	= 5;
	static final int a9_2624	= 6;
	static final int a9_3227	= 7;
	static final int a9_3320	= 8;
	static final int d9_1258	= 9;
	static final int d9_3080	= 10;
	public METime tempOffset = new METime();

	public METime getTempOffset() {
		return tempOffset;
	}
	public void setTempOffset(METime t1) {
		tempOffset = t1;
	}


	public Time(Master master) {
		super(master, "9_374");
            addStateOop("Start [Watch]", "9_1572");
            addStateOop("EditHours", "9_1562");
            addStateOop("EditMinutes", "9_1386");
            addStateOop("EditSeconds", "9_2802");
            addStateOop("ExitEdit", "9_2034");
            addStateOop("Show", "9_3478");
            addStateOop("Stop [Watch]", "9_908");

            addTransition ("Start [Watch]", "", 0, "Show");
            addTransition ("EditMinutes", "Down", a9_793, "EditMinutes");
            addTransition ("EditHours", "Mode", 0, "EditMinutes");
            addTransition ("EditMinutes", "Up", a9_3227, "EditMinutes");
            addTransition ("EditSeconds", "Down", a9_2615, "EditSeconds");
            addTransition ("EditSeconds", "Up", a9_2624, "EditSeconds");
            addTransition ("ExitEdit", "", a9_2324, "Show");
            addTransition ("EditHours", "Set", 0, "ExitEdit");
            addTransition ("EditMinutes", "Mode", 0, "EditSeconds");
            addTransition ("EditHours", "Up", a9_1070, "EditHours");
            addTransition ("Show", "Mode", 0, "Stop [Watch]");
            addTransition ("EditSeconds", "Set", 0, "ExitEdit");
            addTransition ("EditMinutes", "Set", 0, "ExitEdit");
            addTransition ("Show", "Set", a9_3320, "EditHours");
            addTransition ("EditHours", "Down", a9_1862, "EditHours");
            addTransition ("EditSeconds", "Mode", 0, "EditHours");


            addStateDisplay("EditHours", METime.HOUR_OF_DAY, METime.MINUTE, d9_3080);
            addStateDisplay("EditMinutes", METime.MINUTE, METime.MINUTE, d9_3080);
            addStateDisplay("EditSeconds", METime.SECOND, METime.MINUTE, d9_3080);
            addStateDisplay("ExitEdit", -1, METime.MINUTE, d9_3080);
            addStateDisplay("Show", -1, METime.MINUTE, d9_1258);
	};


	public Object perform(int methodId)
	{
		switch (methodId) {
			case a9_793:
				getTempOffset().roll(METime.MINUTE, false, displayTime());
				return null;
			case a9_1070:
				getTempOffset().roll(METime.HOUR_OF_DAY, true, displayTime());
				return null;
			case a9_1862:
				getTempOffset().roll(METime.HOUR_OF_DAY, false, displayTime());
				return null;
			case a9_2324:
				setClockOffset(getTempOffset());
				return null;
			case a9_2615:
				getTempOffset().roll(METime.SECOND, false, displayTime());
				return null;
			case a9_2624:
				getTempOffset().roll(METime.SECOND, true, displayTime());
				return null;
			case a9_3227:
				getTempOffset().roll(METime.MINUTE, true, displayTime());
				return null;
			case a9_3320:
				setTempOffset(getClockOffset());
				return null;
			case d9_1258:
				return getClockTime();
			case d9_3080:
				return getSysTime().mePlus(getTempOffset());
		}
		return null;
	}
}