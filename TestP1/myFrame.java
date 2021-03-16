package TestP1;

import java.awt.*;
import java.awt.event.*;

public class myFrame extends Frame {
	private Image offScreenImage = null;
	private static boolean zoomOrNot;
	private static int zoomFactor = 0;

	Image b = Planet.getImage("I//bg.jpg");
	Star sun = new Star("I//sun.png",400,300,"sun");
	Planet earth = new Planet(sun, "I//earth.png", 150, 100, 0.01,"earth");
	Planet moon = new Planet(earth, "I//moon.png", 30, 20, 0.1,"moon");
	Planet saturn = new Planet(sun,"I//saturn.png",270, 180, 0.0001,"saturn");
	Planet titan = new Planet(saturn,"I//titan.png",30, 20, 0.1,"titan");

	public myFrame(String s){
		setSize(800, 600);
		setLocation(100, 100);
		setVisible(true);

		new PaintThread().start();

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoomOrNot = true;
				//Zoom out
				if (e.getWheelRotation() > 0) {
					zoomFactor -=1;
					sizeToZoom();
					repaint();
				}
				//Zoom in
				if (e.getWheelRotation() < 0) {
					zoomFactor +=1 ;
					sizeToZoom();
					repaint();
				}
			}
		});


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	protected void sizeToZoom() {
		zoomOrNot = false;

		if(zoomFactor>3 || zoomFactor <-2) {
			zoomFactor = 0;
			return;
		}else {
			sun.resize(zoomFactor);
			earth.resize(zoomFactor);
			moon.resize(zoomFactor);
			saturn.resize(zoomFactor);
			titan.resize(zoomFactor);
		}
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(this.getSize().width, this.getSize().height);
		}
		Graphics gOff = offScreenImage.getGraphics();
		print(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void paint(Graphics g) {
		g.drawImage(b,0,0,null);
		sun.draw(g);
		earth.draw(g);
		moon.draw(g);
		saturn.draw(g);
		titan.draw(g);
	}

	class PaintThread extends Thread {
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

