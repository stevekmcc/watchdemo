package com.metacase.watch.generated;

import com.metacase.watch.framework.*;

public class Delicia extends AbstractWatchApplet {

	private static final long serialVersionUID = 1L;

	public Delicia() {
		master=new Master();
		master.init(this, new DisplayX334(), new TAST(master));
	}
}