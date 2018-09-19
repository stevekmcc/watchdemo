package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Sporty extends AbstractWatchApplet {

	private static final long serialVersionUID = 1L;

	public Sporty() {
		master=new Master();
		master.init(this, new DisplayX234(), new TST(master));
	}
}