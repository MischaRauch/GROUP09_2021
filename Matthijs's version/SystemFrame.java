/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is for testing code
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import titan.Vector3dInterface;
import java.awt.Color;

public class SystemFrame extends JFrame implements ActionListener
{
  private JLayeredPane lPane = new JLayeredPane();
  private Vector3dInterface[][] locations;
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
  private int stepCount = 0;
  private double scale = 0.000000001;
  private double sizeScale = 1;
  private double xOffset = 700;
  private double yOffset = 400;

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

    sun = new CelestialBodyComponent("Sun", locations[0][stepCount].mul(scale).getX()+xOffset, locations[0][stepCount].mul(scale).getY()+yOffset, 75*sizeScale, new Color(232, 138, 37));
    mercury = new CelestialBodyComponent("Mercury", locations[1][stepCount].mul(scale).getX()+xOffset, locations[1][stepCount].mul(scale).getY()+yOffset, 5*sizeScale, new Color(112, 109, 107));
    venus = new CelestialBodyComponent("Venus", locations[2][stepCount].mul(scale).getX()+xOffset, locations[2][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(196, 165, 143));
    earth = new CelestialBodyComponent("Earth", locations[3][stepCount].mul(scale).getX()+xOffset, locations[3][stepCount].mul(scale).getY()+yOffset, 5*sizeScale, new Color(38, 120, 60));
    moon = new CelestialBodyComponent("Moon", locations[4][stepCount].mul(scale).getX()+xOffset, locations[4][stepCount].mul(scale).getY()+yOffset, 1*sizeScale, new Color(171, 169, 167));
    mars = new CelestialBodyComponent("Mars", locations[5][stepCount].mul(scale).getX()+xOffset, locations[5][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(199, 114, 30));
    jupiter = new CelestialBodyComponent("Jupiter", locations[6][stepCount].mul(scale).getX()+xOffset, locations[6][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(209, 142, 84));
    saturn = new CelestialBodyComponent("Saturn", locations[7][stepCount].mul(scale).getX()+xOffset, locations[7][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(212, 169, 131));
    titan = new CelestialBodyComponent("Titan", locations[8][stepCount].mul(scale).getX()+xOffset, locations[8][stepCount].mul(scale).getY()+yOffset, 5*sizeScale, new Color(115, 191, 135));
    uranus = new CelestialBodyComponent("Uranus", locations[9][stepCount].mul(scale).getX()+xOffset, locations[9][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(190, 232, 237));
    neptune = new CelestialBodyComponent("Neptune", locations[10][stepCount].mul(scale).getX()+xOffset, locations[10][stepCount].mul(scale).getY()+yOffset, 20*sizeScale, new Color(55, 86, 212));

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

    sun.setSize(75*sizeScale);
    mercury.setSize(5*sizeScale);
    venus.setSize(20*sizeScale);
    earth.setSize(5*sizeScale);
    moon.setSize(1*sizeScale);
    mars.setSize(20*sizeScale);
    jupiter.setSize(20*sizeScale);
    saturn.setSize(20*sizeScale);
    titan.setSize(5*sizeScale);
    uranus.setSize(20*sizeScale);
    neptune.setSize(20*sizeScale);


    repaint();
    if(stepCount == 19999)
    {
      stepCount = 0;
    }
  }
}
