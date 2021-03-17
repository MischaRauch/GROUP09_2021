/*
 * @author Group09
 * @version 0.99.0
 *
 * This class is for testing code
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import titan.Vector3dInterface;

import java.awt.geom.*;

public class CelestialBodyComponent extends JComponent
{
  private double x;
  private double y;
  private double d;
  private Color c;
  private JLabel label;
  private String name;

  public CelestialBodyComponent(String name, double x, double y, double d, Color c)
  {
    this.setBounds(0,0,2560, 1000);
    this.x = x;
    this.y = y;
    this.d = d;
    this.c = c;
    this.name = name;
    label = new JLabel(name);
    label.setForeground(Color.WHITE);
    label.setVisible(true);
    if(name.equals("Moon") || name.equals("Titan"))
    {
      label.setBounds((int) (x-10), (int) (y-51), 100, 100);
      label.setFont((new Font(label.getFont().getName(), Font.PLAIN, 8)));
    }
    else
    {
      label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    }
    this.add(label);
  }

  public void setSize(double d)
  {
    this.d = d;
  }

  public void setCoordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void draw(Graphics2D g2)
  {
    if(name.equals("Moon") || name.equals("Titan"))
    {
      label.setBounds((int) (x-10), (int) (y-51), 100, 100);
      label.setFont((new Font(label.getFont().getName(), Font.PLAIN, 8)));
    }
    else
    {
      label.setBounds((int) (x-18), (int) (y-62), 100, 100);
    }
    Ellipse2D.Double planet = new Ellipse2D.Double(x-(0.5*d), y-(0.5*d), d, d);
    g2.setStroke(new BasicStroke(3));
    g2.setColor(c);
    g2.fill(planet);
    g2.draw(planet);
  }

  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    draw(g2);
    paintTrajectory(g2);
  }
  public void paintTrajectory(Graphics2D g)
  {
    SystemFrame sF = new SystemFrame();
    int oldStepCount = 0;
    if(sF.stepCount % 150 == 0)
    {
      int tmp;
      for (tmp = oldStepCount; tmp < sF.stepCount; tmp+=500) 
      {
        if(this.name == "Mercury")
        {
          JPanel mercury = new JPanel();
          mercury.setOpaque(true);
          mercury.setBackground(new Color(112, 109, 107));
          mercury.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(mercury);
        }
        if(this.name == "Venus")
        {
          JPanel venus = new JPanel();
          venus.setBackground(new Color(196, 165, 143));
          venus.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(venus);
        }
        if(this.name == "Earth")
        {
          JPanel earth = new JPanel();
          earth.setBackground(new Color(38, 120, 60));
          earth.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(earth);
        }
        if(this.name == "Moon")
        {
          JPanel moon = new JPanel();
          moon.setBackground(new Color(171, 169, 167));
          moon.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(moon);
        }
        if(this.name == "Mars")
        {
          JPanel mars = new JPanel();
          mars.setBackground(new Color(199, 114, 30));
          mars.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(mars);
        }
        if(this.name == "Jupiter")
        {
          JPanel jupiter = new JPanel();
          jupiter.setBackground(new Color(209, 142, 84));
          jupiter.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(jupiter);
        }
        if(this.name == "Saturn")
        {
          JPanel saturn = new JPanel();
          saturn.setBackground(new Color(212, 169, 131));
          saturn.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(saturn);
        }
        if(this.name == "Titan")
        {
          JPanel titan = new JPanel();
          titan.setBackground(new Color(115, 191, 135));
          titan.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(titan);
        }
        if(this.name == "Uranus")
        {
          JPanel uranus = new JPanel();
          uranus.setBackground(new Color(190, 232, 237));
          uranus.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(uranus);
        }
        if(this.name == "Neptune")
        {
          JPanel neptune = new JPanel();
          neptune.setBackground(new Color(55, 86, 212));
          neptune.setBounds((int)x,(int)y, 5, 5);
          sF.lPane.add(neptune);
        }
        oldStepCount = sF.stepCount;
      }
    }
  }
}
