/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is for testing code
 */

import javax.swing.*;

import jdk.dynalink.beans.StaticClass;

import java.awt.*;
import java.awt.event.*;
import titan.Vector3dInterface;
import java.awt.Color;

public class SystemFrame extends JFrame implements ActionListener
{
  public static JLayeredPane lPane = new JLayeredPane();
  public static Vector3dInterface[][] locations;
  private Timer timer = new Timer(1, this);
  private CelestialBodyComponent sun;
  private CelestialBodyComponent mercury;
  private CelestialBodyComponent venus;
  private CelestialBodyComponent earth;
  private CelestialBodyComponent moon;
  private CelestialBodyComponent mars;
  private CelestialBodyComponent jupiter;
  private CelestialBodyComponent saturn;
  private CelestialBodyComponent titan;
  private CelestialBodyComponent uranus;
  private CelestialBodyComponent neptune;
  private RocketComponent probe;
  public static int stepCount = 0;
  public static double scale = 0.000000001;
  public static double xOffset = 700;
  public static double yOffset = 400;

  public SystemFrame() {}
  public SystemFrame(Vector3dInterface[][] locations)
  {
    lPane.setOpaque(true);
    lPane.setBackground(Color.BLACK);
    lPane.setLayout(null);
    this.locations = locations;
    this.setSize(2560, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(Color.BLACK);
    this.setVisible(true);
    JButton zoomInButton = new JButton("Zoom in");
    zoomInButton.setBounds(1300,800,100,30);
    zoomInButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scale *= 2;
      }
    });
    lPane.add(zoomInButton);
    JButton zoomOutButton = new JButton("Zoom out");
    zoomOutButton.setBounds(1300,760,100,30);
    zoomOutButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scale *= 0.5;
      }
    });
    lPane.add(zoomOutButton);

    JButton panRightButton = new JButton("Pan right");
    panRightButton.setBounds(1200,760,100,30);
    panRightButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        xOffset -= 100;
      }
    });
    lPane.add(panRightButton);
    JButton panLeftButton = new JButton("Pan left");
    panLeftButton.setBounds(1200,800,100,30);
    panLeftButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        xOffset += 100;
      }
    });
    lPane.add(panLeftButton);

    JButton panUpButton = new JButton("Pan up");
    panUpButton.setBounds(1100,760,100,30);
    panUpButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        yOffset += 100;
      }
    });
    lPane.add(panUpButton);
    JButton panDownButton = new JButton("Pan down");
    panDownButton.setBounds(1100,800,100,30);
    panDownButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        yOffset -= 100;
      }
    });
    lPane.add(panDownButton);

    sun = new CelestialBodyComponent("Sun", locations[0][stepCount].mul(scale).getX()+xOffset, locations[0][stepCount].mul(scale).getY()+yOffset, 696342e5*scale, new Color(232, 138, 37));
    mercury = new CelestialBodyComponent("Mercury", locations[1][stepCount].mul(scale).getX()+xOffset, locations[1][stepCount].mul(scale).getY()+yOffset, 4879.4e5*scale, new Color(112, 109, 107));
    venus = new CelestialBodyComponent("Venus", locations[2][stepCount].mul(scale).getX()+xOffset, locations[2][stepCount].mul(scale).getY()+yOffset, 12104e5*scale, new Color(196, 165, 143));
    earth = new CelestialBodyComponent("Earth", locations[3][stepCount].mul(scale).getX()+xOffset, locations[3][stepCount].mul(scale).getY()+yOffset, 12742e5*scale, new Color(38, 120, 60));
    moon = new CelestialBodyComponent("Moon", locations[4][stepCount].mul(scale).getX()+xOffset, locations[4][stepCount].mul(scale).getY()+yOffset, 3474.2e5*scale, new Color(171, 169, 167));
    mars = new CelestialBodyComponent("Mars", locations[5][stepCount].mul(scale).getX()+xOffset, locations[5][stepCount].mul(scale).getY()+yOffset, 6779e5*scale, new Color(199, 114, 30));
    jupiter = new CelestialBodyComponent("Jupiter", locations[6][stepCount].mul(scale).getX()+xOffset, locations[6][stepCount].mul(scale).getY()+yOffset, 139820e5*scale, new Color(209, 142, 84));
    saturn = new CelestialBodyComponent("Saturn", locations[7][stepCount].mul(scale).getX()+xOffset, locations[7][stepCount].mul(scale).getY()+yOffset, 116460e5*scale, new Color(212, 169, 131));
    titan = new CelestialBodyComponent("Titan", locations[8][stepCount].mul(scale).getX()+xOffset, locations[8][stepCount].mul(scale).getY()+yOffset, 5149.5e5*scale, new Color(115, 191, 135));
    uranus = new CelestialBodyComponent("Uranus", locations[9][stepCount].mul(scale).getX()+xOffset, locations[9][stepCount].mul(scale).getY()+yOffset, 50724e5*scale, new Color(190, 232, 237));
    neptune = new CelestialBodyComponent("Neptune", locations[10][stepCount].mul(scale).getX()+xOffset, locations[10][stepCount].mul(scale).getY()+yOffset, 49224e5*scale, new Color(55, 86, 212));
    probe = new RocketComponent(locations[11][stepCount].mul(scale).getX()+xOffset, locations[11][stepCount].mul(scale).getY()+yOffset, 50e7*scale, 250e7*scale);

    lPane.add(mercury);
    lPane.add(venus);
    lPane.add(moon);
    lPane.add(earth);
    lPane.add(mars);
    lPane.add(jupiter);
    lPane.add(titan);
    lPane.add(saturn);
    lPane.add(uranus);
    lPane.add(sun);
    lPane.add(neptune);
    lPane.add(probe);

    timer.start();

    this.add(lPane);
    this.revalidate();
  }

  public void actionPerformed(ActionEvent e)
  {
    stepCount += 1;
    sun.setCoordinates(locations[0][stepCount].mul(scale).getX()+xOffset, locations[0][stepCount].mul(scale).getY()+yOffset);
    mercury.setCoordinates(locations[1][stepCount].mul(scale).getX()+xOffset, locations[1][stepCount].mul(scale).getY()+yOffset);
    venus.setCoordinates(locations[2][stepCount].mul(scale).getX()+xOffset, locations[2][stepCount].mul(scale).getY()+yOffset);
    earth.setCoordinates(locations[3][stepCount].mul(scale).getX()+xOffset, locations[3][stepCount].mul(scale).getY()+yOffset);
    moon.setCoordinates(locations[4][stepCount].mul(scale).getX()+xOffset, locations[4][stepCount].mul(scale).getY()+yOffset);
    mars.setCoordinates(locations[5][stepCount].mul(scale).getX()+xOffset, locations[5][stepCount].mul(scale).getY()+yOffset);
    jupiter.setCoordinates(locations[6][stepCount].mul(scale).getX()+xOffset, locations[6][stepCount].mul(scale).getY()+yOffset);
    saturn.setCoordinates(locations[7][stepCount].mul(scale).getX()+xOffset, locations[7][stepCount].mul(scale).getY()+yOffset);
    titan.setCoordinates(locations[8][stepCount].mul(scale).getX()+xOffset, locations[8][stepCount].mul(scale).getY()+yOffset);
    uranus.setCoordinates(locations[9][stepCount].mul(scale).getX()+xOffset, locations[9][stepCount].mul(scale).getY()+yOffset);
    neptune.setCoordinates(locations[10][stepCount].mul(scale).getX()+xOffset, locations[10][stepCount].mul(scale).getY()+yOffset);
    probe.setCoordinates(locations[11][stepCount].mul(scale).getX()+xOffset, locations[11][stepCount].mul(scale).getY()+yOffset);

    sun.setSize(696342e5*scale);
    mercury.setSize(4879.4e5*scale);
    venus.setSize(12104e5*scale);
    earth.setSize(12742e5*scale);
    moon.setSize(3474.2e5*scale);
    mars.setSize(6679e5*scale);
    jupiter.setSize(139820e5*scale);
    saturn.setSize(116460e5*scale);
    titan.setSize(5149.5e5*scale);
    uranus.setSize(50724e5*scale);
    neptune.setSize(49224e5*scale);
    probe.setSize(50e7*scale, 250e7*scale);

    if(stepCount % 100 == 0)
    {
      System.out.println("Distance between probe and Titan: " + locations[11][stepCount].dist(locations[8][stepCount]));
      System.out.println("Z distance: " + ((Vector3d)(locations[11][stepCount])).zDist(locations[8][stepCount]));
    }

    repaint();
    if(stepCount == locations[0].length-1)
    {
      stepCount = 0;
    }
  }
}
