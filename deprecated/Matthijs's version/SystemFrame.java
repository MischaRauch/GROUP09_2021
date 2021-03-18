/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is for testing code
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.event.*;
import titan.Vector3dInterface;
import java.awt.Color;

public class SystemFrame extends JFrame implements ActionListener
{
  public static JLayeredPane lPane = new JLayeredPane();
  //public static JPanel animation = new JPanel();
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
  public CelestialBodyComponent cbc;
  public static int stepCount = 0;
  public static double scale = 0.000000001;
  private double sizeScale = 1;
  public static double xOffset = 700;
  public static double yOffset = 400;
  private int sunDiameter = 1392700000;
  private int mercuryDiameter = 4879400;
  private int venusDiameter = 12104000;
  private int earthDiameter = 12742000;
  private int moonDiameter = 3474200;
  private int marsDiameter = 6779000;
  private int jupiterDiameter = 139820000;
  private int saturnDiameter = 116460000;
  private int titanDiameter = 5149500;
  private int uranusDiameter = 50724000;
  private int neptuneDiameter = 49244000;

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
        scale *= 1.25;
        sizeScale *= 1.25;
      }
    });
    lPane.add(zoomInButton);
    JButton zoomOutButton = new JButton("Zoom out");
    zoomOutButton.setBounds(1300,760,100,30);
    zoomOutButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        scale *= 0.8;
        sizeScale *= 0.8;
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

    sun = new CelestialBodyComponent("Sun", locations[0][stepCount].mul(scale).getX()+xOffset, locations[0][stepCount].mul(scale).getY()+yOffset, sunDiameter*scale, new Color(232, 138, 37));
    mercury = new CelestialBodyComponent("Mercury", locations[1][stepCount].mul(scale).getX()+xOffset, locations[1][stepCount].mul(scale).getY()+yOffset, mercuryDiameter*scale, new Color(112, 109, 107));
    venus = new CelestialBodyComponent("Venus", locations[2][stepCount].mul(scale).getX()+xOffset, locations[2][stepCount].mul(scale).getY()+yOffset, venusDiameter*scale, new Color(196, 165, 143));
    earth = new CelestialBodyComponent("Earth", locations[3][stepCount].mul(scale).getX()+xOffset, locations[3][stepCount].mul(scale).getY()+yOffset, earthDiameter*scale, new Color(38, 120, 60));
    moon = new CelestialBodyComponent("Moon", locations[4][stepCount].mul(scale).getX()+xOffset, locations[4][stepCount].mul(scale).getY()+yOffset, moonDiameter*scale, new Color(171, 169, 167));
    mars = new CelestialBodyComponent("Mars", locations[5][stepCount].mul(scale).getX()+xOffset, locations[5][stepCount].mul(scale).getY()+yOffset, marsDiameter*scale, new Color(199, 114, 30));
    jupiter = new CelestialBodyComponent("Jupiter", locations[6][stepCount].mul(scale).getX()+xOffset, locations[6][stepCount].mul(scale).getY()+yOffset, jupiterDiameter*scale, new Color(209, 142, 84));
    saturn = new CelestialBodyComponent("Saturn", locations[7][stepCount].mul(scale).getX()+xOffset, locations[7][stepCount].mul(scale).getY()+yOffset, saturnDiameter*scale, new Color(212, 169, 131));
    titan = new CelestialBodyComponent("Titan", locations[8][stepCount].mul(scale).getX()+xOffset, locations[8][stepCount].mul(scale).getY()+yOffset, titanDiameter*scale, new Color(115, 191, 135));
    uranus = new CelestialBodyComponent("Uranus", locations[9][stepCount].mul(scale).getX()+xOffset, locations[9][stepCount].mul(scale).getY()+yOffset, uranusDiameter*scale, new Color(190, 232, 237));
    neptune = new CelestialBodyComponent("Neptune", locations[10][stepCount].mul(scale).getX()+xOffset, locations[10][stepCount].mul(scale).getY()+yOffset, neptuneDiameter*scale, new Color(55, 86, 212));

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
    //lPane.add(animation);


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

    sun.setSize(sunDiameter*scale);
    mercury.setSize(mercuryDiameter*scale);
    venus.setSize(venusDiameter*scale);
    earth.setSize(earthDiameter*scale);
    moon.setSize(moonDiameter*scale);
    mars.setSize(marsDiameter*scale);
    jupiter.setSize(jupiterDiameter*scale);
    saturn.setSize(saturnDiameter*scale);
    titan.setSize(titanDiameter*scale);
    uranus.setSize(uranusDiameter*scale);
    neptune.setSize(neptuneDiameter*scale);


    repaint();
    if(stepCount == 19999)
    {
      stepCount = 0;
    }
  }
}
