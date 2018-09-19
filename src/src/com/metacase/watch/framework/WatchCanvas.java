package com.metacase.watch.framework;

/**
 * (C) 2002 MetaCase Consulting
 * The display canvas. Uses a separate thread for updating the display
 */
import java.awt.*;
import java.util.*;


public class WatchCanvas extends Canvas implements Runnable  {

	private static final long serialVersionUID = 1L;
	private static int MILLIS_PER_TICK = 40;		// update screen every * ms
	private static int bgRGB = 0xdae5da;			// background color
	private static int textRGB = 0x000000;			// text color
	public String appField = "";
	public String stateField = "";
	public Image offscreen;
	private Master master; 							// parent
	private volatile Thread animationThread = null;	// animation thread

	public WatchCanvas(Master master) {
		this.master  = master;
		setSize(new Dimension(120, 80));
	}


	public void startAnimation() {
		animationThread = new Thread(this);
		animationThread.start();
	}


	public void stopAnimation() {
		animationThread = null;
	}

	/**
	 * While this thread has not been stopped, it will perform a
	 * tick() action approximately every MILLIS_PER_TICK milliseconds
	 * (as close to that period as possible).
	 */
	public void run() {
		Thread currentThread = Thread.currentThread();

		try	{
			// This ends when animationThread is set to null, or when
			// it is subsequently set to a new Thread.  Either way, the
			// current thread should terminate.
			while (currentThread == animationThread) {
				long startTime = System.currentTimeMillis();
				tick();
				repaint(10);
				long elapsedTime = 10 + System.currentTimeMillis() - startTime;
				if (elapsedTime < MILLIS_PER_TICK) {
					synchronized(this) {
						wait(MILLIS_PER_TICK - elapsedTime);
					}
				}
			}
		}
		catch(InterruptedException e) {	}
	}


	// What to do each run() tick
	private synchronized void tick() {
		AbstractWatchApplication application;
		METime time;
		String value;
		Vector<Zone> zones;
		Zone zone;
		int keyUnit;
		int center;
		int i;

		application = (AbstractWatchApplication)(master.appStack.peek());
		appField = application.appName();
		stateField = application.currentState;
		time = application.displayTime();
		keyUnit = application.keyUnit();
		zones = master.display.times;
		center = zones.size()/2;

		// Allocate units to zones from roughly center (keyUnit) outwards
		for (i = 0; i <= center; i++) {
			zone=(Zone)(zones.elementAt(center - i));
			zone.unit = keyUnit - i;
			if (i + center >= zones.size())	break;
			zone = (Zone)(zones.elementAt(center+i));
			zone.unit = keyUnit + i;
		}
		
		// Set blinking and string values for each zone
		for (i = 0; i < zones.size(); i++) {
			zone=(Zone)(zones.elementAt(i));
			
			if (zone.unit == master.blinkingUnit) zone.blinking = true;
			else zone.blinking = false;
			
			if(zone.unit<METime.HOUR_OF_DAY || zone.unit>METime.HUNDREDTH) value = "  ";
			else value = String.valueOf(time.get(zone.unit));
			
			zone.setValue(value);
		}
	}

	// null out the offscreen buffer as part of invalidation
	public void invalidate() {
		super.invalidate();
		offscreen = null;
	}
		
	// override update to NOT erase the background before painting
	public void update(Graphics g) {
		paint(g);
	}

	// paint children into an offscreen buffer, then blast entire image at once
	public void paint(Graphics g) {
		if(offscreen == null) {
			offscreen = createImage(getSize().width, getSize().height);
		}

		Graphics og = offscreen.getGraphics();
		og.setClip(0, 0, getSize().width, getSize().height);
		dbPaint(og);
		g.drawImage(offscreen, 0, 0, null);
		og.dispose();
	}
	

	//Draw the background and the time digit pairs
	public void dbPaint(Graphics g) {
		master.applet.tf.setText(stateField);
		int width = getSize().width;
		int height = getSize().height;
		int x, y, charWidth, jdk1;
		Font f;
		FontMetrics fm;
		
		// Clear display
		g.setColor(new Color(bgRGB));
		g.fillRect(0, 0, width, height);

		// Paint current app name
		f=Font.decode("Arial-PLAIN-12");
		g.setFont(f);
		fm = g.getFontMetrics();
		jdk1 = fm.getMaxAscent();
		
		g.setColor(new Color(textRGB));
		g.drawString(appField, (width-fm.stringWidth(appField))/2, 0 + jdk1);
		y = 2 * fm.getHeight();

		// Paint icons
		f = Font.decode("Arial-BOLD-14");
		g.setFont(f);
		fm = g.getFontMetrics();
		jdk1 = fm.getMaxAscent();
		charWidth = fm.stringWidth("m");

		x = (width - charWidth*(2 * master.display.icons.size() - 1)) / 2;
		
		for (Icon icon : master.display.icons) {
			if (icon.on) {
				g.drawRect(x - 2, y - 1, charWidth + 2, fm.getHeight() + 1);
				g.drawString(icon.iconName.substring(0,1), x, y + jdk1);
			}
			x += 2 * charWidth;
		}
		y += fm.getHeight();
		
		// Paint time digits
		f=Font.decode("Arial-BOLD-18");
		g.setFont(f);
		fm = g.getFontMetrics();
		jdk1 = fm.getMaxAscent();
		charWidth = fm.stringWidth("1");

		x = (width - charWidth * (3 * master.display.times.size() - 1)) / 2;
		y += (height - y - fm.getHeight()) / 2;
		
		for (Zone zone : master.display.times) {
			String zoneString;
			
			if (zone.blinking && ((System.currentTimeMillis()) % 1000) < 500) zoneString = "  ";
			else zoneString = zone.zoneValue;
				
			g.drawString(zoneString, x, y + jdk1);
			x += 3 * charWidth;
		}	
	}
}