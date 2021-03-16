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
	double stepSize = 0.1;
	int step = 1;
	SolarSystem sol;

	Image b;
	Star sun;
	Planet earth;
	Planet moon;
	Planet saturn;
	Planet titan;

	public void initV()
	{
		b = Planet.getImage("I//bg.jpg");
		sun = new Star("I//sun.png",400,300,"sun",locations[0][step-2]);
		earth = new Planet(sun, "I//earth.png", 150, 93, 0.0108,"earth",locations[1][step-2]);
		moon = new Planet(earth, "I//moon.png", 15, 10, 0.1332,"moon", locations[2][step-2]);
		saturn = new Planet(sun,"I//saturn.png",270, 180, 0.0004,"saturn", locations[3][step-2]);
		titan = new Planet(saturn,"I//titan.png",15, 10, 0.1332,"titan", locations[4][step-2]);
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
		earth.resize(zoomFactor);
		moon.resize(zoomFactor);
		saturn.resize(zoomFactor);
		titan.resize(zoomFactor);


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
			earth.setPosition(locations[1][step-1]);
			moon.setPosition(locations[2][step-1]);
			saturn.setPosition(locations[3][step-1]);
			titan.setPosition(locations[4][step-1]);
			//System.out.println("HERE22");
		}
		
		step++;
		//System.out.println("Step"+step);
		//System.out.println("GOT HERE"+locations.length);
		//System.out.println("GOT HEREearth"+locations[1].length);
		//System.out.println("TEST"+locations[0][step]);
	}

}

