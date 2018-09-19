package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Celestra extends AbstractWatchApplet {

	private static final long serialVersionUID = 1L;

	public Celestra() {
		master=new Master();
		master.init(this, new DisplayX022(), new Simple(master));
	}
}