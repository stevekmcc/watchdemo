package com.metacase.watch.framework;

/**
 * (C) 2001 MetaCase Consulting.
 * Our own time class: existing time classes provide poor support for the behaviour we need, and seem downright buggy in places
 */
import java.util.*;

public class METime {
	public static final int HOUR_OF_DAY=0;
	public static final int MINUTE=1;
	public static final int SECOND=2;
	public static final int HUNDREDTH=3;
	public static final int[] sizes={24, 60, 60, 100};

	public int[] unit = new int[4];

	public METime() {
		for(int i=HOUR_OF_DAY; i<=HUNDREDTH; i++)
			set(i,0);
	}

	public METime(int hours, int minutes, int seconds) {
		set(HOUR_OF_DAY, hours);
		set(MINUTE, minutes);
		set(SECOND, seconds);
		set(HUNDREDTH, 0);
	}

	public METime meMinus(METime t) {
		METime t1 = new METime();
		for(int i = HOUR_OF_DAY; i <= HUNDREDTH; i++) {
			t1.add(i, get(i)-(t.get(i)));
		}
		return t1;
	}

	public METime mePlus(METime t) {
		METime t1 = new METime();
		for(int i = HOUR_OF_DAY; i<=HUNDREDTH; i++) {
			t1.add(i, get(i)+t.get(i));
		}
		return t1;
	}

	public void add(int field, int value) {
		unit[field] = unit[field]+value;
		normalise();
	}

	public int get(int field) {
		return unit[field];
	}

	public void set(int field, int value) {
		unit[field] = value;
	}

	public void roll(int field, boolean up) {
		if (up == true) {
			unit[field] = unit[field]+1;
			if (unit[field] >= sizes[field]) unit[field]=0;
		}
		else {
			unit[field] = unit[field]-1;
			if (unit[field] < 0) unit[field] = sizes[field]-1;
		}
	}

	public void roll(int field, boolean up, METime displayTime)	{
		METime newDisplayTime = new METime();
		newDisplayTime.setFromMilliseconds(displayTime.asMilliseconds());
		newDisplayTime.roll(field, up);
		setFromMilliseconds(newDisplayTime.asMilliseconds() - displayTime.asMilliseconds() + this.asMilliseconds());
	}

	public int asMilliseconds()	{
		int i;
		int factor = 10;
		int mills = 0;

		for (i = HUNDREDTH; i >= HOUR_OF_DAY; i--) {
			mills = mills + (get(i) * factor);
			factor = factor * sizes[i];
		}
		return mills;
	}

	public void setFromMilliseconds(int mills) {
		int i;
		int left;
		int temp;
		int factor = 10;

		left = mills;

		for (i = HUNDREDTH; i>=HOUR_OF_DAY; i--) {
			factor = factor * sizes[i];
		}
			
		for (i = HOUR_OF_DAY; i <= HUNDREDTH; i++) {
			factor = factor / sizes[i];
			temp = left / factor;
			set(i, temp % sizes[i]);
			left = left - (temp * factor);
		}
	}

	public void normalise()	{
		setFromMilliseconds(asMilliseconds());
	}

	public void setFromSysClock() {
		Calendar cal = Calendar.getInstance();
		set(HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
		set(MINUTE, cal.get(Calendar.MINUTE));
		set(SECOND, cal.get(Calendar.SECOND));
		set(HUNDREDTH, ((int)((System.currentTimeMillis()) % 1000)) / 10);
	}
}
