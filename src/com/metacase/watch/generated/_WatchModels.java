package com.metacase.watch.generated;

import com.metacase.watch.framework.*;


import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.lang.reflect.*;

public class _WatchModels {

	static final Image icon = loadIcon();
	
    public static void addComponents(Container contentPane) {
    	// Set layout
    	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    	
    	//Add Labels and set fonts
    	contentPane.add(Box.createVerticalGlue());
    	JLabel label1 = new JLabel("Digital Watch Models");
    	JLabel label2 = new JLabel("WatchModels");
    	label1.setFont(new Font(label1.getFont().getFontName(), label1.getFont().getStyle(), 16));
    	label1.setAlignmentX(Component.CENTER_ALIGNMENT);
    	label2.setFont(new Font(label2.getFont().getName(), label2.getFont().getStyle(), 16));
    	label2.setAlignmentX(Component.CENTER_ALIGNMENT);
    	contentPane.add(label1);
    	contentPane.add(label2);
    	contentPane.add(Box.createVerticalGlue());
    	
    	// Add buttons.
    	addButton("Ace", contentPane);
    	addButton("Celestra", contentPane);
    	addButton("Celestron", contentPane);
    	addButton("Delicia", contentPane);
    	addButton("Sporty", contentPane);
    }

    private static Image loadIcon() {
    	Image _icon = null;
    	try {
    	    // Read from a file
    	    File file = new File("WatchModels.png");
    	    _icon = ImageIO.read(file);
    	} catch(IOException e){ }
    	return _icon;
    }

    private static void setFrameLocation(JFrame f) {
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	
        int w = f.getSize().width;
        int h = f.getSize().height;
        int x = (dim.width-w)/2 - w/2;
        int y = (dim.height-h)/2 - h/2;
        
    	f.setLocation(x, y);
    }    


    private static void addButton(final String appName, final Container cont) {
    	JButton button;
	    button = new JButton(new AbstractAction(appName) {
	    		private static final long serialVersionUID = 1L;
	    		public void actionPerformed(ActionEvent e) {
	    		SwingUtilities.invokeLater(new Runnable() {
	    			@SuppressWarnings("unchecked")
	    			@Override
	    			public void run() {
	    		    			Class<AbstractWatchApplet> appClass;
	    		    			String packageName = this.getClass().getPackage().getName();
	    		    			try {
	    		    				appClass = (Class<AbstractWatchApplet>) Class.forName(packageName + "." + appName);
	    		    				Class<?> partypes[] = new Class[0];
	    		    				Constructor<?> ct = null;
	                        	    	    ct = appClass.getConstructor(partypes);    
	                        	    	    Object arglist[] = new Object[0];
	                        	    	    Object app = ct.newInstance(arglist);
	                        	    	    startAndShowApplet((AbstractWatchApplet)app, cont);
						} catch (Exception e) { 
						    e.printStackTrace();
						}
	    		}
						
	                });
	    		}
	    	});
    	button.setAlignmentX(Component.CENTER_ALIGNMENT);
    	button.setMaximumSize(new Dimension(105, 45)); // For some reason (BoxLayout maybe?) this method sets only width...
    	button.setPreferredSize(new Dimension(105, 30)); // ... and this sets height!! :)
    	cont.add(button);
    	cont.add(Box.createVerticalGlue());
    }
    
    private static void startAndShowApplet(Applet a, Container cont) {
    	JFrame f = new JFrame(a.getClass().getName());
		a.init();
		a.start();
		f.add(a);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setPreferredSize(new Dimension(240, 200));
		f.setIconImage(icon);
		f.setResizable(true);
		f.setLocationRelativeTo(cont);
		f.pack();
		f.setVisible(true);
    }
    
    private static void createAndShowGUI() {
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
		JFrame frame = new JFrame("Digital watch");
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	addComponents(frame.getContentPane());
    	
    	frame.setPreferredSize(new Dimension(210, 330));
    	frame.setIconImage(icon);
    	frame.pack();
    	frame.setVisible(true);
    	frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent we){
    			System.exit(0);
    		}
		});
    	setFrameLocation(frame);
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
