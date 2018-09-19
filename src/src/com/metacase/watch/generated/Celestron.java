package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Celestron extends AbstractWatchApplet {

	private static final long serialVersionUID = 1L;

	public Celestron() {
		master=new Master();
		master.init(this, new DisplayX032(), new Simple(master));
	}
}