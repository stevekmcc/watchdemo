package com.metacase.watch.framework;

import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;

/**
 * (C) 2002 MetaCase Consulting
 * The applet. Handles input via buttons, delegates state machine to Master and displaying to WatchCanvas.
 */
public abstract class AbstractWatchApplet extends Applet implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public Master master;
	public TextField tf;
	private WatchCanvas watchCanvas;
	private Panel buttonPanel;
	private Panel debugPanel;
	
	public void init() {
		Button button;
		int i;

		setBackground(Color.white);
		watchCanvas = new WatchCanvas(master);
		add("Screen", watchCanvas);

		buttonPanel = new Panel();
		add("Buttons", buttonPanel);
		for (i = 0; i < master.display.buttons.size(); i++) {
			button = new Button((String)(master.display.buttons.elementAt(i)));
			button.setForeground(Color.red);
			buttonPanel.add(button); button.addActionListener(this);
		}

		debugPanel = new Panel();
		add("Debug", debugPanel);
		tf = new TextField("State           ");
		debugPanel.add(tf);
	}

	public void start() {
		watchCanvas.startAnimation();
	}

	public void stop() {
		watchCanvas.stopAnimation();
	}

	public void actionPerformed(ActionEvent e) {
		master.sendEvent(e.getActionCommand());
	}

	public void ringAlarm() {	}
}

