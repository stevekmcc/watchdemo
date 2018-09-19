package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Ace extends AbstractWatchApplet {

	private static final long serialVersionUID = 1L;

	public Ace() {
		master=new Master();
		master.init(this, new DisplayX334(), new TASTW(master));
	}
}