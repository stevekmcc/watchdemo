package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class DisplayX334 extends AbstractDisplay
{
	public DisplayX334()
	{
		icons.addElement(new Icon("alarm"));
		icons.addElement(new Icon("stopwatch"));
		icons.addElement(new Icon("timer"));

		times.addElement(new Zone("Zone1"));
		times.addElement(new Zone("Zone2"));
		times.addElement(new Zone("Zone3"));

		buttons.addElement("Mode");
		buttons.addElement("Set");
		buttons.addElement("Up");
		buttons.addElement("Down");

	}
}