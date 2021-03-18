package graphics;

import java.awt.*;
import java.awt.event.*;
import physics.*;
import titan.Vector3dInterface;
import physics.SolarSystem;

public class myFrame extends Frame {
	private Image offScreenImage = null;

	private static boolean zoomOrNot;
	private static double zoomFactor = 1;
	Vector3dInterface[][] locations;
	double stepSize = 90000;
	int step = 1;
	SolarSystem sol;

	Image b;
	Star sun;
	Planet mercury;
	Planet venus;
	Planet earth;
	Planet moon;
	Planet mars;
	Planet jupiter;
	Planet saturn;
	Planet titan;
	Planet uranus;
	Planet neptune;
	
	public void initV()
	{
		b = Planet.getImage("I//bg.jpg");
		sun = new Star("I//sun.png","sun",locations[0][step-2]);
		mercury = new Planet(sun, "I//moon.png", "mercury", locations[1][step-2]);
		venus = new Planet(sun, "I//moon.png", "venus", locations[2][step-2]);
		earth = new Planet(sun, "I//earth.png","earth",locations[3][step-2]);
		moon = new Planet(sun, "I//moon.png","moon", locations[4][step-2]);
		mars = new Planet(sun, "I//moon.png", "mars", locations[5][step-2]);
		jupiter = new Planet(sun, "I//moon.png", "jupiter", locations[6][step-2]);
		saturn = new Planet(sun,"I//saturn.png","saturn", locations[7][step-2]);
		titan = new Planet(saturn,"I//titan.png","titan", locations[8][step-2]);
		uranus = new Planet(sun, "I//moon.png", "uranus", locations[9][step-2]);
		neptune = new Planet(sun, "I//moon.png", "neptune", locations[10][step-2]);
	}

	public myFrame(String s){
		calculation();
		initV();
		setSize(800, 600);
		setLocation(100, 100);
		
		setVisible(true);
		new PaintThread().start();

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoomOrNot = true;

				if (e.getWheelRotation() > 0) {
					zoomFactor *= 0.8;
					sizeToZoom();
					repaint();
				}

				if (e.getWheelRotation() < 0) {
					zoomFactor *= 1.2 ;
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

		if(zoomFactor>1.3) {
			zoomFactor = 1.3;
		}else if (zoomFactor < 0.8) {
			zoomFactor = 0.8;
		}
		sun.resize(zoomFactor);
		mercury.resize(zoomFactor);
		venus.resize(zoomFactor);
		earth.resize(zoomFactor);
		moon.resize(zoomFactor);
		mars.resize(zoomFactor);
		jupiter.resize(zoomFactor);
		saturn.resize(zoomFactor);
		titan.resize(zoomFactor);
		uranus.resize(zoomFactor);
		neptune.resize(zoomFactor);

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
		mercury.draw(g);
		venus.draw(g);
		earth.draw(g);
		moon.draw(g);
		mars.draw(g);
		jupiter.draw(g);
		saturn.draw(g);
		titan.draw(g);
		uranus.draw(g);
		titan.draw(g);
		calculation();
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
	public void calculation() 
	{
		sol = new SolarSystem(stepSize, step);
		locations = sol.getLocations();
		if (step != 1) 
		{
			sun.setPosition(locations[0][step-1]);
			mercury.setPosition(locations[1][step-1]);
			venus.setPosition(locations[2][step-1]);
			earth.setPosition(locations[3][step-1]);
			moon.setPosition(locations[4][step-1]);
			mars.setPosition(locations[5][step-1]);
			jupiter.setPosition(locations[6][step-1]);
			saturn.setPosition(locations[7][step-1]);
			titan.setPosition(locations[8][step-1]);
			uranus.setPosition(locations[9][step-1]);
			neptune.setPosition(locations[10][step-1]);
			//System.out.println("HERE22: "+step);
			//System.out.println("EARTH POSITION: "+earth.getPosition());
		}


		step++;
		//System.out.println("Step"+step);
		//System.out.println("GOT HERE"+locations.length);
		//System.out.println("GOT HEREearth"+locations[1].length);
		//System.out.println("TEST"+locations[0][step]);
	}

}

